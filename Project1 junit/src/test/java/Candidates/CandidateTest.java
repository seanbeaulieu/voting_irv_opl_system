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
<<<<<<< HEAD
       /* Name testClass = new Name();

        testClass.setmName("Jasper");
        assertEquals("Jasper", testClass.getmName());

        testClass.setfName("Shey");
        assertEquals("Shey", testClass.getfName());*/
=======
//        Name testClass = new Name();
//
//        testClass.setmName("Jasper");
//        assertEquals("Jasper", testClass.getmName());
//
//        testClass.setfName("Shey");
//        assertEquals("Shey", testClass.getfName());
        assertEquals("Shey(D)", testCandidate.getName());
>>>>>>> 1f451168370976755fb16b43645ddaacc3b26ab7
    }

    @Test
    void getNumVotes()
    {
<<<<<<< HEAD
       /* numVotes testClass = new numVotes();

        testClass.setmNumVotes("6");
        assertEquals("6",testClass.numVotes());*/
=======
        assertEquals(1, testCandidate.getNumVotes());
>>>>>>> 1f451168370976755fb16b43645ddaacc3b26ab7
    }

    @Test
    void setNumVotes()
    {
<<<<<<< HEAD

=======
//        setNumVotes testClass = new setVotes();
//
//        testClass.setNumVotes("6");
//        assertEquals("6", )
        testCandidate.setNumVotes(5);
        assertEquals(5, testCandidate.getNumVotes());
>>>>>>> 1f451168370976755fb16b43645ddaacc3b26ab7
    }

    @Test
    void compareTo()
    {
        assertEquals(-1, testCandidate.compareTo(secondCandidate)); 
    }
}