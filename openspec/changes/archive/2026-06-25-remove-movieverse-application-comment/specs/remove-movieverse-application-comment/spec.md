## ADDED Requirements

### Requirement: Remove stray comment from MovieVerse entrypoint
The system SHALL remove the `// tests` comment from `MovieVerseApplication.java` to keep the startup class clean and free of misleading comments.

#### Scenario: Clean entrypoint source file
- **WHEN** a developer reviews `MovieVerseApplication.java`
- **THEN** the file contains no stray `// tests` comment at the end of the class
