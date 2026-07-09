## Design

### Goal
Provide a single API endpoint that returns the movie with the highest average user rating, along with its metadata.

### Architecture

- `RatingService` already computes rating summaries for individual movies and depends on `RatingRepository` and `MovieService`.
- The new feature will add a top-rated lookup method inside `RatingService`.
- This method will evaluate all movies with ratings and select the highest-rated movie using a deterministic tie-breaker.
- The existing `RatingController` will expose a new route under `/api/v1/ratings/top-rated`.

### Data and selection rules

- Use average user rating as the primary ranking metric.
- Ignore movies with no user ratings.
- If there is a tie on average score, use total rating count as a tie-breaker.
- If still tied, use a deterministic fallback such as the lexicographically smallest movie ID.

### Response model

- Return the selected movie entity or a summary DTO that includes movie metadata and the computed average score.
- The route will be read-only and return a standard `ApiResponse` wrapper for consistency with the rest of the API.

### Error handling

- If no rated movies exist, return a 404-style resource not found or an empty response with a clear message.
- Reuse existing `ResourceNotFoundException` semantics where appropriate.

### Implementation notes

- Keep the top-rated selection logic encapsulated in `RatingService` to preserve controller simplicity.
- Reuse `MovieService` for movie validation and metadata retrieval.
- Add focused tests in `RatingServiceTest` for average ranking and tie-breaking.
- Ensure the new API endpoint is documented with `@Operation` metadata.
