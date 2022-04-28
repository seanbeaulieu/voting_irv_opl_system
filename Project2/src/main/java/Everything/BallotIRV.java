package Everything;

import Everything.Candidates.Candidate;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a ballot in an IRV based election
 * Written by Jasper Rutherford
 */
public class BallotIRV
{
    /**
     * the candidates in the order that this ballot voted for them
     */
    private ArrayList<Candidate> candidates;

    /**
     * This ballot's id
     */
    private int id;

    /**
     * Creates a BallotIRV object
     *
     * @param choice_arr populates the ballot with the orders in which the candidates are ranked
     * @param candidates the list of candidates in the order they were given in the input file
     * @param id         this ballot's id (the nth ballot in the input file has an id of n)
     */
    public BallotIRV(String[] choice_arr, ArrayList<Candidate> candidates, int id)
    {
        this.id = id;

        //initialize the queue
        this.candidates = new ArrayList<>();

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
     * Gets the next candidate from this ballot and removes it from this ballot's list
     *
     * @return a Candidate representing this ballot's current best choice
     */
    public Candidate nextCandidate()
    {
        Candidate out = null;
        if (candidates.size() != 0)
        {
            out = candidates.get(0);
            candidates.remove(0);
        }
        return out;
    }

    /**
     * gets this ballots id
     *
     * @return an int representing this ballot's id
     */
    public int getId()
    {
        return id;
    }

    /**
     * Creates a String representation of this BallotIRV.
     * @return a String representing the information stored on this BallotIRV
     */
    public String toString()
    {
        String out = "Ballot ID: " + id;
        for (Candidate cand : candidates)
        {
            out += "\n" + cand.getName();
        }

        return out;
    }

    /**
     * Gets whether or not this ballot is valid (AKA has at least half of the candidates ranked, rounded up from .5 or above to the next higher integer value)
     * @param numCandidates the number of candidates in this election
     * @return true if valid, false otherwise
     */
    public boolean isValid(int numCandidates)
    {
        return candidates.size() >= numCandidates / 2.0;
    }
}
