import java.util.ArrayList;

public class CandidateIRV extends Candidate
{
    /**
     * Whether or not the CandidateIRV has won a seat
     */
    private boolean won;

    /**
     * Whether or not the CandidateIRV's ballots have been reallocated/whether or not this CandidateIRV has lost the election
     */
    private boolean lost;

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

        won = false;
        lost = false;
        ballots = new ArrayList<>();
    }

    /**
     * Whether or not this CandidateIRV has won a seat.
     * @return a boolean representing whether or not this CandidateIRV has won a seat
     */
    public boolean hasWon()
    {
        return won;
    }

    /**
     * Whether or not this CandidateIRV's ballots have been reallocated/whether or not this CandidateIRV has lost the election
     * @return a boolean (true if this CandidateIRV cannot win the election, false otherwise)
     */
    public boolean hasLost()
    {
        return lost;
    }

    /**
     * Gets the list of BallotIRV's who are assigned to this CandidateIRV
     * @return an ArrayList of BallotIRV's
     */
    public ArrayList<BallotIRV> getBallots()
    {
        return ballots;
    }
}