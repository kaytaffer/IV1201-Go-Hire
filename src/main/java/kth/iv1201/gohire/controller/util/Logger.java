package kth.iv1201.gohire.controller.util;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Utility class containing static methods to write to error and event logs.
 */
public class Logger {

    private final static String ERROR_LOG_NAME = "errorlog.txt";
    private final static String EVENT_LOG_NAME = "eventlog.txt";

    /**
     * Logs the supplied exception to the errorlog, prepending the current time.
     * @param exceptionToLog The error to write to the log.
     * @throws LoggerException if there is a problem with logging an event.
     */
    public static void logError(Exception exceptionToLog) throws LoggerException {
        FileWriter exceptionLogger = makeWriter(ERROR_LOG_NAME);
        String message = Arrays.toString(exceptionToLog.getStackTrace());
        makeLogEntry(exceptionLogger, message);
    }

    /**
     * Logs an event with the supplied message, prepending the current time of the event.
     * @param message The message to write to the log.
     * @throws LoggerException if there is a problem with logging an event.
     */
    public static void logEvent(String message) throws LoggerException {
        FileWriter eventLogger = makeWriter(EVENT_LOG_NAME);
        makeLogEntry(eventLogger, message);
    }

    //Creates a named FileWriter
    private static FileWriter makeWriter(String logName) throws LoggerException {
        try {
            String date = LocalDate.now().toString();
            return new FileWriter(date + "_" + logName, true);
        } catch (IOException e) {
            throw new LoggerException("Logger failed to write to log. Caused by: \n" + Arrays.toString(e.getStackTrace()));
        }
    }

    //Writes the supplied message to the supplied FileWriter and closes it
    private static void makeLogEntry(FileWriter fileWriter, String message) throws LoggerException {
        String time = LocalDateTime.now().toString();
        message = time + ": " + message + "\n";
        try {
            fileWriter.append(message);
            fileWriter.close();
        } catch (IOException e) {
            throw new LoggerException("Logger failed connect to log file. Caused by: \n" + Arrays.toString(e.getStackTrace()));
        }
    }

}
