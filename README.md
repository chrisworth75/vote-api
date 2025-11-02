# Vote API

Spring Boot REST API for the Vote microservice application.

## Endpoints

### User Endpoints
- `POST /api/users/register` - Register a new user
- `POST /api/users/login` - Login an existing user

### Poll Endpoints
- `GET /api/polls` - Get all polls
- `GET /api/polls/{id}` - Get poll by ID

### Vote Endpoints
- `POST /api/votes` - Cast a vote
- `GET /api/votes/results/{pollId}` - Get vote results for a poll
- `GET /api/votes/user/{userId}/poll/{pollId}` - Get user's vote for a specific poll

## Build and Run

```bash
./mvnw clean package
docker build -t vote-api .
docker run --name vote-api -p 8085:8085 -e SPRING_DATASOURCE_URL=jdbc:postgresql://vote-db:5432/votedb -d vote-api
```

## Environment Variables

- `SPRING_DATASOURCE_URL`: Database connection URL
- `SPRING_DATASOURCE_USERNAME`: Database username (default: postgres)
- `SPRING_DATASOURCE_PASSWORD`: Database password (default: password)
