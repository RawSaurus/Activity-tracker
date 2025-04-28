# A-Track

## Description
A-Track is a comprehensive activity tracking application designed to help users manage and monitor their daily activities, goals, and achievements. Built with Spring Boot and Java, it provides a robust backend for handling templates, achievements, and user profiles, while ensuring secure and efficient data management. The project includes features like user registration, email validation, managing library of activities(create, update, share, delete), creating different types of achievements for activities, seeing progress with option to create sessions and sharing your activities for others to use via templates

## Why
You might be telling to yourself "Oh great, just another fancy TODO app." ... And you would be exactly right! So why create enterprise level project structure for basically a FizzBuzz test ? First of all, this is a learning project, so I decided to choose something conceptually simple so I could channel difficulty into implementing language and other tools chosen for this project, to learn optimal procedures and conventions. Secondly there are things that other productivity apps miss or don't have it in a way I would want, so of course, the only logical solution was to build one myself and step by step continue improving it so I (and hopefully other people) will like it and find it helpful

## Features
- User registration and authorization
- Email validation
- File upload and download for images
- Creating custom activities
- Extending activities with achievements and sessions
- Creating templates based on your activities for other users
- Calendar functionality to see achievements and sessions you have done, and mark plans

## Technologies

### Backend
- Spring Boot 3
- Spring Security 6
- Spring HATEOAS
- JWT Token Authentication
- Spring Data JPA
- Spring Validation
- OpenAPI and Swagger UI Documentation
- Postgresql
- Docker
- Testing: JUnit, Mockito, Hamcrest, AssertJ

## Getting Started
To get started:
- Create *.env* file in same directory as *docker-compose.yml* and fill these values:
  ```
  DB_USERNAME=[your-db-username]
  DB_PASSWORD=[your-db-password]
  ```
- Then follow instructions in respective directiories: 
  - Frontend currently under development
  - [Backend](/activity-tracker/README.md)

## Contributing
- This is intended as solo project but if you want to create a pull request, you can fork the project and follow instructions in [Getting Started](#getting-started)
