package kth.iv1201.gohire.controller;

import kth.iv1201.gohire.DTO.LoggedInPersonDTO;
import kth.iv1201.gohire.DTO.LoginRequestDTO;
import kth.iv1201.gohire.service.PersonService;
import kth.iv1201.gohire.service.exception.LoginFailedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class PersonControllerTest {

    @Mock
    PersonService personService;
    @InjectMocks
    PersonController personController;
    LoginRequestDTO mockLoginRequestDTO;
    LoggedInPersonDTO mockLoggedInPersonDTO;

    @BeforeEach
    void setUp() {
        personController = new PersonController(personService);
    }

    @AfterEach
    void tearDown() {
        personController = null;
    }

    @Test
    void testIfUserReturnedWhenLoginCorrect() throws LoginFailedException, MethodArgumentNotValidException {
        mockLoginRequestDTO = new LoginRequestDTO("exampleUsername", "examplePassword");
        mockLoggedInPersonDTO = new LoggedInPersonDTO(0, "exampleUsername", "recruiter");
        when(personService.login(mockLoginRequestDTO)).thenReturn(mockLoggedInPersonDTO);
        LoggedInPersonDTO returnedLoggedInPersonDTO = personController.login(mockLoginRequestDTO);
        assertEquals(mockLoggedInPersonDTO, returnedLoggedInPersonDTO,
                "Returned LoggedInPersonDTO from PersonController does not equal returned" +
                        "LoggedInPersonDTO from PersonService.");
    }

    @Test
    void testIfLoginFailedExceptionIsThrownWhenCredentialsPresentButIncorrect() throws LoginFailedException {
        mockLoginRequestDTO = new LoginRequestDTO("exampleUsername", "examplePassword");
        when(personService.login(mockLoginRequestDTO)).thenThrow(new LoginFailedException("Login Failed"));
        assertThrowsExactly(LoginFailedException.class, () -> personController.login(mockLoginRequestDTO),
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
}