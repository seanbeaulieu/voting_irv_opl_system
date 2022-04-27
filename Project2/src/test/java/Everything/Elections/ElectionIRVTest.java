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
    @DisplayName("Test ElectionIRV.readInputs()")
    void readInputs()
    {

        FileHandler fileHandler = new FileHandler();
        fileHandler.addFilename("irvtest.txt");
        ElectionIRV electionIRV = new ElectionIRV(fileHandler,true);
        assertTrue(electionIRV.readInputs());

    }

    @Test
    void calcResults()
    {

        FileHandler fileHandler = new FileHandler();
        fileHandler.addFilename("irvtest.txt");
        ElectionIRV electionIRV = new ElectionIRV(fileHandler, true);

        CandidateIRV rosen = new CandidateIRV("Rosen(D)");
        CandidateIRV klein = new CandidateIRV("Kleinberg(R)");
        CandidateIRV chou = new CandidateIRV("Chou(I)");
        CandidateIRV royce = new CandidateIRV("Royce(L)");

        electionIRV.candidates.add(rosen);
        electionIRV.candidates.add(klein);
        electionIRV.candidates.add(chou);
        electionIRV.candidates.add(royce);

        BallotIRV ballot1 = new BallotIRV(new String[]{"1", "3", "4", "2"}, electionIRV.candidates, 0);
        BallotIRV ballot2 = new BallotIRV(new String[]{"1", "", "2", ""}, electionIRV.candidates, 1);
        BallotIRV ballot3 = new BallotIRV(new String[]{"1", "2", "3", ""}, electionIRV.candidates, 2);
        BallotIRV ballot4 = new BallotIRV(new String[]{"3", "2", "1", "4"}, electionIRV.candidates, 3);
        BallotIRV ballot5 = new BallotIRV(new String[]{"", "", "1", "2"}, electionIRV.candidates, 4);
        BallotIRV ballot6 = new BallotIRV(new String[]{"", "", "", "1"}, electionIRV.candidates, 5);

        electionIRV.unassignedBallots.add(ballot1);
        electionIRV.unassignedBallots.add(ballot2);
        electionIRV.unassignedBallots.add(ballot3);
        electionIRV.unassignedBallots.add(ballot4);
        electionIRV.unassignedBallots.add(ballot5);
        electionIRV.unassignedBallots.add(ballot6);

        electionIRV.calcResults();

        final ArrayList<CandidateIRV> candidates = new ArrayList<>();
        candidates.add(rosen);

        assertEquals(candidates, electionIRV.winners);

    }

    @Test
    @DisplayName("Test ElectionIRV.getCandidatesFromIRVLine")
    void getCandidatesFromString() {

        FileHandler fh = new FileHandler();
        fh.addFilename("irvtest.txt");

        final ElectionIRV election = new ElectionIRV(fh, false);
        String candidate_line = "Rosen,Kleinberg,Chou,Royce";
        boolean test = election.getCandidatesFromIRVLine(candidate_line);

        assertEquals(4, election.candidates.toArray().length);

    }

    @Test
    void generateReport()
    {
    }

    @Test
    @DisplayName("Test ElectionIRV.getLowestCandidate()")
    void getLowest() {

        CandidateIRV rosen = new CandidateIRV("Rosen(D)");
        CandidateIRV klein = new CandidateIRV("Kleinberg(R)");
        CandidateIRV chou = new CandidateIRV("Chou(I)");

        FileHandler fileHandler = new FileHandler();
        fileHandler.addFilename("irvtest.txt");
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