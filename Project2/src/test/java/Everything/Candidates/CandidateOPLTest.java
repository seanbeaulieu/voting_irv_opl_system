package Everything.Candidates;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

/**
 * Runs tests that involve the CandidateOPL class
 * Written by Ann Huynh and Sean Beaulieu
 */
class CandidateOPLTest {

    @Test
    @DisplayName("Test Get Party")
    void getParty() {
        final CandidateOPL john = new CandidateOPL("John", "D");
        assertEquals("D", john.getParty());
    }

}
