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

java
└── com
    └── example
        └── mvp
            ├── controller
            │   └── urlMappingController.java
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

## Architecture
Controller --> Service -->Repository --> PostgreSQL