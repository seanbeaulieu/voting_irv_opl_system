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


    Collections.shuffle(ballots);
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
            // Does the check for shuffle happen in main? How would it get transferred into this function?

            if (!shuffle) {
                // shuffle
            }

            

            // While there are still unassigned ballots
            // Does this need to be numBallots? Could also be while checkForWinner still returns false. 
            // It's possible that this code could be simplified down to less than 30 lines.
            // The main challenge is all of the edge cases that will take up lots of space 
            // Tie breakers, coin flip, the order of ballots

            while (checkForWinner() == false) {

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
    // Could/Should change to single line reading and loop in calcResults in order to track order

    // Does this need to work where all the ballots are just read into unassigned ballots?

    // Note2: The way that this function is setup parses the ballot for the index of 1
    //        and assigns the ballot to the candidate. However, this might need to be 
    //        changed in order to accomodate the initialization of currCandidate in BallotIRV
    //        and the workings of nextCandidate()
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

    /**
     * this function checks all of the candidates for a candidate that has the majority (>50%) amount of first choice ballots
     */
    public boolean checkForWinner() {

        // Loop through all candidates and tally the amount of first choice ballots they have
        
        int indexOfWinner;
        int numBallotsOfTemp;

        // WILL NEED TO POSSIBLY ADJUST NUMCANDIDATES BASED ON ANY BALLOTS THAT AREN'T ASSIGNED
        for (int i = 0; i < numCandidates; i++) {

            // create candidate
            CandidateIRV candidate = (CandidateIRV) candidates.get(index);

            // check amount of ballots
            int numBallotsCandHas = candidate.ballots.size();

            // Compare the ballots. This is where we would need to handle tie edgecases. Lot of code to write here.
            if (numBallotsCanHas > numBallotsOfTemp) {
                indexOfWinner = i;
                numBallotsOfTemp = numBallotsCandHas;
            }

            // Check that the numberBallots is the majority relative to the whole number 
            // This means that the winner is declared, and that after the loop the won bool is set to true
            if ( (double) (numBallots / numBallotsOfTemp) > .50) {
                break;
            }
            
            else {
                // something else
            }

            // IN THE EVENT OF A TIE
            return false;
        }

        // Set the candidate with the most first place votes as the winner
        CandidateIRV winner_candidate = (CandidateIRV) candidates.get(index);
        winner_candidate.won = true;
        return true;
    }

}

