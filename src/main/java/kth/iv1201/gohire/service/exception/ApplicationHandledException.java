package kth.iv1201.gohire.service.exception;

/**
 * Exception thrown when an application that has already been handled is ordered to change.
 */
public class ApplicationHandledException extends Exception{

    /**
     * Creates an instance of a throwable <code>ApplicationHandledException</code>.
     * @param message Exception message
     */
    public ApplicationHandledException(String message) {super(message);}
}
