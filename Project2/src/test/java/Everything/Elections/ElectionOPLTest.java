package Everything.Elections;

import Everything.FileHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Runs tests that involve the ElectionOPL class
 * Written by Ann Huynh and Sean Beaulieu
 */
class ElectionOPLTest {

    FileHandler fileHandler = new FileHandler();

    @Test
    @DisplayName("Test ElectionOPL.readInputs()")
    void readInputs() {

        fileHandler.addFilename("./testing/ElectionOPLTestFile.txt");
        ElectionOPL electionOPL = new ElectionOPL(fileHandler,true);
        assertTrue(electionOPL.readInputs());
    }

    @Test
    void calcResults() {

    }

    @Test
    void generateReport() {
    }

}