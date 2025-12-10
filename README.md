# GitHub User Activity CLI

Project url: https://roadmap.sh/projects/github-user-activity

Command line tool to fetch and display recent GitHub user activity using the GitHub API. Built in plain Java without external libraries.

## Features
- Fetch recent events from `https://api.github.com/users/{username}/events`
- Parse and display multiple activities (PushEvent, WatchEvent, IssuesEvent, etc.)
- Graceful error handling for invalid usernames and API failures
- Clean multi-class architecture (CLI + HTTP Client + Formatter)

## Usage
java GitHubActivity <username>

text

### Examples
Valid user
java GitHubActivity kamranahmedse

text
undefined
Pushed commits to kamranahmedse/developer-roadmap
Starred sevlyar/go-daemon
CreateEvent in kamranahmedse/developer-roadmap

text
undefined
Invalid user
java GitHubActivity nonexistentuser

text
undefined
Error: User not found: https://api.github.com/users/nonexistentuser/events

text

## Project Structure
src/
├── GitHubActivity.java # CLI entrypoint
├── GitHubApiClient.java # HTTP requests to GitHub API
└── ActivityFormatter.java # JSON parsing and activity formatting

text

## How it works
1. **CLI** reads username from `args[0]`
2. **GitHubApiClient** makes HTTP GET to GitHub Events API
3. **ActivityFormatter** manually parses JSON array, extracts `type` + `repo.name`
4. Maps event types to human-readable messages
5. Prints first 10 events or until end

## Tech Stack
- Java 17+
- `HttpURLConnection` (no external HTTP libs)
- Manual JSON string parsing (no Jackson/Gson)
- Pure stdlib only

## Build & Run
javac src/*.java
java -cp src GitHubActivity <username>

text

## Future Enhancements
- Spring Boot REST API version (`GET /activity/{username}`)
- Jackson JSON parsing with POJOs
- Commit count from PushEvent payload
- Event filtering by type
- Rate limit handling with User-Agent header

