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
        super(fileHandler);
    }

    /**
     * Calculates the results of the election according to IRV
     */
    @Override
    public void calcResults() {

        if (inputFileExists()) {

            String electionType = nextLine();

            if (electionType.equals("IRV")) {

                numCandidates = nextInt();
                numBallots = nextInt();
                String rawCandidates = nextLine();

                if (!getCandidatesFromIRVLine(rawCandidates)) {
                    // error
                }

            }

            // Need to handle shuffle check here
            // Possibly add shuffle as a function in ElectionIRV

            // While there are still unassigned ballots
            while (numBallots != 0) {

            }


            
        }
    }

    public boolean getCandidatesFromIRVLine(String line) {
        
        // split the raw input line into an array of candidates names
        String[] candidates_arr = line.split(",");

        // populate the candidates arrayList with candidates
        for (int i = 0; i < candidates_arr.length; i++) {
            candidates.add(new CandidateIRV(candidates_arr[i]));
        }

        return true;
    }

    /**
     * Reads through all the ballots and assigns the ballots to their first choice candidate
     */

    // Note - might not need to loop, and can instead read line by line. However, it can also be
    // helpful to do it all in one pass, check for a majority, and then go reassigning ballots
    public boolean readFirstChoiceBallots() {

        // read each line
        for (int i = 0; i < numBallots; i++) {

            // Read in a ballot, and populate an array with the choices. (Now, does split recognize choices like ",,1,2" and populate
            // the array with 4 different objects? Not sure)
            String rawBallot = nextLine();
            int[] arr choice_arr = rawBallot.split(','); 

            // Create a new ballot object. Notes on population in BallotIRV
            BallotIRV ballot = new BallotIRV(choice_arr);

            // Find the index of the choice_arr that contains 1, find the candidate that is in the candidate list array 1, and add the
            // ballot to that candidate
            int index = choice_arr.indexOf(1);
            CandidateIRV candidate = (CandidateIRV) candidates.get(index);
            candidate.ballots.add(ballot);

        }

        // return 1 if success
        return 1;

    }


}
