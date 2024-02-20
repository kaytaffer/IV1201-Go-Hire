package kth.iv1201.gohire.controller.util;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class LoggerTest {
    private static String filePathEventLog;
    private static String filePathErrorLog;
    private String message;

    @BeforeAll
    static void setUpAll() {
        String date = LocalDate.now().toString();
        filePathEventLog = date + "_" + "eventlog.txt";
        filePathErrorLog = date + "_" + "errorlog.txt";
    }
    @BeforeEach
    void setUp(){
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder("PERFORMING LOGGER TEST:");
        for(int i = 0; i < 100; i++){
            stringBuilder.append(random.nextInt(10));
        }
        message = stringBuilder.toString();
    }
    @AfterAll
    static void tearDownAll() {
    }
    @Test
    public void testIfLogEventLogsToFile() throws IOException {
        try {
            Logger.logEvent(message);
        } catch (LoggerException e) {
            fail("Failed to write to eventlog");
        }
        FileReader fileReader = new FileReader(filePathEventLog);
        BufferedReader reader = new BufferedReader(fileReader);
        boolean match = false;
        String line;
        while((line = reader.readLine()) != null){
            if(line.contains(message)){
                match = true;
            }
        }
        assertTrue(match, "Expected event message not written to eventlog");
        reader.close();
    }

    @Test
    public void testIfErrorLogsToFile() throws IOException {
        try {
            Logger.logError(new Exception(message));
        } catch (LoggerException e) {
            fail("Failed to write to Errorlog");
        }
        FileReader fileReader = new FileReader(filePathErrorLog);
        BufferedReader reader = new BufferedReader(fileReader);
        boolean match = false;
        String line;
        while((line = reader.readLine()) != null){
            if(line.contains(message)){
                match = true;
            }
        }
        assertTrue(match, "Expected Error message not written to Errorlog");
        reader.close();
    }

}
