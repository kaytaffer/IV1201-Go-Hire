package kth.iv1201.gohire.controller;

import jakarta.servlet.http.HttpSession;
import kth.iv1201.gohire.DTO.CreateApplicantRequestDTO;
import kth.iv1201.gohire.DTO.LoggedInPersonDTO;
import kth.iv1201.gohire.DTO.LoginRequestDTO;
import kth.iv1201.gohire.controller.util.LoggerException;
import kth.iv1201.gohire.service.PersonService;
import kth.iv1201.gohire.controller.exception.LoginFailedException;
import kth.iv1201.gohire.service.exception.UserCreationFailedException;
import kth.iv1201.gohire.service.exception.UserNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class PersonControllerTest {

    @Mock
    PersonService personService;
    @Mock
    HttpSession session;
    @Mock
    AuthenticationManager authenticationManager;
    @InjectMocks
    PersonController personController;
    LoginRequestDTO mockLoginRequestDTO;
    LoggedInPersonDTO mockLoggedInPersonDTO;
    CreateApplicantRequestDTO mockCreateApplicantRequestDTO;
    UsernamePasswordAuthenticationToken mockAuthenticatedSuccessfulResponse;
    UsernamePasswordAuthenticationToken mockAuthenticatedFailedResponse;
    UsernamePasswordAuthenticationToken mockAuthenticationRequest;

    @BeforeEach
    void setUp() {
        Mockito.reset(personService);
        mockLoginRequestDTO = new LoginRequestDTO("exampleUsername", "examplePassword");
        mockLoggedInPersonDTO = new LoggedInPersonDTO(0, "exampleUsername", "recruiter");
        mockCreateApplicantRequestDTO = new CreateApplicantRequestDTO("exampleFirstName", "exampleLastName", "example@example.com", "123456-7890", "exampleUsername", "examplePassword");
        mockAuthenticatedSuccessfulResponse = UsernamePasswordAuthenticationToken.authenticated("exampleUsername2", "examplePassword2", null);
        mockAuthenticatedFailedResponse = UsernamePasswordAuthenticationToken.unauthenticated("exampleUsername", "examplePassword");
        mockAuthenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated("exampleUsername", "examplePassword");
    }

    @AfterEach
    void tearDown() {
        mockLoginRequestDTO = null;
        mockLoggedInPersonDTO = null;
        mockCreateApplicantRequestDTO = null;
    }

    @Test
    void testIfUserReturnedWhenLoginCorrect() throws LoginFailedException, LoggerException, UserNotFoundException {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuthenticatedSuccessfulResponse);
        when(personService.fetchLoggedInPersonByUsername(mockLoginRequestDTO.getUsername()))
                .thenReturn(mockLoggedInPersonDTO);
        LoggedInPersonDTO returnedLoggedInPersonDTO = personController.login(mockLoginRequestDTO, session);
        assertEquals(mockLoggedInPersonDTO, returnedLoggedInPersonDTO,
                "Returned LoggedInPersonDTO from PersonController does not equal returned" +
                        "LoggedInPersonDTO from PersonService.");
    }

    @Test
    void testIfLoginFailedExceptionIsThrownWhenCredentialsPresentButIncorrect() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuthenticatedFailedResponse);
        assertThrowsExactly(LoginFailedException.class, () -> personController.login(mockLoginRequestDTO, session),
                "No LoginFailedException was thrown when credentials were incorrect.");
    }

    @Test
    void testIfMethodArgumentNotValidExceptionIsThrownWhenCredentialsAreNull(){
        // TODO
    }

    @Test
    void testIfCorrectExceptionThrownWhenCredentialsAreBlank(){
        // TODO
    }

    @Test
    void testIfExceptionThrownWhenCredentialsAreGreaterThenMax(){
        // TODO
    }
    @Test
    void testIfApplicantCreationFailedExceptionIsThrownWhenUsernameAlreadyExistsInDB() throws UserCreationFailedException {
        when(personService.createApplicantAccount(mockCreateApplicantRequestDTO)).thenThrow(new UserCreationFailedException("Username already exists in database"));
        assertThrowsExactly(UserCreationFailedException.class, () -> personController.createNewApplicant(mockCreateApplicantRequestDTO),
                "No UserCreationFailedException was thrown when username already exists in the database.");
    }

    @Test
    void testIfUserReturnedWhenCreateNewApplicantSucceeded() throws UserCreationFailedException, LoggerException {
        when(personService.createApplicantAccount(mockCreateApplicantRequestDTO)).thenReturn(mockLoggedInPersonDTO);
        LoggedInPersonDTO user = personController.createNewApplicant(mockCreateApplicantRequestDTO);
        assertEquals(mockCreateApplicantRequestDTO.getUsername(), user.getUsername(),"Returned LoggedInPersonDTO" +
                "from PersonController does not equal returned LoggedInPersonDTO from PersonService.");
    }

    @Test
    void testCreateApplicanIfExceptionIsThrownIfFirstNameIsEmpty() {
        //TODO
    }

    @Test
    void testCreateApplicanIfExceptionIsThrownIfFirstNameIsTooLong() {
        //TODO
    }

    @Test
    void testCreateApplicanIfExceptionIsThrownIfLastNameIsEmpty() {
        //TODO
    }

    @Test
    void testCreateApplicanIfExceptionIsThrownIfLastNameIsTooLong() {
        //TODO
    }
    @Test
    void testCreateApplicanIfExceptionIsThrownIfEmailIsNotFormattedAsEmail() {
        //TODO
    }

    @Test
    void testCreateApplicanIfExceptionIsThrownIfEmailIsEmpty() {
        //TODO
    }

    @Test
    void testCreateApplicantIfExceptionIsThrownIfEmailIsTooLong () {
        //TODO
    }

    @Test
    void testCreateApplicanIfExceptionIsThrownIfPersonNumberIsEmpty() {
        //TODO
    }

    @Test
    void testCreateApplicanIfExceptionIsThrownIfPersonNumberIsPoorlyFormatted() {
        //TODO
    }

    @Test
    void testCreateApplicanIfExceptionIsThrownIfUsernameIsEmpty() {
        //TODO
    }

    @Test
    void testCreateApplicanIfExceptionIsThrownIfUsernameIsTooLong() {
        //TODO
    }

    @Test
    void testCreateApplicanIfExceptionIsThrownIfPasswordIsEmpty() {
        //TODO
    }

    @Test
    void testCreateApplicanIfExceptionIsThrownIfPasswordIsTooLong() {

    }
}
