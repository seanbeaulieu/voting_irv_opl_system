package Everything.Elections;

import Everything.Candidates.Candidate;
import Everything.Candidates.CandidateIRV;
import Everything.Elections.ElectionIRV;
import Everything.FileHandler;
import Everything.BallotIRV;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Runs tests that involve the ElectionIRV class
 * Written by Ann Huynh and Sean Beaulieu
 */
class ElectionIRVTest
{

    @Test
    @DisplayName("Test ElectionIRV.readInputs()")
    void readInputs()
    {
        FileHandler fileHandler = new FileHandler();
        ElectionIRV electionIRV = new ElectionIRV(fileHandler,true);
        fileHandler.addFilename("./testing/irvtest.txt");

        assertTrue(electionIRV.readInputs());
    }

    @Test
    @DisplayName("Test CalcResults from Single File")
    void calcResults()
    {
        FileHandler fileHandler = new FileHandler();
        String file1 = "./testing/irvtest.txt";
        fileHandler.addFilename(file1);
        Election election;
        election = new ElectionIRV(fileHandler, false);
        CandidateIRV rosen = new CandidateIRV("Rosen(D)");

        if (election.readInputs() == true) {
            election.calcResults();
        }

        final ArrayList<CandidateIRV> candidates = new ArrayList<>();
        candidates.add(rosen);

        assertEquals(candidates, election.getWinners());
    }

    @Test
    @DisplayName("Test Calculate Results from Multiple Files")
    void calcResultsFromMultiple() {
        FileHandler fileHandler = new FileHandler();
        String file1 = "./testing/irvm1.csv";
        String file2 = "./testing/irvm2.csv";
        String file3 = "./testing/irvm3.csv";
        fileHandler.addFilename(file1);
        fileHandler.addFilename(file2);
        fileHandler.addFilename(file3);
        Election election;
        election = new ElectionIRV(fileHandler, false);
        CandidateIRV rosen = new CandidateIRV("Rosen(D)");
        CandidateIRV chou = new CandidateIRV("Chou(I)");
        CandidateIRV royce = new CandidateIRV("Royce(L");

        final ArrayList<CandidateIRV> candidates = new ArrayList<>();
        candidates.add(rosen);
        candidates.add(chou);
        candidates.add(royce);

        if (election.readInputs() == true) {
            election.calcResults();
        }

        assertEquals(3, election.getWinners().size());
    }

    @Test
    @DisplayName("Test to Filter Out Ballots With Half Ranked")
    void filterBallots() {
        FileHandler fileHandler = new FileHandler();
        String file1 = "./testing/irvtest.txt";
        fileHandler.addFilename(file1);
        ElectionIRV election = new ElectionIRV(fileHandler, false);

        boolean test = election.readInputs();

        assertEquals(5, election.unassignedBallots.size());
    }

    @Test
    @DisplayName("Test ElectionIRV.getCandidatesFromIRVLine")
    void getCandidatesFromString() {

        FileHandler fh = new FileHandler();

        final ElectionIRV election = new ElectionIRV(fh, false);
        String candidate_line = "Rosen,Kleinberg,Chou,Royce";
        boolean test = election.getCandidatesFromIRVLine(candidate_line);

        assertEquals(4, election.tempCandidates.toArray().length);

    }

    @Test
    void generateReport()
    {
    }


    @Nested
    @DisplayName("Test ElectionIRV.getNextCandidate()")
    class getNextCandidate
    {
        private Candidate rosen = new CandidateIRV("Rosen(D)");
        private Candidate klein = new CandidateIRV("Kleinberg(R)");
        private Candidate chou = new CandidateIRV("Chou(I)");
        private Candidate royce = new CandidateIRV("Royce(L)");

        FileHandler fileHandler = new FileHandler();

        ElectionIRV election = new ElectionIRV(fileHandler, false);

        @Test
        @DisplayName("Test Many Valid Candidates")
        void testManyValidCandidates()
        {

            ArrayList<Candidate> ballotCandidates = new ArrayList<>();
            fileHandler.addFilename("irvtest.txt");
            ballotCandidates.add(rosen);
            ballotCandidates.add(klein);
            ballotCandidates.add(chou);
            ballotCandidates.add(royce);

            election.candidates.add(rosen);
            election.candidates.add(klein);
            election.candidates.add(chou);
            election.candidates.add(royce);

            BallotIRV ballot1 = new BallotIRV(new String[]{"1", "2", "3", "4"}, ballotCandidates, 0);

            CandidateIRV first = election.getNextCandidate(ballot1);
            CandidateIRV second = election.getNextCandidate(ballot1);

            assertEquals(klein, second);

        }
    }
}