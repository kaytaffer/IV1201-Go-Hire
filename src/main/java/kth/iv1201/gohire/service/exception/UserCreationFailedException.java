package kth.iv1201.gohire.service.exception;

/**
 * Exception thrown when the creation of a new user to the application fails.
 */
public class UserCreationFailedException extends Exception{

    /**
     * Creates an instance of a throwable <code>UserCreationFailedException</code>.
     * @param message Exception message
     */
    public UserCreationFailedException(String message) {super(message);}
}