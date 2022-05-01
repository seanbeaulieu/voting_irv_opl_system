package Everything.Candidates;

import Everything.BallotIRV;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Runs tests involving the CandidateIRV class
 * Written by Ann Huynh and Sean Beaulieu
 */
class CandidateIRVTest {

    @Nested
    @DisplayName("Test CandidateIRV.getBallots()")
    class getBallots {

        private ArrayList<Candidate> candidates = new ArrayList<>();
        private final CandidateIRV john = new CandidateIRV("John");
        private final BallotIRV test_ballot1 = new BallotIRV(new String[]{"", ""}, candidates, 0);

        @Test
        @DisplayName("Test From Zero Available Ballots")
        void testFromZeroBallots() {
            ArrayList<BallotIRV> no_ballots = new ArrayList<>();
            assertEquals(no_ballots, john.getBallots());
        }

        @Test
        @DisplayName("Test From One Available Ballot")
        void testFromOneBallot() {
            ArrayList<BallotIRV> ballots = new ArrayList<>();
            john.addBallot(test_ballot1);
            ballots.add(test_ballot1);
            assertEquals(john.getBallots(), ballots);
        }

        @Test
        @DisplayName("Test From Multiple Available Ballots")
        void testFromMultipleBallots() {
            ArrayList<BallotIRV> ballots = new ArrayList<>();

            final BallotIRV test_ballot1 = new BallotIRV(new String[]{"", ""}, candidates, 0);
            final BallotIRV test_ballot2 = new BallotIRV(new String[]{"", ""}, candidates, 1);
            final BallotIRV test_ballot3 = new BallotIRV(new String[]{"", ""}, candidates, 2);
            final BallotIRV test_ballot4 = new BallotIRV(new String[]{"", ""}, candidates, 3);

            final CandidateIRV john = new CandidateIRV("John");

            john.addBallot(test_ballot1);
            john.addBallot(test_ballot2);
            john.addBallot(test_ballot3);
            john.addBallot(test_ballot4);

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

        private ArrayList<Candidate> candidates = new ArrayList<>();
        ArrayList<BallotIRV> ballots = new ArrayList<>();

        @Test
        @DisplayName("Test to add a single ballot")
        void testToAddSingleBallot() {
            final BallotIRV test_ballot1 = new BallotIRV(new String[]{"", ""}, candidates, 0);
            ArrayList<BallotIRV> ballots1 = new ArrayList<>();
            final CandidateIRV john = new CandidateIRV("John");

            john.addBallot(test_ballot1);
            ballots1.add(test_ballot1);

            assertEquals(ballots1, john.getBallots());
        }
        @Test
        @DisplayName("Test to add multiple ballots")
        void testToAddMultipleBallots() {
            final BallotIRV test_ballot1 = new BallotIRV(new String[]{"", ""}, candidates, 0);
            final BallotIRV test_ballot2 = new BallotIRV(new String[]{"", ""}, candidates, 1);
            final BallotIRV test_ballot3 = new BallotIRV(new String[]{"", ""}, candidates, 2);

            ArrayList<BallotIRV> ballots1 = new ArrayList<>();
            final CandidateIRV john = new CandidateIRV("John");

            john.addBallot(test_ballot1);
            john.addBallot(test_ballot2);
            john.addBallot(test_ballot3);

            ballots.add(test_ballot1);
            ballots.add(test_ballot2);
            ballots.add(test_ballot3);

            assertEquals(ballots, john.getBallots());
        }
    }

    @Test
    @DisplayName("Test to Return Number of Votes")
    void getNumVotes() {
        ArrayList<BallotIRV> ballots = new ArrayList<>();
        ArrayList<Candidate> candidates = new ArrayList<>();
        final CandidateIRV john = new CandidateIRV("John");
        final BallotIRV test_ballot1 = new BallotIRV(new String[]{"", ""}, candidates, 0);

        john.addBallot(test_ballot1);
        int expect = john.getNumVotes();

        assertEquals(1, expect);
    }
}