package Misc;

import Candidates.Candidate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BallotIRVTest
{

    @Test
    void nextCandidate()
    {
    }

    @Test
    void getId()
    {
    }

    @Nested
    @DisplayName("Tests the BallotIRV.toString() method")
    class testToString
    {
        private final Candidate john = new Candidate("John");
        private final Candidate hannah = new Candidate("Hannah");

        private ArrayList<Candidate> candidates = new ArrayList<>();

        @Test
        @DisplayName("Test Zero Available Candidates")
        void testZeroCandidates()
        {
            BallotIRV ballot = new BallotIRV(new String[]{}, candidates, 0);
            Assertions.assertEquals("Ballot ID: 0", ballot.toString());
        }

        @Test
        @DisplayName("Test Single Available Candidate")
        void testSingleCandidate()
        {
            candidates.add(john);
            BallotIRV ballot = new BallotIRV(new String[]{}, candidates, 1);
            Assertions.assertEquals("Ballot ID: 1", ballot.toString());
            ballot = new BallotIRV(new String[]{"1"}, candidates, 2);
            Assertions.assertEquals("Ballot ID: 2\nJohn", ballot.toString());
        }

        @Test
        @DisplayName("Test Multiple Available Candidates")
        void testMultipleCandidates()
        {
            candidates.add(john);
            candidates.add(hannah);
            BallotIRV ballot = new BallotIRV(new String[]{"", ""}, candidates, 3);
            Assertions.assertEquals("Ballot ID: 3", ballot.toString());
            ballot = new BallotIRV(new String[]{"", "1"}, candidates, 4);
            Assertions.assertEquals("Ballot ID: 4\nHannah", ballot.toString());
            ballot = new BallotIRV(new String[]{"1", ""}, candidates, 5);
            Assertions.assertEquals("Ballot ID: 5\nJohn", ballot.toString());
            ballot = new BallotIRV(new String[]{"1", "2"}, candidates, 6);
            Assertions.assertEquals("Ballot ID: 6\nJohn\nHannah", ballot.toString());
            ballot = new BallotIRV(new String[]{"2", "1"}, candidates, 7);
            Assertions.assertEquals("Ballot ID: 7\nHannah\nJohn", ballot.toString());
        }
    }
}