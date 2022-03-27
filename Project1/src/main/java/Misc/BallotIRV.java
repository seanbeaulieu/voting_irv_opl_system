package Misc;

import Candidates.Candidate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Represents a ballot in an IRV based election
 * Written by Jasper Rutherford
 */
public class BallotIRV
{
    /**
     * the candidates in the order that this ballot voted for them
     */
    private PriorityQueue<Candidate> candidates;

    private int id;

    /**
     * Creates a BallotIRV object
     *
     * @param choice_arr populates the ballot with the orders in which the candidates are ranked
     * @param candidates the list of candidates in the order they were given in the input file
     * @param id this ballot's id (the nth ballot in the input file has an id of n)
     */
    public BallotIRV(String[] choice_arr, ArrayList<Candidate> candidates, int id)
    {
        this.id = id;

        //initialize the queue
        this.candidates = new PriorityQueue<>();

        //add candidates to the queue in the order they were voted for on the ballot
        for (int choice = 1; choice < choice_arr.length + 1; choice++)
        {
            int index = Arrays.asList(choice_arr).indexOf(choice + "");

            if (index != -1)
            {
                this.candidates.add(candidates.get(index));
            }
        }
    }

    /**
     * Gets the next candidate from this ballot
     * @return a Candidate representing this ballot's next best choice
     */
    public Candidate nextCandidate()
    {
        return candidates.poll();
    }

    /**
     * gets this ballots id
     * @return an int representing this ballot's id
     */
    public int getId()
    {
        return  id;
    }
}
