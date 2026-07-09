# get-highest-rated-movie Specification

## Purpose
Provide an API endpoint that returns the movie with the highest average user rating from the MovieVerse catalogue.

## Requirements

### Requirement: Top-rated movie lookup
The system SHALL return the movie with the highest average user rating from all rated movies.

### Requirement: Exclude unrated movies
The system SHALL ignore movies that have no user ratings when determining the top-rated movie.

### Requirement: Tie-breaking rules
If multiple movies share the same highest average rating, the system SHALL choose the movie with the greater number of ratings.
If the tie remains, the system SHALL choose the movie with the lexicographically smallest movie ID.

### Requirement: API contract
The system SHALL expose the top-rated movie via a new HTTP GET endpoint under `/api/v1/ratings/top-rated`.
The response SHALL use the existing `ApiResponse` wrapper and include movie metadata with the computed rating.

## Scenario: Retrieve the top rated movie
- GIVEN multiple movies with ratings
- WHEN a client requests `/api/v1/ratings/top-rated`
- THEN the API returns the movie with the highest average user rating
- AND the response includes the top movie's metadata and rating information

## Success Criteria
- [ ] The API returns a valid movie response when rated movies exist.
- [ ] Unrated movies are not considered.
- [ ] Tie-breaking is deterministic and documented.
- [ ] The endpoint is discoverable in API documentation with an `@Operation` summary.
