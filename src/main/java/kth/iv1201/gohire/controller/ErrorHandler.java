package kth.iv1201.gohire.controller;

import kth.iv1201.gohire.DTO.ErrorDTO;
import kth.iv1201.gohire.controller.util.Logger;
import kth.iv1201.gohire.controller.util.LoggerException;
import kth.iv1201.gohire.service.exception.UserCreationFailedException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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
     * @return ErrorDTO containing <code>ErrorType</code> and appropriate message to client, or null if causing
     *         <code>Exception</code> isn't meant to be translated to client.
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorDTO> handleException (Exception exception) throws LoggerException {

        if (exception instanceof UserCreationFailedException) {
            return handleUserCreationFailedException((UserCreationFailedException) exception);
        } else if (exception instanceof MethodArgumentNotValidException) {
            return handleMethodArgumentNotValidException((MethodArgumentNotValidException) exception);
        } else if (exception instanceof BadCredentialsException) {
            return handleBadCredentialsException((BadCredentialsException) exception);
        } else if (exception instanceof LoggerException) {
            return handleLoggerException((LoggerException) exception);
        } else
        return handleOtherExceptions(exception);
    }

    private ResponseEntity<ErrorDTO> handleUserCreationFailedException(UserCreationFailedException exception){
        return new ResponseEntity<> (new ErrorDTO(ErrorType.USERNAME_ALREADY_EXISTS,exception.getMessage()),
                HttpStatus.CONFLICT);
    }

    private ResponseEntity<ErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        return new ResponseEntity<> (new ErrorDTO(ErrorType.USER_INPUT_ERROR, exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorDTO> handleBadCredentialsException(BadCredentialsException exception){
        return new ResponseEntity<> (new ErrorDTO(ErrorType.LOGIN_FAIL,exception.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<ErrorDTO> handleLoggerException(LoggerException exception) {
        System.out.println(exception.toString());
        return null;
    }

    //Generic handling and logging for all unspecified exceptions
    private ResponseEntity<ErrorDTO> handleOtherExceptions(Exception exception) throws LoggerException {
        Logger.logError(exception);
        return new ResponseEntity<> (new ErrorDTO(ErrorType.SERVER_INTERNAL,exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
