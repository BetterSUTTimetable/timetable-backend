SUT Timetable Backend
===
[![Build Status](https://travis-ci.org/BetterSUTTimetable/timetable-backend.svg?branch=develop)](https://travis-ci.org/BetterSUTTimetable/timetable-backend)
===
Backend for Silesian University Of Technology's timetable. 

Dependencies
---
* JDK 1.8
* Maven

How to run
---
Enter the project's root directory and execute command
`mvn spring-boot:run`

API Documentation
---
Docs are generated with Swagger. Available at http://localhost:8080/swagger-ui.html 
after running the server.

### How to login
Login endpoint description not included in Swagger documentation. 

Sign in using `/login` endpoint using `POST`
method using either `application/json` media type e.g. `{"email":"user@email.com","password":"user_pass"}`
or standard `application/x-www-form-urlencoded` media type e.g. `email=user@email.com&password=user_pass`.
