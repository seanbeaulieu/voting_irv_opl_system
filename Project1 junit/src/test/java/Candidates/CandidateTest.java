package Candidates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CandidateTest
{

    @Test
    void getName()
    {
        Name testClass = new Name();

        testClass.setmName("Jasper");
        assertEquals("Jasper", testClass.getmName());

        testClass.setfName("Shey");
        assertEquals("Shey", testClass.getfName());
    }

    @Test
    void getNumVotes()
    {
        getVotes testClass = new getVotes();

        testClass.setmNumVotes("6");
        assertEquals("6",testClass.getVotes());
    }

    @Test
    void setNumVotes()
    {
        setNumVotes testClass = new setVotes();

        testClass.setNumVotes("6");
        assertEquals("6", )

    }

    @Test
    void compareTo()
    {
    }
}