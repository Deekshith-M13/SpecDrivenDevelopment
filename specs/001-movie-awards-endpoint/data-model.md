# Data Model: Movie Awards Endpoint

## Entities

### AwardLeaderResponse

Represents the payload returned by the new endpoint.

- **movieTitle**: string — the title of the movie with the highest award total.
- **totalAwards**: integer — the total number of awarded entries counted for that movie.
- **oscarWins**: integer — the number of Academy Award wins for the movie.
- **baftaWins**: integer — the number of BAFTA wins for the movie.

### Relationship

The response is derived from existing award records and movie metadata. Each movie may have many award entries, and the endpoint aggregates those records into one summary response.
