package kth.iv1201.gohire.controller.util;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Class concerning static methods to write to error and event logs.
 */
public class Logger {

    private final static String ERROR_LOG_NAME = "errorlog.txt";
    private final static String EVENT_LOG_NAME = "eventlog.txt";

    /**
     * Logs the supplied exception to the errorlog, prepending the current time.
     * @param exceptionToLog The error to write to the log.
     */
    public static void logError(Exception exceptionToLog) {
        FileWriter exceptionLogger = makeWriter(ERROR_LOG_NAME);
        String message = "";
        //TODO decide how to log, stackTrace?
        makeLogEntry(exceptionLogger, message);
    }

    /**
     * Logs an event with the supplied message, prepending the current time of the event.
     * @param message The message to write to the log.
     */
    public static void logEvent(String message) {
        FileWriter eventLogger = makeWriter(EVENT_LOG_NAME);
        makeLogEntry(eventLogger, message);
    }

    //Creates a named FileWriter
    private static FileWriter makeWriter(String logName) {
        try {
            String date = LocalDate.now().toString();
            return new FileWriter(date + "_" + logName, true);
        } catch (IOException e) {
            //TODO don't infiniloop
            throw new RuntimeException(e);
        }
    }

    //Writes the supplied message to the supplied FileWriter and closes it
    private static void makeLogEntry(FileWriter fileWriter, String message) {
        String time = LocalDateTime.now().toString();
        message = time + ": " + message + "\n";
        try {
            fileWriter.append(message);
            fileWriter.close();
        } catch (IOException e) {
            //TODO don't infiniloop
            throw new RuntimeException(e);
        }
    }

}
