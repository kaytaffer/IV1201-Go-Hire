package kth.iv1201.gohire.service.exception;

/**
 * Exception thrown when requesting a user which does not exist in the database.
 */
public class UserNotFoundException extends Exception{

    /**
     * Creates an instance of a throwable <code>UserNotFoundException</code>.
     * @param message Exception message.
     */
    public UserNotFoundException(String message) {super(message);}
}
