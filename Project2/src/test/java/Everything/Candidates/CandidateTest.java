package Everything.Candidates;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Runs tests involving the Candidate Class
 * Written by Ann Huynh and Sean Beaulieu
 */
class CandidateTest {

    @Test
    @DisplayName("Test Candidate.getName()")
    void getName() {
        final Candidate john = new Candidate("John");
        assertEquals("John", john.getName());
    }

    @Nested
    @DisplayName("Test Candidate.getNumVotes() and Candidate.setNumVotes()")
        class votes {

            final Candidate john = new Candidate("John");

            @Test
            @DisplayName("Test to set NumVotes to 5")
            void setNumVotes() {

                john.setNumVotes(5);
                int votes = john.getNumVotes();
                assertEquals(votes, john.getNumVotes());
            }

            @Test
            @DisplayName("Test to get NumVotes")
            void getNumVotes() {
                john.setNumVotes(5);
                assertEquals(5, john.getNumVotes());
            }
        }

    @Nested
    @DisplayName("Test Candidate.compareTo()")
    class compareTo {

        final Candidate john = new Candidate("John");
        final Candidate mary = new Candidate("Mary");

        @Test
        @DisplayName("Test With Mary Having More Votes")
        void testWithMoreVotes() {
            john.setNumVotes(1);
            mary.setNumVotes(2);

            assertEquals(-1, john.compareTo(mary));
        }

        @Test
        @DisplayName("Test with Equal Votes")
        void testWithEqualVotes() {
            john.setNumVotes(1);
            mary.setNumVotes(1);

            assertEquals(0, john.compareTo(mary));
        }

        @Test
        @DisplayName("Test with Mary Having Less Votes")
        void testWithLessVotes() {
            john.setNumVotes(2);
            mary.setNumVotes(1);

            assertEquals(1, john.compareTo(mary));
        }
    }

}
