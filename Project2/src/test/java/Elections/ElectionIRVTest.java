package Elections;

import Candidates.Candidate;
import Candidates.CandidateIRV;
import Misc.BallotIRV;
import Misc.FileHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ElectionIRVTest
{

    @Test
    void readInputs()
    {
    }

    @Test
    void calcResults()
    {
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
        private Candidate anna = new CandidateIRV("Anna");
        private Candidate dave = new CandidateIRV("Dave");
        private Candidate john = new CandidateIRV("John");


        private ArrayList<Candidate> ballotCandidates = new ArrayList<>();

        FileHandler fileHandler = new FileHandler("./testing/irvtest.txt");
        ElectionIRV election = new ElectionIRV(fileHandler, false);

        @Test
        @DisplayName("Test Many Valid Candidates")
        void testManyValidCandidates()
        {
            ballotCandidates.add(rosen);
            ballotCandidates.add(klein);
            ballotCandidates.add(chou);
            ballotCandidates.add(royce);

            BallotIRV ballot1 = new BallotIRV(new String[]{"1", "2", "3", "4"}, ballotCandidates, 0);

            ballotCandidates.add(anna);
            ballotCandidates.add(dave);
            ballotCandidates.add(john);
            BallotIRV ballot2 = new BallotIRV(new String[]{"1", "2", "3", "4", "2", "3", "4"}, ballotCandidates, 0);

            assertTrue(election.readInputs(), "File Issue");

        }
    }
}