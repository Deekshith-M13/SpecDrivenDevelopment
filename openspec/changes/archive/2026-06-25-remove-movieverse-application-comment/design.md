## Context

`MovieVerseApplication.java` is the Spring Boot application entrypoint for the MovieVerse service. The file currently includes an unnecessary inline comment `// tests` after the closing class bracket.

## Goals / Non-Goals

**Goals:**
- Remove the stray comment from the main application class.
- Keep the change minimal and low-risk.

**Non-Goals:**
- No functional changes to application startup behavior.
- No architectural or dependency modifications.

## Decisions

- Remove only the `// tests` comment and do not alter any other code.
- No new tests are required for behavior; this is a cleanup change.

## Risks / Trade-offs

- Risk: Accidentally changing unrelated formatting or code structure.
  - Mitigation: Limit edits to the comment line only.
