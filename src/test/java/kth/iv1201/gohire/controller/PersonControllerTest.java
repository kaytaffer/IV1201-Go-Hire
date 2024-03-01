package kth.iv1201.gohire.controller;

import jakarta.servlet.http.HttpSession;
import kth.iv1201.gohire.DTO.*;
import kth.iv1201.gohire.controller.util.Logger;
import kth.iv1201.gohire.controller.util.LoggerException;
import kth.iv1201.gohire.service.PersonService;
import kth.iv1201.gohire.service.exception.ApplicationHandledException;
import kth.iv1201.gohire.service.exception.UserCreationFailedException;
import kth.iv1201.gohire.service.exception.UserNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
    Authentication mockAuthentication;

    Authentication mockSuccessfulAuthentication;

    ChangeApplicationStatusRequestDTO mockChangeApplicationStatusRequestDTO;
    ApplicantDTO mockAcceptedApplicantDTO;
    ApplicantDTO mockRejectedApplicantDTO;
    LinkedList<ApplicantDTO> mockListOfApplicants;

    String filePathEventLog;
    String filePathErrorLog;

    @BeforeEach
    void setUp() {
        Mockito.reset(personService);
        mockLoginRequestDTO = new LoginRequestDTO("exampleUsername", "examplePassword");
        mockLoggedInPersonDTO = new LoggedInPersonDTO(0, "exampleUsername", "recruiter");
        mockCreateApplicantRequestDTO = new CreateApplicantRequestDTO("exampleFirstName", "exampleLastName", "example@example.com", "123456-7890", "exampleUsername", "examplePassword");
        mockAuthenticatedSuccessfulResponse = UsernamePasswordAuthenticationToken.authenticated("exampleUsername2", "examplePassword2", null);
        mockAuthenticatedFailedResponse = UsernamePasswordAuthenticationToken.unauthenticated("exampleUsername", "examplePassword");
        mockAuthenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated("exampleUsername", "examplePassword");
        mockListOfApplicants = new LinkedList<>();
        int mockApplicantID = 1;
        mockChangeApplicationStatusRequestDTO = new ChangeApplicationStatusRequestDTO(mockApplicantID, "anyStatus", "exampleUsername", "examplePassword");
        mockAcceptedApplicantDTO = new ApplicantDTO(mockApplicantID, "exampleFirstName", "exampleLastName", "accepted");

        String date = LocalDate.now().toString();
        filePathEventLog = date + "_" + "eventlog.txt";
        filePathErrorLog = date + "_" + "errorlog.txt";
    }

    @AfterEach
    void tearDown() {
        mockLoginRequestDTO = null;
        mockLoggedInPersonDTO = null;
        mockCreateApplicantRequestDTO = null;
        mockListOfApplicants = null;
    }

    @Test
    void testIfUserReturnedWhenLoginCorrect() throws LoggerException, UserNotFoundException {
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
        assertThrowsExactly(BadCredentialsException.class, () -> personController.login(mockLoginRequestDTO, session),
                "No LoginFailedException was thrown when credentials were incorrect.");
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
        assertEquals(mockCreateApplicantRequestDTO.getUsername(), user.getUsername(), "Returned LoggedInPersonDTO" +
                "from PersonController does not equal returned LoggedInPersonDTO from PersonService.");
    }

    @Test
    void testIfFetchApplicantsReturnsListOfApplicants() {
        when(personService.fetchApplicants()).thenReturn(mockListOfApplicants);
        List<ApplicantDTO> applicants = personController.fetchApplicants();
        assertEquals(LinkedList.class, applicants.getClass(), "");
    }

    @Test
    void testIfApplicationHandledExceptionIsThrown() throws ApplicationHandledException {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuthenticatedSuccessfulResponse);
        when(personService.changeApplicantStatus(mockChangeApplicationStatusRequestDTO)).thenThrow(new ApplicationHandledException("The application has already been handled"));
        assertThrowsExactly(ApplicationHandledException.class, () -> personController.changeApplicationStatus(mockChangeApplicationStatusRequestDTO),
                "No ApplicationHandledException was thrown when applicant has already been handled.");
    }

    @Test
    void testIfBadCredentials() throws ApplicationHandledException {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuthenticatedFailedResponse);
        assertThrowsExactly(BadCredentialsException.class, () -> personController.changeApplicationStatus(mockChangeApplicationStatusRequestDTO),
                "No BadCredentialsException was thrown when credentials were incorrect.");
    }

    @Test
    void testIfChangeApplicantStatusReturnCorrectDTO() throws ApplicationHandledException, LoggerException {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuthenticatedSuccessfulResponse);
        when(personService.changeApplicantStatus(mockChangeApplicationStatusRequestDTO))
                .thenReturn(mockAcceptedApplicantDTO);
        ApplicantDTO returnedApplicantDTO = personController.changeApplicationStatus(mockChangeApplicationStatusRequestDTO);
        assertEquals(mockChangeApplicationStatusRequestDTO.getId(), returnedApplicantDTO.getId(),
                "Returned ApplicantDTOs id" +
                        "from PersonController does not equal returned ApplicantDTOs id from PersonService.");
    }

    @Test
    void testIfSuccessfulChangeApplicantStatusIsLoggedCorrectly() throws ApplicationHandledException, LoggerException, IOException {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuthenticatedSuccessfulResponse);
        when(personService.changeApplicantStatus(mockChangeApplicationStatusRequestDTO))
                .thenReturn(mockAcceptedApplicantDTO);
        ApplicantDTO changedApplicant = personController.changeApplicationStatus(mockChangeApplicationStatusRequestDTO);
        String messageThatShouldBeLogged = "Recruiter " + mockChangeApplicationStatusRequestDTO.getUsername() + " changed status of applicant " + changedApplicant.getFirstName() + " " +
                changedApplicant.getLastName() + " to " + changedApplicant.getStatus() + ".";
        boolean match = checkIfCorrectEventMessageWasLogged(messageThatShouldBeLogged);
        assertTrue(match, "Expected event message not written to eventlog");
    }

    @Test
    void testIfSuccessfulCreateNewApplicantIsLoggedCorrectly() throws UserCreationFailedException, LoggerException, IOException {
        when(personService.createApplicantAccount(mockCreateApplicantRequestDTO)).thenReturn(mockLoggedInPersonDTO);
        LoggedInPersonDTO newApplicant = personController.createNewApplicant(mockCreateApplicantRequestDTO);
        String messageThatShouldBeLogged = "New applicant registered: " + newApplicant.getUsername();
        boolean match = checkIfCorrectEventMessageWasLogged(messageThatShouldBeLogged);
        assertTrue(match, "Expected event message not written to eventlog");
    }

    @Test
    void testIfSuccessfulLoginIsLoggedCorrectly() throws UserNotFoundException, LoggerException, IOException {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuthenticatedSuccessfulResponse);
        when(personService.fetchLoggedInPersonByUsername(mockLoginRequestDTO.getUsername()))
                .thenReturn(mockLoggedInPersonDTO);
        personController.login(mockLoginRequestDTO, session);
        String messageThatShouldBeLogged = "New applicant registered: " + mockLoginRequestDTO.getUsername();
        boolean match = checkIfCorrectEventMessageWasLogged(messageThatShouldBeLogged);
        assertTrue(match, "Expected event message not written to eventlog");
    }

    private boolean checkIfCorrectEventMessageWasLogged(String messageThatShouldBeLogged) throws IOException {
        FileReader fileReader = new FileReader(filePathEventLog);
        BufferedReader reader = new BufferedReader(fileReader);
        boolean match = false;
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains(messageThatShouldBeLogged)) {
                match = true;
            }
        }
        reader.close();
        return match;
    }
}