package Candidates;

import Misc.BallotIRV;

import java.util.ArrayList;

/**
 * Represents a Candidate in an IRV based Election
 * Written by Jasper Rutherford
 */
public class CandidateIRV extends Candidate
{
    /**
     * The list of all BallotIRV's which are currently assigned to this CandidateIRV
     */
    ArrayList<BallotIRV> ballots;

    /**
     * Creates a CandidateIRV
     * @param name the name of this CandidateIRV
     */
    public CandidateIRV(String name)
    {
        super(name);

        ballots = new ArrayList<>();
    }

    /**
     * Gets the list of BallotIRV's who are assigned to this CandidateIRV
     * @return an ArrayList of BallotIRV's
     */
    public ArrayList<BallotIRV> getBallots()
    {
        return ballots;
    }

    /**
     * adds the provided ballot to this candidate's list of ballots
     * @param ballot the ballotIRV to be added to this candidate's list of ballots
     */
    public void addBallot(BallotIRV ballot)
    {
        ballots.add(ballot);
    }

    /**
     * Gets this Candidate's number of votes
     *
     * @return an int representing this Candidate's number of votes
     */
    public int getNumVotes()
    {
        return ballots.size();
    }

    /**
     * Checks whether this CandidateIRV has the same name as the supplied CandidateIRV
     *
     * @param other
     * @return true if the candidates are equal to all the other candidates, false if not
     */
    public boolean equals(Object other) {
        if (other instanceof CandidateIRV){
            return this.getName().equals(((CandidateIRV) other).getName());
        }
        else{
            return false;
        }
    }

}