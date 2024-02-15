package kth.iv1201.gohire.controller.util;

/**
 * Exception thrown when the <code>Logger</code> fails to write to the log.
 */
public class LoggerException extends Exception{

    /**
     * Creates an instance of a throwable <code>LoggerException</code>.
     * @param message Exception message
     */
    public LoggerException(String message) {super(message);}
}