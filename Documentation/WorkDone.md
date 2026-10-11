### Project Goal
* Building a URL shortner to learn backend
* NOT JUST BUILD BUT ALSO UNDERSTAND
### TechStack
* #### Language: Java21
* #### Backend: SpringBoot
* #### Build Tool: Marven
* #### Database: PostgrSQL
* #### Docker

## Project Structure
```text

        └── mvp
            ├── controller
            │   ├── urlMappingController.java
            │   └── urlRedirectController.java
            ├── entity
            │   └── urlMapping.java
            ├── MvpApplication.java
            ├── repository
            │   └── urlMappingRepository.java
            └── service
                └── urlMappingService.java


```

## Implemented Till Now 
* Created a project
* Configured PostgreSQL Connectivity
* Created **urlMapping** entity
* Created **urlMappingRepository**
* Implemented **urlMappingService**
* Implemented **urlController**
* Added _POST /api/urls_
* Added a **urlRedirectController**
* Implemeted a _getByShortCode_ in **urlMappingService** 
* Added _GET /{shortCode}_

## Architecture
Controller --> Service --> Repository --> PostgreSQL