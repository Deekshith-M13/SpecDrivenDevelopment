## Why

Consumers of the MovieVerse API need a simple way to discover the single top-rated movie in the catalogue. The system already stores user movie ratings and computes per-movie summaries, so adding a dedicated highest-rated endpoint improves discoverability without changing existing rating workflows.

## What Changes

- Add a new API endpoint to return the movie with the highest average user rating.
- Extend `RatingService` with logic to evaluate ratings across all movies and select the top-rated movie.
- Add a controller route under `/api/v1/ratings` for the top-rated movie lookup.
- Update tests to cover the new service behavior and endpoint.
- Add a new OpenSpec feature spec and implementation task artifacts.

## Capabilities

### New Capabilities
- `get-highest-rated-movie`: Exposes a top-rated movie lookup based on aggregated user ratings.

### Modified Capabilities
- `RatingService`: Add top-rated movie selection.
- `RatingController`: Add top-rated rating endpoint.

## Impact

- Affected files:
  - `movieverse/movieverse/src/main/java/com/movieverse/rating/service/RatingService.java`
  - `movieverse/movieverse/src/main/java/com/movieverse/rating/controller/RatingController.java`
  - `movieverse/movieverse/src/test/java/com/movieverse/RatingServiceTest.java`

- Expected behavior: returns the movie with the highest average user rating among movies that have ratings.
- No breaking API changes for existing rating or movie endpoints.
- The new endpoint is read-only and does not modify persisted ratings.
