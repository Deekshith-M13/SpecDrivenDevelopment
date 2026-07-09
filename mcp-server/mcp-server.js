import { McpServer } from "@modelcontextprotocol/sdk/server/mcp.js";
import { StdioServerTransport } from "@modelcontextprotocol/sdk/server/stdio.js";
import { z } from "zod";
import { execSync } from "child_process";
import { readFileSync } from "fs";
import path from "path";

const REPO_ROOT = "C:\\Users\\pranav.gujjar\\OneDrive - Accenture\\Desktop\\movie\\moviesite-main";
const GRAPH_REPORT = path.join(REPO_ROOT, "graphify-out", "GRAPH_REPORT.md");

const server = new McpServer({
  name: "movieverse-graphify",
  version: "1.0.0"
});

// Tool 1 — Get full graph report
server.tool(
  "get_graph_report",
  "Get the full Graphify structural graph report for MovieVerse. Use this instead of grepping files to understand codebase structure.",
  {},
  async () => {
    const report = readFileSync(GRAPH_REPORT, "utf8");
    return { content: [{ type: "text", text: report }] };
  }
);

// Tool 2 — Find dependents of a class
server.tool(
  "find_dependents",
  "Find all classes that depend on a given class. Use this instead of grep to find usages.",
  { className: z.string().describe("Java class name e.g. MovieService") },
  async ({ className }) => {
    try {
      const result = execSync(
        `graphify query ${className} --no-llm`,
        { cwd: REPO_ROOT, encoding: "utf8" }
      );
      return { content: [{ type: "text", text: result }] };
    } catch (e) {
      return { content: [{ type: "text", text: e.stdout || e.message }] };
    }
  }
);

// Tool 3 — Blast radius of a class
server.tool(
  "blast_radius",
  "Find everything that breaks if a class changes. Returns direct + transitive dependents and affected tests.",
  { className: z.string().describe("Java class name e.g. TicketingService") },
  async ({ className }) => {
    try {
      const result = execSync(
        `graphify path ${className} --no-llm`,
        { cwd: REPO_ROOT, encoding: "utf8" }
      );
      return { content: [{ type: "text", text: result }] };
    } catch (e) {
      return { content: [{ type: "text", text: e.stdout || e.message }] };
    }
  }
);

// Tool 4 — List all classes in a domain
server.tool(
  "list_domain_classes",
  "List all Java classes in a given domain package. Use instead of browsing folder structure.",
  { domain: z.string().describe("Domain name e.g. ticketing, movie, rating, financial, snapshot, recognition") },
  async ({ domain }) => {
    try {
      const result = execSync(
        `graphify list --filter ${domain} --no-llm`,
        { cwd: REPO_ROOT, encoding: "utf8" }
      );
      return { content: [{ type: "text", text: result }] };
    } catch (e) {
      return { content: [{ type: "text", text: e.stdout || e.message }] };
    }
  }
);

// Tool 5 — Impact analysis: what breaks if file X changes
server.tool(
  "analyze_impact",
  "Full impact analysis for a given Java class. Returns direct dependents, transitive dependents, affected tests, and risk level. Use this instead of grepping to understand blast radius before making a change.",
  {
    className: z.string().describe("Java class name to analyse e.g. MovieService, TicketingRepository, ApiResponse"),
    depth: z.number().optional().describe("BFS depth for transitive search, default 2")
  },
  async ({ className, depth = 2 }) => {

    const results = { className, depth, direct: "", transitive: "", riskLevel: "", affectedTests: [], summary: "" };

    // Step 1 — direct dependents
    try {
      results.direct = execSync(
        `graphify query ${className} --no-llm`,
        { cwd: REPO_ROOT, encoding: "utf8" }
      ).trim();
    } catch (e) {
      results.direct = e.stdout || e.message;
    }

    // Step 2 — transitive path tracing
    try {
      results.transitive = execSync(
        `graphify path ${className} --no-llm`,
        { cwd: REPO_ROOT, encoding: "utf8" }
      ).trim();
    } catch (e) {
      results.transitive = e.stdout || e.message;
    }

    // Step 3 — risk classification based on known high-risk nodes
    const HIGH_RISK = ["MovieService", "MovieRepository", "ApiResponse", "GlobalExceptionHandler"];
    const MEDIUM_RISK = ["RatingService", "TicketingService", "FinancialService"];

    if (HIGH_RISK.some(h => className.includes(h))) {
      results.riskLevel = "HIGH";
      results.affectedTests = ["MovieServiceTest", "MovieVerseIntegrationTest (all 22 tests)"];
    } else if (MEDIUM_RISK.some(m => className.includes(m))) {
      results.riskLevel = "MEDIUM";
      results.affectedTests = [`${className}Test`, "MovieVerseIntegrationTest (relevant methods)"];
    } else {
      results.riskLevel = "LOW";
      results.affectedTests = [`${className}Test`];
    }

    // Step 4 — read graph report for additional context
    let graphContext = "";
    try {
      const report = readFileSync(GRAPH_REPORT, "utf8");
      const lines = report.split("\n");
      const relevant = lines.filter(l => l.toLowerCase().includes(className.toLowerCase()));
      graphContext = relevant.slice(0, 20).join("\n");
    } catch (e) {
      graphContext = "Graph report not available";
    }

    results.summary = `
## Impact Analysis: ${className}

### Risk Level: ${results.riskLevel}

### Direct Dependents
${results.direct || "None found"}

### Transitive Dependents (depth=${depth})
${results.transitive || "None found"}

### Graph Report Context
${graphContext || "No relevant entries"}

### Tests to Run
${results.affectedTests.map(t => `- ${t}`).join("\n")}

### Recommendation
${results.riskLevel === "HIGH"
  ? "Run full integration test suite before merging. Manual review required."
  : results.riskLevel === "MEDIUM"
  ? "Run domain-specific tests + integration tests for affected endpoints."
  : "Run unit tests for this class only."}
    `.trim();

    return { content: [{ type: "text", text: results.summary }] };
  }
);

