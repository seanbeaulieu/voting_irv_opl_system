package Everything.Elections;

import Everything.Candidates.Candidate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Runs tests that involve the Election class
 * Written by Ann Huynh and Sean Beaulieu
 */
class ElectionTest {

    @Test
    @DisplayName("Test getWinners")
    void getWinners() {

        final Election test_election = new Election(null, false);
        final Candidate john = new Candidate("John");
        test_election.winners.add(john);
        final ArrayList<Candidate> candidates = new ArrayList<>();
        candidates.add(john);
        assertEquals(candidates, test_election.getWinners());

    }

}
