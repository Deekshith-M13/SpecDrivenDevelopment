## 1. Change Preparation

- [ ] 1.1 Review the existing `RatingService` and `RatingController` implementations.
- [ ] 1.2 Confirm the API contract for the new top-rated endpoint.

## 2. Implementation

### 2.1 Service logic
- [ ] 2.1.1 Add a new method to `RatingService` that computes the highest-rated movie across all rated movies.
- [ ] 2.1.2 Implement deterministic tie-breaking by average score, then total ratings, then movie ID.
- [ ] 2.1.3 Reuse `MovieService` for movie lookup and validation as needed.

### 2.2 Controller endpoint
- [ ] 2.2.1 Add a new `GET /api/v1/ratings/top-rated` route to `RatingController`.
- [ ] 2.2.2 Return the top-rated movie in an `ApiResponse` wrapper.

### 2.3 Tests
- [ ] 2.3.1 Add unit tests to `RatingServiceTest` covering:
  - highest-rated movie selection based on average score
  - tie-breaking when movies share the same average score
  - behavior when no movies have ratings
- [ ] 2.3.2 Optionally add a controller/integration test for the new endpoint.

## 3. Validation

- [ ] 3.1 Run `mvn -q test -Dtest=RatingServiceTest` and verify the new tests pass.
- [ ] 3.2 Run `mvn -q test -Dtest=MovieVerseIntegrationTest` if a controller integration test is added.
- [ ] 3.3 Confirm the new endpoint returns the expected top-rated movie for seeded ratings.