// Tool 6 — Search for a file by name and return its path
server.tool(
  "find_file",
  "Find a Java file by class name and return its full path. Use this instead of browsing folders or grepping for file locations.",
  {
    className: z.string().describe("Class name to find e.g. TicketingService, BookingRequest")
  },
  async ({ className }) => {
    try {
      const result = execSync(
        `dir /s /b "${REPO_ROOT}\\*${className}*.java"`,
        { encoding: "utf8", shell: "cmd.exe" }
      ).trim();
      return { content: [{ type: "text", text: result || `No file found for: ${className}` }] };
    } catch (e) {
      return { content: [{ type: "text", text: `Not found: ${className}` }] };
    }
  }
);

// Tool 7 — Print all classes, methods, and fields declared in a file
server.tool(
  "inspect_file",
  "List all classes, methods, and fields declared in a given Java file. Use this instead of opening and reading the full file — returns only the structure, not the implementation bodies.",
  {
    className: z.string().describe("Class name to inspect e.g. TicketingService")
  },
  async ({ className }) => {
    try {
      const filePath = execSync(
        `dir /s /b "${REPO_ROOT}\\*${className}.java"`,
        { encoding: "utf8", shell: "cmd.exe" }
      ).trim().split("\n")[0].trim();

      if (!filePath) return { content: [{ type: "text", text: `File not found: ${className}` }] };

      const src = readFileSync(filePath, "utf8");
      const lines = src.split("\n");

      const structure = [];
      structure.push(`File: ${filePath}\n`);

      lines.forEach((line) => {
        const trimmed = line.trim();
        if (/(public|private|protected)?\s*(class|interface|enum)\s+\w+/.test(trimmed)) {
          structure.push(`CLASS: ${trimmed.replace(/{.*/, "").trim()}`);
        } else if (/^\s*(private|protected|public)\s+\w[\w<>,\s]+\s+\w+\s*;/.test(line) && !trimmed.startsWith("//")) {
          structure.push(`  FIELD: ${trimmed}`);
        } else if (/(public|private|protected)[\w\s<>,\[\]]+\(/.test(trimmed)
          && !trimmed.startsWith("//")
          && !trimmed.includes("new ")
          && !trimmed.includes("return ")) {
          structure.push(`  METHOD: ${trimmed.replace(/{.*/, "").trim()}`);
        } else if (/^@(GetMapping|PostMapping|PutMapping|DeleteMapping|RequestMapping|Transactional|PostConstruct)/.test(trimmed)) {
          structure.push(`  ANNOTATION: ${trimmed}`);
        }
      });

      const tokenEstimate = Math.ceil(structure.join("\n").length / 4);
      structure.push(`\n[~${tokenEstimate} tokens — vs ~${Math.ceil(src.length / 4)} tokens for full file read]`);

      return { content: [{ type: "text", text: structure.join("\n") }] };
    } catch (e) {
      return { content: [{ type: "text", text: e.message }] };
    }
  }
);

const transport = new StdioServerTransport();
await server.connect(transport);