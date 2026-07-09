# Research: Movie Awards Endpoint

## Decision

Implement the endpoint in the existing recognition module by adding a new controller route under the current recognition API and computing the award leader from the existing award repository data.

## Rationale

The repository already exposes award-related services and seeded award data for movies, so extending that module avoids introducing an additional data source or architectural layer. The existing recognition controller already uses the common ApiResponse envelope, which makes the endpoint consistent with the rest of the API.

## Alternatives considered

- Add a new standalone controller in a different module: rejected because it would duplicate the existing recognition context and increase maintenance.
- Compute the leader in the repository layer only: rejected because the service layer already owns the business logic for award summaries and validation.
