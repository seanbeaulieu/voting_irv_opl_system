package Candidates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CandidateOPLTest
{
    CandidateOPL candy = new CandidateOPL;
    candy.party = "D";

    @Test
    void getParty()
    {
        assertEquals("D", candy.getParty());
    }
}
