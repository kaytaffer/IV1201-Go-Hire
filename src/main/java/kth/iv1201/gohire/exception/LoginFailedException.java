package kth.iv1201.gohire.exception;

/**
 * Exception thrown when the logging in of a user to the application fails.
 */
public class LoginFailedException extends Exception{

    /**
     * Creates an instance of a throwable <code>LoginFailedException</code>.
     * @param message Exception message
     */
    public LoginFailedException(String message) {super(message);}
}
