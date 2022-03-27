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
//        Name testClass = new Name();
//
//        testClass.setmName("Jasper");
//        assertEquals("Jasper", testClass.getmName());
//
//        testClass.setfName("Shey");
//        assertEquals("Shey", testClass.getfName());
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
//        setNumVotes testClass = new setVotes();
//
//        testClass.setNumVotes("6");
//        assertEquals("6", )
        testCandidate.setNumVotes(5);
        assertEquals(5, testCandidate.getNumVotes());
    }

    @Test
    void compareTo()
    {
        assertEquals(-1, testCandidate.compareTo(secondCandidate));
    }
}