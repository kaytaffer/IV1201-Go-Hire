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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
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

    @Autowired
    private MockMvc mockMvc;

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
    void testIfMethodArgumentNotValidExceptionIsThrownWhenLoginCredentialsAreNull(){
        // TODO
    }

    @Test
    void testIfCorrectExceptionThrownWhenLoginCredentialsAreBlank(){
        // TODO
    }

    @Test
    void testIfExceptionThrownWhenLoginCredentialsAreGreaterThenMax(){
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

    /* The following two tests are temporary since there is no other protected content */

    @Test
    @WithMockUser(roles = {"applicant"})
    void testThatApplicantCanNotAccessRecruiterSecret() {
        assertDoesNotThrow(() -> {
            mockMvc.perform(get("/api/recruiter")).andExpect(status().isForbidden());
        }, "Applicant was allowed to access recruiter secret.");
    }

    @Test
    @WithMockUser(roles = {"recruiter"})
    void testThatRecruiterCanAccessRecruiterSecret() {
        assertDoesNotThrow(() -> {
            mockMvc.perform(get("/api/recruiter")).andExpect(status().isOk());
        }, "Recruiter was not allowed to access recruiter secret.");

    }
}
