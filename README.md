# Spring Authorization

http://second-codefellows.us-west-2.elasticbeanstalk.com/

## Introduction

This is an example of Spring Security using Spring Boot

The application allows users to create an account and login

and follow other users.

    / - home page allowing user to login
    /register - allows user to create acccount with user info
    /myprofile - navigates to page with user infor
    /feed - see all posts from people you follow
    /users/{userId} - see profiles from other people

The /myprofile path allows for users to add a post attached to them.

## Instructions

Instructions

    Ensure you have Gradle and Postgres installed on your machine.
    Clone this repo.
    
    In the application properties, ensure you have your Postgres database setuo with
    the proper connection strings and username/password to the database
    
    e.g. 
    
    spring.datasource.url=jdbc:${DATABASE_URL}
    spring.datasource.platform=postgres
    spring.datasource.username=${PSQL_USERNAME}
    spring.datasource.password=${PSQL_PASSWORD}
    
    Navigate to the root directory of the repo.
    use "gradle bootRun" to start the application and navigate to the correct URL mentioned above.
    use "gradle test" to test the application
    
    OR
    
    Open project with IntelliJ.
    Navigate to the SongrApplication java file in the src directory and run the file.
    
    After running
