package Everything.Elections;

import Everything.FileHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class ElectionOPLTest {

    FileHandler fileHandler = new FileHandler();

    FileHandler fileHandlerIRV = new FileHandler();
    @Test
    @DisplayName("Test ElectionOPL.readInputs()")
    void readInputs() {

        fileHandler.addFilename("ElectionOPLTestFile.txt");
        ElectionOPL electionOPL = new ElectionOPL(fileHandler,true);
        assertTrue(electionOPL.readInputs());

        fileHandlerIRV.addFilename("irvtest.txt");
        ElectionOPL badelectionOPL = new ElectionOPL(fileHandlerIRV, true);
        assertFalse(badelectionOPL.readInputs());

    }

    @Test
    void calcResults() {

    }

    @Test
    void generateReport() {
    }

}