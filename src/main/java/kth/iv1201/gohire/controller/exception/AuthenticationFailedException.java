package kth.iv1201.gohire.controller.exception;


/**
 * Exception thrown when authentication fails
 */
public class AuthenticationFailedException extends Exception{

    /**
     * Creates an instance of a throwable <code>AuthenticationFailedException</code>.
     * @param message Exception message
     */
    public AuthenticationFailedException(String message) {super(message);}
}
