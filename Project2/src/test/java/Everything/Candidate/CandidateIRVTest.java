package Everything;

import Everything.Candidates.Candidate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CandidateIRVTest {

    @Nested
    @DisplayName("Test CandidateIRV.getBallots()")
    class getBallots {

        private final CandidateIRV john = new CandidateIRV("John");
        private final BallotIRV test_ballot1 = new BallotIRV(new String[]{"", ""}, candidates, 0);

        @Test
        @DisplayName("Test From Zero Available Ballots")
        void testFromZeroBallots() {
            assertEquals(null, john.getBallots());
        }

        @Test
        @DisplayName("Test From One Available Ballot")
        void testFromOneBallot() {
            priate ArrayList<ballot> ballots = new ArrayList<>();
            john.addBallot(test_ballot1);
            ballots.add(test_ballot1);
            assertEquals(john.getBallots(), ballots);
        }

        @Test
        @DisplayName("Test From Multiple Available Ballots")
        void testFromMultipleBallots() {
            priate ArrayList<ballot> ballots = new ArrayList<>();

            private final BallotIRV test_ballot1 = new BallotIRV(new String[]{"", ""}, candidates, 0);
            private final BallotIRV test_ballot2 = new BallotIRV(new String[]{"", ""}, candidates, 1);
            private final BallotIRV test_ballot3 = new BallotIRV(new String[]{"", ""}, candidates, 2);
            private final BallotIRV test_ballot4 = new BallotIRV(new String[]{"", ""}, candidates, 3);

            ballots.add(test_ballot1);
            ballots.add(test_ballot2);
            ballots.add(test_ballot3);
            ballots.add(test_ballot4);

            assertEquals(john.getBallots(), ballots);
        }

    }

    @Nested
    @DisplayName("Test CandidateIRV.getBallots()")
    class addBallot {

        priate ArrayList<ballot> ballots = new ArrayList<>();
        private final CandidateIRV john = new CandidateIRV("John");

        @Test
        @DisplayName("Test to add a null ballot")
        void testToAddNullBallot() {
            private final BallotIRV test_ballot1 = new BallotIRV(null);
            john.addBallot(test_ballot1);

            assertEquals(ballot, john.ballots);
        }
        @Test
        @DisplayName("Test to add a signle ballot")
        void testToAddSingleBallot() {
            private final BallotIRV test_ballot1 = new BallotIRV(new String[]{"", ""}, candidates, 0);
            priate ArrayList<ballot> ballots = new ArrayList<>();

            john.addBallot(test_ballot1);
            ballots.add(test_ballot1);

            assertEquals(ballots, john.ballots);
        }
        @Test
        @DisplayName("Test to add multiple ballots")
        void testToAddMultipleBallots() {
            private final BallotIRV test_ballot1 = new BallotIRV(new String[]{"", ""}, candidates, 0);
            private final BallotIRV test_ballot2 = new BallotIRV(new String[]{"", ""}, candidates, 1);
            private final BallotIRV test_ballot3 = new BallotIRV(new String[]{"", ""}, candidates, 2);

            priate ArrayList<ballot> ballots = new ArrayList<>();

            john.addBallot(test_ballot1);
            john.addBallot(test_ballot2);
            john.addBallot(test_ballot3);

            ballots.add(test_ballot1);
            ballots.add(test_ballot2);
            ballots.add(test_ballot3);

            assertEquals(ballots, john.ballots);
        }
    }

    @Test
    @DisplayName("Test to Return Number of Votes")
    class getNumVotes() {
        priate ArrayList<ballot> ballots = new ArrayList<>();
        private final BallotIRV test_ballot1 = new BallotIRV(new String[]{"", ""}, candidates, 0);
        private final CandidateIRV john = new CandidateIRV("John");

        john.addBallot(test_ballot1);
        assertEquals(1, john.getNumVotes());
    }
}