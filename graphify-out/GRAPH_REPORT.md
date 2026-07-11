# Graph Report - D:\Accenture\spec\SpecDrivenDevelopment  (2026-07-11)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 563 nodes · 1374 edges · 30 communities (24 shown, 6 thin omitted)
- Extraction: 89% EXTRACTED · 11% INFERRED · 0% AMBIGUOUS · INFERRED: 153 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `87efe123`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- TicketingRepository
- Movie
- Rating
- ApiResponse
- Snapshot
- Award
- MovieFinancials
- MovieVerseIntegrationTest
- ResourceNotFoundException
- package.json
- common.ps1
- ScreenType
- token_helper.py
- MovieStatus
- BookingStatus
- OpenApiConfig.java
- mcp-server.js
- MovieVerseApplication
- create-new-feature.ps1
- read_archive.py
- speckit_revert.py
- update-agent-context.sh
- com.movieverse:movieverse-api

## God Nodes (most connected - your core abstractions)
1. `ApiResponse` - 50 edges
2. `Movie` - 38 edges
3. `MovieService` - 34 edges
4. `Genre` - 29 edges
5. `MovieVerseIntegrationTest` - 28 edges
6. `TicketingRepository` - 26 edges
7. `Award` - 22 edges
8. `ResourceNotFoundException` - 21 edges
9. `Snapshot` - 21 edges
10. `Booking` - 21 edges

## Surprising Connections (you probably didn't know these)
- `FinancialService` --references--> `MovieService`  [EXTRACTED]
  movieverse/movieverse/src/main/java/com/movieverse/financial/service/FinancialService.java → movieverse/movieverse/src/main/java/com/movieverse/movie/service/MovieService.java
- `MovieController` --references--> `MovieService`  [EXTRACTED]
  movieverse/movieverse/src/main/java/com/movieverse/movie/controller/MovieController.java → movieverse/movieverse/src/main/java/com/movieverse/movie/service/MovieService.java
- `MovieSummaryDto` --references--> `MovieStatus`  [EXTRACTED]
  movieverse/movieverse/src/main/java/com/movieverse/movie/dto/MovieSummaryDto.java → movieverse/movieverse/src/main/java/com/movieverse/movie/model/MovieStatus.java
- `TopRatedMovieDto` --references--> `Genre`  [EXTRACTED]
  movieverse/movieverse/src/main/java/com/movieverse/movie/dto/TopRatedMovieDto.java → movieverse/movieverse/src/main/java/com/movieverse/movie/model/Genre.java
- `TopRatedMovieDto` --references--> `MovieStatus`  [EXTRACTED]
  movieverse/movieverse/src/main/java/com/movieverse/movie/dto/TopRatedMovieDto.java → movieverse/movieverse/src/main/java/com/movieverse/movie/model/MovieStatus.java

## Import Cycles
- None detected.

## Communities (30 total, 6 thin omitted)

### Community 0 - "TicketingRepository"
Cohesion: 0.06
Nodes (34): BusinessException, BookingRequest, Data, Builder, Data, SeatAvailabilityDto, Booking, Builder (+26 more)

### Community 1 - "Movie"
Cohesion: 0.05
Nodes (45): Builder, Data, MovieSummaryDto, CastMember, Builder, Data, CrewMember, Builder (+37 more)

### Community 2 - "Rating"
Cohesion: 0.08
Nodes (33): Builder, Data, TopRatedMovieDto, GetMapping, Operation, PostMapping, RequestMapping, RequiredArgsConstructor (+25 more)

### Community 3 - "ApiResponse"
Cohesion: 0.09
Nodes (33): DeleteMapping, Getter, JsonInclude, ApiResponse, Builder, GetMapping, Operation, ResponseEntity (+25 more)

### Community 4 - "Snapshot"
Cohesion: 0.09
Nodes (29): GetMapping, Operation, RequestMapping, RequiredArgsConstructor, ResponseEntity, RestController, Tag, SnapshotController (+21 more)

