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
    @DisplayName("Test ElectionIRV.getCandidatesFromIRVLine")
    void getCandidatesFromString() {

        FileHandler fh = new FileHandler("irvtest.txt");

        final ElectionIRV election = new ElectionIRV(fh, false);
        String candidate_line = "Rosen,Kleinberg,Chou,Royce";
        boolean test = election.getCandidatesFromIRVLine(candidate_line);

        assertEquals(4, election.candidates.toArray().length);

    }

    @Test
    void generateReport()
    {
    }

    @Nested
    @DisplayName("Test ElectionIRV.getLowestCandidate()")
    class getLowest {

        private CandidateIRV rosen = new CandidateIRV("Rosen(D)");
        private CandidateIRV klein = new CandidateIRV("Kleinberg(R)");
        private CandidateIRV chou = new CandidateIRV("Chou(I)");

        FileHandler fileHandler = new FileHandler("./testing/irvtest.txt");
        ElectionIRV election = new ElectionIRV(fileHandler, false);

        election.candidates.add(rosen);
        election.candidates.add(klein);
        election.candidates.add(chou);

        BallotIRV ballot1 = new BallotIRV(new String[]{"1", "2", "3", "4"}, election.candidates, 0);
        BallotIRV ballot2 = new BallotIRV(new String[]{"1", "2", "3", "4"}, election.candidates, 0);
        BallotIRV ballot3 = new BallotIRV(new String[]{"1", "2", "3", "4"}, election.candidates, 0);

        rosen.addBallot(ballot1);
        rosen.addBallot(ballot2);
        klein.addBallot(ballot3);





    }

    @Nested
    @DisplayName("Test ElectionIRV.getNextCandidate()")
    class getNextCandidate
    {
        private Candidate rosen = new CandidateIRV("Rosen(D)");
        private Candidate klein = new CandidateIRV("Kleinberg(R)");
        private Candidate chou = new CandidateIRV("Chou(I)");
        private Candidate royce = new CandidateIRV("Royce(L)");

        FileHandler fileHandler = new FileHandler("./testing/irvtest.txt");
        ElectionIRV election = new ElectionIRV(fileHandler, false);

        @Test
        @DisplayName("Test Many Valid Candidates")
        void testManyValidCandidates()
        {

            ArrayList<Candidate> ballotCandidates = new ArrayList<>();

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