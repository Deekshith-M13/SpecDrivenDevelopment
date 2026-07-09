## Why

The previous cleanup removed a stray `// tests` comment from `MovieVerseApplication.java`. This revert restores the file to its prior exact source form.

## What Changes

- Reinsert the `// tests` comment at the end of `MovieVerseApplication.java`.
- Create a revert spec capturing the restoration requirement.
- Document the reversal in design and task artifacts.

## Capabilities

### New Capabilities
- `revert-remove-movieverse-application-comment`: Captures the intent to restore the original source comment.

### Modified Capabilities
- None

## Impact

- Affected file: `movieverse/movieverse/src/main/java/com/movieverse/MovieVerseApplication.java`
- No functional behavior change expected beyond restoring the prior source text.
