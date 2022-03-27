package Candidates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CandidateTest
{

    Candidate testCandidate = new Candidate;
    testCandidate.name = "Shey";
    testCandidate.numVotes = 1;

    Candidate secondCandidate = new Candidate;
    secondCandidate.name = "Ann";
    secondCandidate.numVotes = 7;

    @Test
    void getName()
    {
        assertEquals("Shey(D)", testCandidate.getName());
    }

    @Test
    void getNumVotes()
    {
        assertEquals(1, testCandidate.getNumVotes());
    }

    @Test
    void setNumVotes()
    {
        testCandidate.setNumVotes(5);
        assertEquals(5, testCandidate.getNumVotes());
    }

    @Test
    void compareTo()
    {
        assertEquals(-1, testCandidate.compareTo(secondCandidate));
    }
}
