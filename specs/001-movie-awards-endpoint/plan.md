# Implementation Plan: Movie Awards Endpoint

**Branch**: `001-movie-awards-endpoint` | **Date**: 2026-07-01 | **Spec**: [spec.md](spec.md)

**Input**: Feature specification from `/specs/001-movie-awards-endpoint/spec.md`

## Summary

Add a new API endpoint that returns the movie with the highest total awards and includes the award counts required by the specification: title, total awards, Oscar wins, and BAFTA wins. The implementation will extend the existing recognition service and controller layer, reusing the current in-memory award repository and movie validation flow.

## Technical Context

**Language/Version**: Java 17 / Spring Boot

**Primary Dependencies**: Spring Web, Lombok, Spring Validation, Swagger/OpenAPI

**Storage**: In-memory repository seeded at startup

**Testing**: JUnit 5, Spring Boot Test

**Target Platform**: Java web service

**Project Type**: Web service

**Performance Goals**: Return the award leader in a single request without additional data access work

**Constraints**: Must fit the existing repository pattern and response envelope used by the API

**Scale/Scope**: Single endpoint addition in the existing recognition module

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

- No constitution file is present for this repository, so the plan proceeds with the existing project conventions and test-first implementation practice.

## Project Structure

### Documentation (this feature)

```text
specs/001-movie-awards-endpoint/
├── plan.md
├── research.md
├── data-model.md
├── quickstart.md
└── contracts/
```

### Source Code (repository root)

```text
movieverse/movieverse/src/main/java/
├── com/movieverse/recognition/controller/
├── com/movieverse/recognition/service/
├── com/movieverse/recognition/repository/
└── com/movieverse/movie/service/

movieverse/movieverse/src/test/java/
├── com/movieverse/recognition/
└── com/movieverse/movie/
```

**Structure Decision**: Extend the existing recognition module with a new endpoint in the controller and a service method that computes the award leader from the seeded repository. Add targeted controller or service tests under the existing Spring test structure.

## Complexity Tracking

No complexity deviations are expected for this change.
