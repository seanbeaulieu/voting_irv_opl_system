package Candidates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CandidateIRVTest
{
    Candidate testCandidate = new Candidate("Shey(D)");

    @Test
    void hasWon()
    {
        assertEquals(true, Candidate.get(0).hasWon());
    }

    @Test
    void hasLost()
    {
        assertEquals(true, Candidate.get(2).hasLost());
    }

    @Test
    void getBallots()
    {
        assertEquals(ballots, getBallots());
    }

    @Test
    void addBallot()
    {
        assertEquals(true, ballots.contains(ballot));
    }

    @Test
    void getNumVotes()
    {
        assertEquals(numVotes, getNumVotes());
    }
}
