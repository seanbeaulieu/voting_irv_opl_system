package Everything.Candidates;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

public class CandidateOPLTest {

    @Test
    @DisplayName("Test Get Party")
    void getParty() {
        final CandidateOPL john = new CandidateOPL("John", "D");
        assertEquals("D", john.getParty());
    }

}
