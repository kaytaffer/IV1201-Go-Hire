# Go-Hire 
A recruitment application architecture design for the KTH course IV1201

## Setup
1. Clone the repository
2. Make sure node, npm and maven are installed
3. Create a file `src/main/resources/application-local.properties` containing the database configuration, such as:
    ```
   spring.datasource.url=jdbc:postgresql://<host>:<port>/<database>
    spring.datasource.username=<username>
    spring.datasource.password=<password>
   ```
4. Run:
   - `mvn frontend:install-node-and-npm`
   - `npm install`
   - `mvn frontend:webpack`
   - `mvn install`

## Running
Run both `npm run watch` and `GoHireApplication`

---

# Project Structure

## Overview
```
.
└── src                     # source code
    ├── main                
    │   ├── java                  # server source code
    │   │   └── kth.iv1201.gohire
    │   │       ├── config             # spring config classes
    │   │       ├── controller         # backend controller layer
    │   │       ├── DTO                # DTOs
    │   │       ├── entity             # JPA entities
    │   │       ├── repository         # backend repository layer
    │   │       └── service            # backend service layer
    │   ├── js                    # client source code
    │   │   ├── presenter              # frontend presenter layer
    │   │   └── view                   # frontend view layer
    │   └── resources            # application resources
    │       ├── static                 # static web resources, such as favicon and styles
    │       └── templates              # html templates (contains index.html which loads react)
    └── test                # acceptance tests and backend unit tests
        ├── java                # java tests
        │   └── kth.iv1201.gohire
        │       ├── config             # tests for spring config
        │       ├── controller         # tests for controller layer
        │       └── service            # tests for service layer
        └── resources           # application resources for testing
```

## Client
View-Presenter React powered web application.

### js/
- `app` - Root component for the application.
- #### presenter/
  - `homepage` - Responsible for the logic of the homepage.
  - `login` - Responsible for the logic of the login page.
  - **api/**
    - `apiCallHandler` - Authenticates login credentials with the API.
    - `errorMessages` - Pre-defined error objects with their error type matched with the message to be displayed to user.
- #### view/
  - `applicationListingView` - Responsible for rendering an application.
  - `applicationListView` - Responsible for rendering the list of applications.
  - `createNewApplicantView` - Responsible for rendering the create new applicant form.
  - `containerView` - Responsible for rendering the container around the entire page content
  - `footerView` - Responsible for rendering the page footer
  - `handleApplicationView` - Responsible for rendering a view in which to handle application status.
  - `homePageApplicantView` - Responsible for rendering the home page for applicants.
  - `homePageRecruiterView` - Responsible for rendering the home page for recruiters.
  - `loginView` - Responsible for rendering the login page.
  - `mainContentView` - Responsible for rendering the container around the main page content
  - `popupView` - Popup container meant to wrap child components as a popup view.
  - `topBarView` - Responsible for rendering the top bar
  - `userNoticeView` - Responsible for rendering user notice messages.

## Server
The server handling HTTP calls.

### java/

- `GoHireApplication` - contains main method bootstrapping the server.
- #### controller/
  - `ErrorHandler` - Class handling exceptions.
  - `PersonController` - Controller responsible for API calls related to a `PersonEntity`.
  - `StartController` - Responsible for returning the whole React App.
  - **util**
    - `ErrorType` - ENUM representing error types.
    - `Logger` - Utility class containing static methods to write to error and event logs.
    - `LoggerException` - Exception thrown when the `Logger` fails to write to the log.
- #### service/
  - `PersonService` - Service Class that handles business logic related to persons.
  - `SpringDataJpaUserDetailsService` - Used by Spring Security AuthenticationManager for fetching user information when authenticating.
  - **exception/**
    - `ApplicationHandledException` - Exception thrown when an application that has already been handled is ordered to change.
    - `UserCreationFailedException` - Exception thrown when the creation of a new user to the application fails.
    - `UserNotFoundException` - Exception thrown when requesting a user which does not exist in the database.
- #### repository/
  - `PersonRepository` - Repository responsible for accessing data related to a `PersonEntity`.
  - `ApplicationStatusRepository` - Repository responsible for accessing data related to `ApplicationStatusEntity`.
  - `RoleRepository` - Repository responsible for accessing data related to `RoleEntity`.
- #### entity/
  - `PersonEntity` - JPA Entity representing a person.
  - `RoleEntity` - JPA Entity representing a person's role.
  - `ApplicationStatusEntity` - JPA entity representing an application status
- #### DTO/
  - `ChangeApplicationStatusRequestDTO.java` - DTO containing information about an applicant creation request.
  - `CreateApplicantRequestDTO` - DTO containing information about a user creation request.
  - `ErrorDTO` - Class representing an error response.
  - `LoggedInPersonDTO` - DTO containing information about a logged-in persons username and role.
  - `LoginRequestDTO` - DTO containing information about a login request.
  - `ApplicantDTO` - DTO containing information about an application.
- #### config/
  - `SecurityConfiguration`- Configuration for Spring security.
  - `DelegatedAuthenticationEntryPoint` - Configures the authentication entry point