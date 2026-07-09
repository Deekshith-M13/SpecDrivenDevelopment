#!/usr/bin/env python3
"""Token utility for prompt sizing and rough cost estimation.

Examples:
  python scripts/token_helper.py --text "hello world"
  python scripts/token_helper.py --file src/main/java/com/movieverse/ticketing/service/TicketingService.java
  python scripts/token_helper.py --file README.md --model gpt-4o
"""

from __future__ import annotations

import argparse
from pathlib import Path

import tiktoken

# Approximate USD rates per 1M tokens (adjust if your provider/model differs).
MODEL_PRICES = {
    "gpt-4o": {"input": 5.00, "output": 15.00},
    "gpt-4.1": {"input": 5.00, "output": 15.00},
    "gpt-4.1-mini": {"input": 0.80, "output": 3.20},
    "gpt-4.1-nano": {"input": 0.20, "output": 0.80},
    "o4-mini": {"input": 3.00, "output": 12.00},
    # Claude family — base aliases (approximate; update to your provider's current published rates)
    "claude-haiku": {"input": 0.25, "output": 1.25},
    "claude-sonnet": {"input": 3.00, "output": 15.00},
    "claude-opus": {"input": 15.00, "output": 75.00},
    # Claude Haiku versioned
    "claude-haiku-4.6": {"input": 0.25, "output": 1.25},
    "claude-haiku-4.7": {"input": 0.25, "output": 1.25},
    "claude-haiku-4.8": {"input": 0.25, "output": 1.25},
    # Claude Sonnet versioned
    "claude-sonnet-4.6": {"input": 3.00, "output": 15.00},
    "claude-sonnet-4.7": {"input": 3.00, "output": 15.00},
    "claude-sonnet-4.8": {"input": 3.00, "output": 15.00},
    # Claude Opus versioned
    "claude-opus-4.6": {"input": 15.00, "output": 75.00},
    "claude-opus-4.7": {"input": 15.00, "output": 75.00},
    "claude-opus-4.8": {"input": 15.00, "output": 75.00},
    # Gemini family (approximate; update to your provider's current published rates)
    "gemini-3-pro": {"input": 7.00, "output": 21.00},
    "gemini-2.5-pro": {"input": 7.00, "output": 21.00},
    "gemini-2.5-flash": {"input": 0.35, "output": 1.05},
    "gemini-2.0-flash": {"input": 0.10, "output": 0.40},
}


MODEL_ALIASES = {
    # Common Claude naming variants
    "claude-3-haiku": "claude-haiku",
    "claude-3.5-haiku": "claude-haiku",
    "claude-3.7-sonnet": "claude-sonnet",
    "claude-3.5-sonnet": "claude-sonnet",
    "claude-3-opus": "claude-opus",
    "haiku": "claude-haiku",
    "sonnet": "claude-sonnet",
    "opus": "claude-opus",
    # Gemini aliases requested
    "gemini-3-pro-latest": "gemini-3-pro",
    "gemini-pro-latest": "gemini-3-pro",
    "gemini-2.5-pro-latest": "gemini-2.5-pro",
    "gemini-2.5-flash-latest": "gemini-2.5-flash",
    "gemini-2.0-flash-latest": "gemini-2.0-flash",
}


def resolve_pricing_model(model: str | None) -> str | None:
    if not model:
        return None
    normalized = model.strip().lower()
    if normalized in MODEL_PRICES:
        return normalized
    if normalized in MODEL_ALIASES:
        return MODEL_ALIASES[normalized]
    return None


def get_encoding(model: str | None, fallback_encoding: str):
    if model:
        try:
            return tiktoken.encoding_for_model(model), f"model:{model}"
        except KeyError:
            pass
    return tiktoken.get_encoding(fallback_encoding), f"encoding:{fallback_encoding}"


def estimate_cost(model: str | None, tokens: int) -> dict[str, float] | None:
    pricing_model = resolve_pricing_model(model)
    if pricing_model is None:
        return None
    per_million = MODEL_PRICES[pricing_model]
    return {
        "input_only": (tokens / 1_000_000) * per_million["input"],
        "input_plus_output_same_size": (tokens / 1_000_000)
        * (per_million["input"] + per_million["output"]),
    }


def safe_chunk_sizes(max_context: int, reserve_output: int, overlap: int) -> dict[str, int]:
    budget = max(1, max_context - reserve_output)
    conservative = max(1, budget - 1500)
    recommended = max(1, conservative - overlap)
    aggressive = max(1, budget - overlap)
    return {
        "recommended": recommended,
        "conservative": conservative,
        "aggressive": aggressive,
    }


def read_input(args: argparse.Namespace) -> str:
    if args.text is not None:
        return args.text
    if args.file is not None:
        return Path(args.file).read_text(encoding="utf-8")
    raise ValueError("Provide either --text or --file")


def main() -> None:
    parser = argparse.ArgumentParser(description="Count tokens and estimate chunk/cost guidance")
    src = parser.add_mutually_exclusive_group(required=True)
    src.add_argument("--text", help="Inline text to count")
    src.add_argument("--file", help="Path to file to count")

    parser.add_argument("--model", default="", help="Model name for tokenizer/cost lookup (e.g., gpt-4o)")
    parser.add_argument("--encoding", default="cl100k_base", help="Fallback encoding name")
    parser.add_argument("--max-context", type=int, default=128000, help="Model context window")
    parser.add_argument("--reserve-output", type=int, default=4000, help="Reserve output tokens")
    parser.add_argument("--overlap", type=int, default=200, help="Chunk overlap tokens")

    args = parser.parse_args()

    text = read_input(args)
    encoding, encoding_used = get_encoding(args.model or None, args.encoding)
    token_count = len(encoding.encode(text))

    chunks = safe_chunk_sizes(args.max_context, args.reserve_output, args.overlap)
    est = estimate_cost(args.model or None, token_count)

    print("Token Helper")
    print(f"- tokenizer: {encoding_used}")
    print(f"- characters: {len(text)}")
    print(f"- tokens: {token_count}")

    print("\nChunk guidance")
    print(f"- max_context: {args.max_context}")
    print(f"- reserve_output: {args.reserve_output}")
    print(f"- overlap: {args.overlap}")
    print(f"- recommended_chunk_tokens: {chunks['recommended']}")
    print(f"- conservative_chunk_tokens: {chunks['conservative']}")
    print(f"- aggressive_chunk_tokens: {chunks['aggressive']}")

    print("\nCost estimate (USD)")
    if est is None:
        print("- model pricing: not available for this model")
        print("- known models: " + ", ".join(sorted(MODEL_PRICES.keys())))
    else:
        resolved = resolve_pricing_model(args.model or None)
        print(f"- model: {args.model}")
        if resolved and resolved != (args.model or "").strip().lower():
            print(f"- pricing_key: {resolved}")
        print(f"- input_only: ${est['input_only']:.6f}")
        print(f"- input_plus_output_same_size: ${est['input_plus_output_same_size']:.6f}")


if __name__ == "__main__":
    main()
