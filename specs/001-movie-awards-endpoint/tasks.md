# Tasks: Movie Awards Endpoint

**Input**: Design documents from `/specs/001-movie-awards-endpoint/`

**Prerequisites**: plan.md (required), spec.md (required for user stories), research.md, data-model.md, contracts/

**Tests**: Tests are included because the feature adds a new API endpoint and the codebase already uses unit tests.

## Format: `[ID] [P?] [Story] Description`

## Phase 1: Setup (Shared Infrastructure)

- [x] T001 Create or confirm the feature-specific implementation structure under the existing Spring module
- [x] T002 [P] Add a focused unit test file for recognition award aggregation in `movieverse/movieverse/src/test/java/com/movieverse/recognition/`

## Phase 2: Foundational (Blocking Prerequisites)

- [x] T003 Add a response DTO for the new award leader payload in `movieverse/movieverse/src/main/java/com/movieverse/recognition/model/`
- [x] T004 Implement award leader aggregation logic in `movieverse/movieverse/src/main/java/com/movieverse/recognition/service/RecognitionService.java`
- [x] T005 Add a controller endpoint for the award leader in `movieverse/movieverse/src/main/java/com/movieverse/recognition/controller/RecognitionController.java`

## Phase 3: User Story 1 - Retrieve the top-awarded movie (Priority: P1) 🎯 MVP

**Goal**: Expose a single API endpoint that returns the top-awarded movie with the requested award counts.

**Independent Test**: A client can call the endpoint and receive the expected movie title and award counts without any other setup.

### Tests for User Story 1

- [x] T006 [P] [US1] Add a service-level unit test for the award leader selection in `movieverse/movieverse/src/test/java/com/movieverse/recognition/RecognitionServiceTest.java`
- [x] T007 [P] [US1] Add a controller-level test for the new endpoint in `movieverse/movieverse/src/test/java/com/movieverse/recognition/RecognitionControllerTest.java`

### Implementation for User Story 1

- [x] T008 [US1] Implement the new endpoint contract response in the recognition controller and service layer using `movieverse/movieverse/src/main/java/com/movieverse/recognition/`
- [x] T009 [US1] Ensure the endpoint returns the movie title, total awards, Oscar wins, and BAFTA wins from the existing award repository data
- [x] T010 [US1] Verify the response shape and fallback behavior for empty award data in the recognition tests

## Phase 4: Polish & Cross-Cutting Concerns

- [x] T011 [P] Update API documentation comments for the new endpoint in `movieverse/movieverse/src/main/java/com/movieverse/recognition/controller/RecognitionController.java`
- [x] T012 Run the relevant Maven tests for the recognition feature and confirm the endpoint behavior

## Dependencies & Execution Order

- Phase 1 must be completed before implementation work begins.
- Phase 2 blocks the user story implementation.
- Phase 3 is the MVP and can be validated independently.
- Phase 4 runs after the feature is working.

## Parallel Opportunities

- The service and controller tests can be authored in parallel once the DTO and service method are defined.
- The documentation comment update can be completed independently after the endpoint is implemented.

## Implementation Strategy

- Implement the DTO and service logic first.
- Add the controller endpoint next.
- Validate through focused unit tests before running the broader Maven suite.
