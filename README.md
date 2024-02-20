# Go-Hire 
A recruitment application architecture design for the KTH course IV1201 

# Client
View-Presenter React powered web application.

# js/

`app` - Root component for the application.

## presenter/

`homepage` - Responsible for the logic of the homepage.

`login` - Responsible for the logic of the login page.

### api/

`apiCallHandler` - Authenticates login credentials with the API.

## view/
`createNewApplicantView` - Responsible for rendering the create new applicant form

`homePageApplicantView` - Responsible for rendering the home page for applicants.

`homePageRecruiterView` - Responsible for rendering the home page for recruiters.

`loginView` - Responsible for rendering the login page.

# Server
The server handling HTTP calls.

# java/

`GoHireApplication` - contains main method bootstrapping the server.

## controller/
`PersonController` - Controller responsible for API calls related to a `PersonEntity`.

`StartController` - Responsible for returning the whole React App.

### util
`Logger` - Utility class containing static methods to write to error and event logs.

`LoggerException` - Exception thrown when the `Logger` fails to write to the log.

## service/
`PersonService` - Service Class that handles business logic related to persons.

### exceptions/
`LoginFailedException` - Exception thrown then the logging in of a user to the application fails.

`UserCreationFailedException` - Exception thrown when the creation of a new user to the application fails.

## repository/
`PersonRepository` - Repository responsible for accessing data related to a `PersonEntity`.

## entity/ 
`PersonEntity` - JPA Entity representing a person.

`RoleEntity` - JPA Entity representing a person's role.

## DTO/ 
`CreateUserRequestDTO` - DTO containing information about a user creation request.

`LoggedInPersonDTO` - DTO containing information about a logged-in persons username and role.

`LoginRequestDTO` - DTO containing information about a login request.

