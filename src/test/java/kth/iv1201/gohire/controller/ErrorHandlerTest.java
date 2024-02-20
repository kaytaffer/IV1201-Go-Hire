package kth.iv1201.gohire.controller;

import kth.iv1201.gohire.DTO.ErrorDTO;
import kth.iv1201.gohire.controller.util.ErrorType;
import kth.iv1201.gohire.controller.util.LoggerException;
import kth.iv1201.gohire.service.exception.LoginFailedException;
import kth.iv1201.gohire.service.exception.UserCreationFailedException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ErrorHandlerTest {
    static ErrorHandler errorHandler;

    @BeforeAll
    static void setupAll(){
        errorHandler = new ErrorHandler();
    }

    @AfterAll
    static void tearDownAll() {
        errorHandler = null;
    }

    @Test
    void testIfHandleUserCreationFailedExceptionReturnsValidErrorType() throws LoggerException {
        ResponseEntity<ErrorDTO> error = errorHandler.handleException(new UserCreationFailedException("error message"));
        assertEquals(ErrorType.USERNAME_ALREADY_EXISTS, Objects.requireNonNull(error.getBody()).getErrorType(), "Method didn't return correct ErrorType");
    }

    @Test
    void testIfHandleUserCreationFailedExceptionReturnsRightStatusCode() throws LoggerException {
        ResponseEntity<ErrorDTO>  error = errorHandler.handleException(new UserCreationFailedException("error message"));
        assertEquals(HttpStatus.CONFLICT, error.getStatusCode(), "Method didn't return correct Status Code");
    }

    @Test
    void testIfHandleMethodArgumentNotValidExceptionReturnsValidErrorType() throws LoggerException {
        MethodParameter methodParameter = new MethodParameter(this.getClass().getMethods()[0], -1);
        ResponseEntity<ErrorDTO>  error = errorHandler.handleException(new MethodArgumentNotValidException(methodParameter, new BeanPropertyBindingResult("target", "target")));
        assertEquals(ErrorType.USER_INPUT_ERROR, Objects.requireNonNull(error.getBody()).getErrorType(), "Method didn't return correct ErrorType");
    }

    @Test
    void testIfHandleMethodArgumentNotValidExceptionReturnsRightStatusCode() throws LoggerException {
        MethodParameter methodParameter = new MethodParameter(this.getClass().getMethods()[0], -1);
        ResponseEntity<ErrorDTO>  error = errorHandler.handleException(new MethodArgumentNotValidException(methodParameter, new BeanPropertyBindingResult("target", "target")));
        assertEquals(HttpStatus.BAD_REQUEST, error.getStatusCode(), "Method didn't return correct Status Code");
    }

    @Test
    void testIfHandlehandleLoginFailedExceptionReturnsValidErrorType() throws LoggerException {
        ResponseEntity<ErrorDTO> error = errorHandler.handleException(new LoginFailedException("error message"));
        assertEquals(ErrorType.LOGIN_FAIL, Objects.requireNonNull(error.getBody()).getErrorType(), "Method didn't return correct ErrorType");
    }

    @Test
    void testIfHandleLoginFailedExceptionReturnsRightStatusCode() throws LoggerException {
        ResponseEntity<ErrorDTO>  error = errorHandler.handleException(new LoginFailedException("error message"));
        assertEquals(HttpStatus.UNAUTHORIZED, error.getStatusCode(), "Method didn't return correct Status Code");
    }

    @Test
    void testIfHandleExceptionReturnsNullIfLoggerExceptionIsSupplied() throws LoggerException {
        ResponseEntity<ErrorDTO>  error = errorHandler.handleException(new LoggerException("error message"));
        assertNull(error, "Method didn't return a valid instance");
    }

    @Test
    void testIfHandleExceptionReturnsNullIfGenericExceptionIsSupplied() throws LoggerException {
        ResponseEntity<ErrorDTO>  error = errorHandler.handleException(new Exception("error message"));
        assertNull(error, "Method didn't return a valid instance");
    }

}
