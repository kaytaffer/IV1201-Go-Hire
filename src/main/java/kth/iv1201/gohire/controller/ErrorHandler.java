package kth.iv1201.gohire.controller;

import kth.iv1201.gohire.DTO.ErrorDTO;
import kth.iv1201.gohire.controller.util.LoggerException;
import kth.iv1201.gohire.service.exception.LoginFailedException;
import kth.iv1201.gohire.service.exception.UserCreationFailedException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import kth.iv1201.gohire.controller.util.ErrorType;

/**
 * Class handling exceptions.
 */
@Controller
@ControllerAdvice
public class ErrorHandler implements ErrorController {

    /**
     * Method responsible for logging exceptions, and return appropriate error object to client.
     * @param exception Exception thrown within server environment.
     * @return ErrorDTO containing <code>ErrorType</code> and appropriate message to client.
     */
    @ExceptionHandler({UserCreationFailedException.class, LoggerException.class,
            MethodArgumentNotValidException.class, LoginFailedException.class})

    public ErrorDTO handleException (Exception exception){

        if (exception instanceof UserCreationFailedException) {
            return handleUserCreationFailedException((UserCreationFailedException) exception);
        } else if (exception instanceof LoggerException) {
            return handleLoggerException((LoggerException) exception);
        } else if (exception instanceof MethodArgumentNotValidException) {
            return handleMethodArgumentNotValidException((MethodArgumentNotValidException) exception);
        } else if (exception instanceof LoginFailedException) {
            return handleLoginFailedException((LoginFailedException) exception);
        } else
        return new ErrorDTO(null, null);
    }

    private ErrorDTO handleUserCreationFailedException(UserCreationFailedException exception){
        return new ErrorDTO(ErrorType.USERNAME_ALREADY_EXISTS,null);
    }
    private ErrorDTO handleLoggerException(LoggerException exception) {
        //TODO
        return new ErrorDTO(null,null);
    }
    private ErrorDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        //TODO
        return new ErrorDTO(null,null);
    }
    private ErrorDTO handleLoginFailedException(LoginFailedException exception){
        //TODO
        return new ErrorDTO(null,null);
    }

}