### Community 5 - "Award"
Cohesion: 0.10
Nodes (24): Award, Builder, Data, AwardLeaderResponse, Builder, Data, AwardSummary, Builder (+16 more)

### Community 6 - "MovieFinancials"
Cohesion: 0.13
Nodes (19): FinancialController, RequestMapping, RequiredArgsConstructor, RestController, Tag, Builder, Data, MovieFinancials (+11 more)

### Community 7 - "MovieVerseIntegrationTest"
Cohesion: 0.18
Nodes (6): AutoConfigureMockMvc, MockMvc, DisplayName, Test, MovieVerseIntegrationTest, SpringBootTest

### Community 8 - "ResourceNotFoundException"
Cohesion: 0.24
Nodes (7): ExceptionHandler, MethodArgumentNotValidException, GlobalExceptionHandler, ResponseEntity, Slf4j, ResourceNotFoundException, RestControllerAdvice

### Community 9 - "package.json"
Cohesion: 0.12
Nodes (16): author, dependencies, @modelcontextprotocol/sdk, zod, description, keywords, license, main (+8 more)

### Community 10 - "common.ps1"
Cohesion: 0.23
Nodes (9): Find-SpecifyRoot(), Format-SpecKitCommand(), Get-CurrentBranch(), Get-FeaturePathsEnv(), Get-InvokeSeparator(), Get-Python3Command(), Get-RepoRoot(), Resolve-TemplateContent() (+1 more)

### Community 11 - "ScreenType"
Cohesion: 0.22
Nodes (9): Builder, Data, Screen, ScreenType, DOLBY_ATMOS, FOUR_DX, IMAX, STANDARD (+1 more)

### Community 12 - "token_helper.py"
Cohesion: 0.46
Nodes (7): estimate_cost(), get_encoding(), main(), read_input(), resolve_pricing_model(), safe_chunk_sizes(), Namespace

### Community 13 - "MovieStatus"
Cohesion: 0.25
Nodes (7): MovieStatus, ARCHIVED, IN_PRODUCTION, POST_PRODUCTION, PRE_PRODUCTION, RELEASED, STREAMING

### Community 14 - "BookingStatus"
Cohesion: 0.29
Nodes (6): BookingStatus, CANCELLED, CONFIRMED, EXPIRED, PENDING, REFUNDED

### Community 15 - "OpenApiConfig.java"
Cohesion: 0.53
Nodes (4): Bean, Configuration, OpenApiConfig, OpenAPI

### Community 16 - "mcp-server.js"
Cohesion: 0.50
Nodes (3): GRAPH_REPORT, server, transport

## Knowledge Gaps
- **67 isolated node(s):** `update-agent-context.sh script`, `GRAPH_REPORT`, `server`, `transport`, `name` (+62 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **6 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `MovieService` connect `Movie` to `TicketingRepository`, `Rating`, `ApiResponse`, `Snapshot`, `Award`, `MovieFinancials`?**
  _High betweenness centrality (0.178) - this node is a cross-community bridge._
- **Why does `ApiResponse` connect `ApiResponse` to `ResourceNotFoundException`, `Rating`, `Snapshot`, `MovieFinancials`?**
  _High betweenness centrality (0.174) - this node is a cross-community bridge._
- **Why does `Movie` connect `Movie` to `Award`, `Rating`, `ApiResponse`, `MovieStatus`?**
  _High betweenness centrality (0.075) - this node is a cross-community bridge._
- **What connects `update-agent-context.sh script`, `GRAPH_REPORT`, `server` to the rest of the system?**
  _67 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `TicketingRepository` be split into smaller, more focused modules?**
  _Cohesion score 0.059921710328214396 - nodes in this community are weakly interconnected._
- **Should `Movie` be split into smaller, more focused modules?**
  _Cohesion score 0.05403508771929825 - nodes in this community are weakly interconnected._
- **Should `Rating` be split into smaller, more focused modules?**
  _Cohesion score 0.08269230769230769 - nodes in this community are weakly interconnected._