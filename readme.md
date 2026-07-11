=========================================================
MovieVerse - Setup & Implementation Guide
=========================================================

This project demonstrates the integration of:

• GitHub Spec Kit (Specification-Driven Development)
• OpenSpec (Specification Implementation)
• Graphify (Structural Code Graph)
• Custom MCP Server (Graphify Integration)
• Git Post-Commit Hook (Automatic Graph Updates)

=========================================================
Project Structure
=========================================================

project-root/
│
├── movieverse/
│      └── movieverse/
│             ├── pom.xml                  -> Maven configuration
│             ├── src/                     -> Spring Boot application source code
│             │    ├── main/
│             │    │    ├── java/
│             │    │    │    └── com/movieverse/ -> Core application classes
│             │    │    └── resources/     -> Application properties and configs
│             │    └── test/
│             │         └── java/          -> Unit and integration tests
│             └── scripts/                 -> Analysis and utility scripts
│
├── specs/                   -> Specifications created using GitHub Spec Kit
│      └── 001-movie-awards-endpoint/     -> Example feature specification
│             ├── spec.md
│             ├── plan.md
│             ├── data-model.md
│             └── checklists/
│
├── openspec/                -> OpenSpec change management
│      ├── config.yaml       -> OpenSpec configuration
│      ├── changes/          -> Change proposals and implementations
│      └── specs/            -> Synced specifications
│
├── mcp-server/              -> Custom MCP Server for Graphify integration
│      ├── server.js
│      └── package.json
│
├── llmlingua-middleware/    -> LLM middleware utilities
│      └── server.py
│
├── graphify-out/            -> Generated Graphify reports and graph data
├── .github/                 -> GitHub-specific configurations
├── .specify/                -> Spec Kit cache and data
├── AGENTS.md                -> Agent definitions and MCP tools
├── template.md              -> Template documentation
└── test-manifest.json       -> Test manifest file

=========================================================
Prerequisites
=========================================================

Install the following:

• Java 21
• Maven
• Python 3.10 or later
• Node.js (v18 or later)
• Git
• Visual Studio Code
• GitHub Copilot + GitHub Copilot Chat

=========================================================
Installation
=========================================================

1. Clone the repository.

2. Verify Java installation

   java --version

3. Verify Maven

   mvn -version

4. Build the application

   mvn clean install

5. Run the Spring Boot application

   mvn spring-boot:run

=========================================================
GitHub Spec Kit
=========================================================

Follow these steps to install and use GitHub Spec Kit in this repository:

1. Install the GitHub Copilot extensions in VS Code.
   - Install GitHub Copilot
   - Install GitHub Copilot Chat

2. Install Spec Kit using the official installation guide for your platform.
   - Verify the installation by running the help command provided in the guide.

3. Open the repository in VS Code.
   - Make sure the workspace root is the repository folder.

4. Start a new feature workflow from Copilot Chat.
   - Use the /speckit.specify command to describe the feature you want to build.
   - Spec Kit will create or update a spec under the specs/ directory.

5. Continue the workflow in order:
   - /analyze -> creates the technical design
   - /plan -> creates implementation tasks
   - /implement -> applies the planned changes

6. Review the generated files and validate the implementation.
   - Check the files under specs/<feature-name>/
   - Run the relevant build or test commands before committing

Example implementation sequence:
   /specify "Create a movie awards endpoint"
   /analyze
   /plan
   /implement

=========================================================
OpenSpec
=========================================================

OpenSpec works alongside GitHub Spec Kit to turn approved specifications into implementation changes.

1. Install OpenSpec using the official package or release for your operating system.
2. Open the repository root in your terminal.
3. Initialize or run OpenSpec from the repository root so it can read the existing spec files.
4. Create a proposal for the change you want to implement.
5. Review the proposed changes and confirm the scope.
6. Apply the approved changes to the codebase.
7. Validate the result by running tests, build checks, or the relevant application commands.

Typical workflow:
   Specification
      ↓
   Proposal Generation (opsx:propose)
      ↓
   Review Changes(opsx:explore)
      ↓
   Apply Changes(opsx:apply)
      ↓
   Validate Feature
      ↓
   Archive Changes(opsx:apply)
=========================================================
Graphify
=========================================================

Graphify helps create a structural code map for the repository.

1. Install Graphify from the official installation instructions.
2. Verify the installation:
      graphify --help
3. From the repository root, generate the initial graph:
      graphify . --code-only --no-llm
4. Review the generated output files, including:
   - GRAPH_REPORT.md
   - structural dependency graph
   - call graph
   - interactive graph visualization
5. Re-run Graphify after major code changes so the graph stays up to date using "graphify update ." command.

=========================================================
Custom MCP Server
=========================================================

The custom MCP server exposes Graphify functionality to GitHub Copilot as reusable tools.

1. Change to the MCP server directory:
      cd mcp-server
2. Install the Node.js dependencies:
      npm install
3. Start the MCP server:
      npm start
4. Configure GitHub Copilot to connect to the MCP server using your VS Code MCP settings.
5. Restart VS Code if the server does not appear immediately.
6. Test the integration by asking Copilot to run an impact analysis, blast radius check, or file search.

Example local workflow:
   1. Run the server
   2. Ask Copilot to inspect the repository
   3. Use the MCP tools to trace dependencies or analyze change impact

=========================================================
GitHub Copilot
=========================================================

Configure GitHub Copilot to use the custom MCP Server.

Restart VS Code if required.

Verify that Copilot can successfully invoke the MCP tools.

=========================================================
Git Post-Commit Hook
=========================================================

The project includes a Git Post-Commit Hook.

The hook automatically:

• Detects committed Java file changes
• Triggers incremental Graphify indexing
• Updates only modified files
• Keeps the structural graph synchronized

Workflow:

Modify Java File
        ↓
Git Commit
        ↓
Post-Commit Hook
        ↓
Graphify Incremental Update
        ↓
Updated Knowledge Graph

=========================================================
Typical Development Workflow
=========================================================

1. Create a specification using GitHub Spec Kit.

2. Generate and review the implementation using OpenSpec.

3. Build and validate the Spring Boot application.

4. Commit the code.

5. Git Hook automatically updates the Graphify graph.

6. GitHub Copilot queries the graph through the Custom MCP Server.

=========================================================
Technologies Used
=========================================================

Spring Boot            - Backend Application
GitHub Spec Kit        - Specification-Driven Development
OpenSpec               - Specification Implementation
Graphify               - Structural Code Analysis
Custom MCP Server      - Graphify Integration with Copilot
Git Post-Commit Hook   - Automatic Graph Synchronization

=========================================================