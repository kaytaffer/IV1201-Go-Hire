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
    void testIfUserReturnedWhenLoginCorrect() throws LoginFailedException {
        mockLoginRequestDTO = new LoginRequestDTO("exampleUsername", "examplePassword");
        mockLoggedInPersonDTO = new LoggedInPersonDTO(0, "exampleUsername", "recruiter");
        when(personService.login(mockLoginRequestDTO)).thenReturn(mockLoggedInPersonDTO);
        LoggedInPersonDTO returnedLoggedInPersonDTO = personController.login(mockLoginRequestDTO);
        assertEquals(mockLoggedInPersonDTO, returnedLoggedInPersonDTO,
                "Returned LoggedInPersonDTO from PersonController does not equal returned" +
                        "LoggedInPersonDTO from PersonService.");
    }

    @Test
    void testIfLoginFailedExceptionThrownWhenCredentialsPresentButIncorrect() {
        mockLoginRequestDTO = new LoginRequestDTO("exampleUsername", "examplePassword");
        try {
            when(personService.login(mockLoginRequestDTO)).thenThrow(new LoginFailedException("Login Failed"));
        } catch (Exception e) {
            assertEquals(e.getClass(), LoginFailedException.class,
                    "Unexpected exception type was thrown, expected LoginFailedException.");
        }
    }

    // TODO test validation

    // TODO test transaction

}