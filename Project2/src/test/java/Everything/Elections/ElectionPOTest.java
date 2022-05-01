package Everything.Elections;

import Everything.Candidates.Candidate;
import Everything.FileHandler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Runs tests that involve the ElectionPO class
 * Written by Ann Huynh and Sean Beaulieu
 */
class ElectionPOTest {

    @Test
    @DisplayName("Test ElectionPO.readInputs()")
    void readInputsPO()
    {
        FileHandler fileHandler = new FileHandler();
        ElectionPO electionPO = new ElectionPO(fileHandler,true);
        fileHandler.addFilename("./testing/testPO.csv");

        assertTrue(electionPO.readInputs());
    }

    @Test
    @DisplayName("Test to Retrieve A Single Winner VIA csv file using PO")
    void calculatePO() {
        FileHandler fileHandler = new FileHandler();
        String file1 = "./testing/testPO.csv";
        fileHandler.addFilename(file1);
        Election election;
        election = new ElectionPO(fileHandler, false);
        Candidate pike = new Candidate("[Pike,D]");

        if (election.readInputs() == true) {
            election.calcResults();
        }

        final ArrayList<Candidate> candidates = new ArrayList<>();
        candidates.add(pike);

        assertEquals(1, election.getWinners().size());
    }
}
