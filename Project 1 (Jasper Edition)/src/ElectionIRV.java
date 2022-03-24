import java.util.ArrayList;
import java.util.Stack;

public class ElectionIRV extends Election
{
    /**
     * A Stack of CandidateIRVs who have lost the election.
     */
    private Stack<CandidateIRV> losers;

    /**
     * The list of BallotIRVs which are not assigned to any CandidateIRV
     */
    private ArrayList<BallotIRV> unassignedBallots;

    /**
     * Creates an ElectionIRV
     */
    public ElectionIRV()
    {

    }

    /**
     * Calculates the results of the election according to IRV
     */
    @Override
    public void calcResults()
    {

    }
}
