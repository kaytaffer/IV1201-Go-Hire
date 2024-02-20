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
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

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
        ErrorDTO error = errorHandler.handleException(new UserCreationFailedException("error message"));
        assertEquals(ErrorType.USERNAME_ALREADY_EXISTS, error.getErrorType(), "Method didn't return correct ErrorType");
    }

    @Test
    void testIfHandleMethodArgumentNotValidExceptionReturnsValidErrorType() throws LoggerException {
        MethodParameter methodParameter = new MethodParameter(this.getClass().getMethods()[0], -1);
        ErrorDTO error = errorHandler.handleException(new MethodArgumentNotValidException(methodParameter, new BeanPropertyBindingResult("target", "target")));
        assertEquals(ErrorType.USER_INPUT_ERROR, error.getErrorType(), "Method didn't return correct ErrorType");
    }

    @Test
    void testIfHandlehandleLoginFailedExceptionReturnsValidErrorType() throws LoggerException {
        ErrorDTO error = errorHandler.handleException(new LoginFailedException("error message"));
        assertEquals(ErrorType.LOGIN_FAIL, error.getErrorType(), "Method didn't return correct ErrorType");
    }

    @Test
    void testIfHandleExceptionReturnsNullIfLoggerExceptionIsSupplied() throws LoggerException {
        ErrorDTO error = errorHandler.handleException(new LoggerException("error message"));
        assertNull(error, "Method didn't return a valid instance");
    }

    @Test
    void testIfHandleExceptionReturnsNullIfGenericExceptionIsSupplied() throws LoggerException {
        ErrorDTO error = errorHandler.handleException(new Exception("error message"));
        assertNull(error, "Method didn't return a valid instance");
    }

}
