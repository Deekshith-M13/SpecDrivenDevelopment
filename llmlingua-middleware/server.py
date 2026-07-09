from flask import Flask, request, jsonify
from llmlingua import PromptCompressor

app = Flask(__name__)

compressor = PromptCompressor(
    model_name="microsoft/llmlingua-2-xlm-roberta-large-meetingbank",
    use_llmlingua2=True,
    device_map="cpu"
)

@app.route("/compress", methods=["POST"])
def compress():
    data = request.json
    text = data.get("text", "")
    rate = data.get("rate", 0.5)

    if len(text.split()) < 50:
        # Too short to benefit — return as-is
        return jsonify({ "compressed": text, "skipped": True })

    result = compressor.compress_prompt(
        text,
        rate=rate,
        force_tokens=['\n', '.', ':', '-', '{', '}']
    )
    return jsonify({ "compressed": result["compressed_prompt"], "skipped": False })

if __name__ == "__main__":
    app.run(port=5001)