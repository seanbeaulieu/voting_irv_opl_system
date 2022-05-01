package Everything;

import Everything.Candidates.Candidate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Runs tests that involve the BallotIRV class
 * Written by Ann Huynh and Sean Beaulieu
 */
class BallotIRVTest
{

    @Nested
    @DisplayName("Test BallotIRV.nextCandidate()")
    class nextCandidate
    {
        private final Candidate john = new Candidate("John");
        private final Candidate hannah = new Candidate("Hannah");

        private ArrayList<Candidate> candidates = new ArrayList<>();

        @Test
        @DisplayName("Test From Zero Available Candidates")
        void testFromZeroCandidates()
        {
            BallotIRV ballot = new BallotIRV(new String[]{}, candidates, 0);
            assertNull(ballot.nextCandidate());
            assertNull(ballot.nextCandidate());
        }

        @Test
        @DisplayName("Test From Single Available Candidate")
        void testFromSingleCandidate()
        {
            candidates.add(john);

            BallotIRV ballot = new BallotIRV(new String[]{}, candidates, 1);
            assertNull(ballot.nextCandidate());

            ballot = new BallotIRV(new String[]{"1"}, candidates, 2);
            assertEquals("John", ballot.nextCandidate().getName());
            assertNull(ballot.nextCandidate());
        }

        @Test
        @DisplayName("Test From Multiple Available Candidates")
        void testFromMultipleCandidates()
        {
            candidates.add(john);
            candidates.add(hannah);

            BallotIRV ballot = new BallotIRV(new String[]{"", ""}, candidates, 3);
            assertNull(ballot.nextCandidate());

            ballot = new BallotIRV(new String[]{"", "1"}, candidates, 4);
            assertEquals("Hannah", ballot.nextCandidate().getName());
            assertNull(ballot.nextCandidate());

            ballot = new BallotIRV(new String[]{"1", ""}, candidates, 5);
            assertEquals("John", ballot.nextCandidate().getName());
            assertNull(ballot.nextCandidate());

            ballot = new BallotIRV(new String[]{"1", "2"}, candidates, 6);
            assertEquals("John", ballot.nextCandidate().getName());
            assertEquals("Hannah", ballot.nextCandidate().getName());
            assertNull(ballot.nextCandidate());

            ballot = new BallotIRV(new String[]{"2", "1"}, candidates, 7);
            assertEquals("Hannah", ballot.nextCandidate().getName());
            assertEquals("John", ballot.nextCandidate().getName());
            assertNull(ballot.nextCandidate());
        }
    }

    @Test
    @DisplayName("Test BallotIRV.getId()")
    void getId()
    {
        ArrayList<Candidate> candidates = new ArrayList<>();
        candidates.add(new Candidate("John"));
        candidates.add(new Candidate("Hannah"));

        BallotIRV b0 = new BallotIRV(new String[]{"", ""}, candidates, -1);
        BallotIRV b1 = new BallotIRV(new String[]{"", ""}, candidates, 0);
        BallotIRV b2 = new BallotIRV(new String[]{"", "1"}, candidates, 1);
        BallotIRV b3 = new BallotIRV(new String[]{"1", ""}, candidates, 2);
        BallotIRV b4 = new BallotIRV(new String[]{"1", "1"}, candidates, 3);

        assertEquals(-1, b0.getId());
        assertEquals(0, b1.getId());
        assertEquals(1, b2.getId());
        assertEquals(2, b3.getId());
        assertEquals(3, b4.getId());
    }

    @Nested
    @DisplayName("Test BallotIRV.toString()")
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
            assertEquals("Ballot ID: 0", ballot.toString());
        }

        @Test
        @DisplayName("Test Single Available Candidate")
        void testSingleCandidate()
        {
            candidates.add(john);
            BallotIRV ballot = new BallotIRV(new String[]{}, candidates, 1);
            assertEquals("Ballot ID: 1", ballot.toString());
            ballot = new BallotIRV(new String[]{"1"}, candidates, 2);
            assertEquals("Ballot ID: 2\nJohn", ballot.toString());
        }

        @Test
        @DisplayName("Test Multiple Available Candidates")
        void testMultipleCandidates()
        {
            candidates.add(john);
            candidates.add(hannah);
            BallotIRV ballot = new BallotIRV(new String[]{"", ""}, candidates, 3);
            assertEquals("Ballot ID: 3", ballot.toString());
            ballot = new BallotIRV(new String[]{"", "1"}, candidates, 4);
            assertEquals("Ballot ID: 4\nHannah", ballot.toString());
            ballot = new BallotIRV(new String[]{"1", ""}, candidates, 5);
            assertEquals("Ballot ID: 5\nJohn", ballot.toString());
            ballot = new BallotIRV(new String[]{"1", "2"}, candidates, 6);
            assertEquals("Ballot ID: 6\nJohn\nHannah", ballot.toString());
            ballot = new BallotIRV(new String[]{"2", "1"}, candidates, 7);
            assertEquals("Ballot ID: 7\nHannah\nJohn", ballot.toString());
        }
    }
}