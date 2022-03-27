package Elections;

import Candidates.Candidate;
import Candidates.CandidateIRV;
import Misc.BallotIRV;
import Misc.FileHandler;

import java.util.ArrayList;
import java.util.Collections;
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
     * Creates an ElectionIRV with the provided fileHandler
     */
    public ElectionIRV(FileHandler fileHandler, boolean shuffle)
    {
        super(fileHandler, shuffle);

        losers = new Stack<>();
        unassignedBallots = new ArrayList<>();
    }

    /**
     * Calculates the results of the election according to IRV
     */
    @Override
    public boolean calcResults()
    {
        if (inputFileExists())
        {
            String electionType = nextLine();

            //check first line
            if (electionType.equals("IRV"))
            {
                numCandidates = nextInt();
                numBallots = nextInt();
                String rawCandidates = nextLine();

                //read candidates
                if (!getCandidatesFromIRVLine(rawCandidates))
                {
                    System.out.println("Error while trying to read Candidates");
                    return false;
                }

                //read ballots
                if (!readBallots())
                {
                    System.out.println("Error while trying to read ballots");
                    return false;
                }

                //shuffle ballots if shuffle is on
                if (shuffle)
                {
                    fileHandler.auditLog("Shuffling Ballots");
                    Collections.shuffle(unassignedBallots);

                    fileHandler.auditLog("New Ballot Order:");
                    for (BallotIRV ballot : unassignedBallots)
                    {
                        fileHandler.auditLog("#" + ballot.getId());
                    }
                }

                //don't shuffle if shuffle is off
                else
                {
                    fileHandler.auditLog("Not Shuffling Ballots");
                }

                //calculate who won the election
                calculateWinner();

                // While there are still unassigned ballots
                // Does this need to be numBallots? Could also be while checkForWinner still returns false.
                // It's possible that this code could be simplified down to less than 30 lines.
                // The main challenge is all of the edge cases that will take up lots of space
                // Tie breakers, coin flip, the order of ballots

//                while (checkForWinner() == false)
//                {
//
//                    // possible reassignment of ballots here?
//                    // there could be a function that takes the ballots from the candidate who just lost, and then reassigns them
//
//                }

                //successful election!
                return true;
            }
            else
            {
                System.out.println("First line of input file does not match election type.");
                return false;
            }
        }
        else
        {
            System.out.println("Input file does not exist.");
            return false;
        }
    }

    /**
     * Parses candidates from the supplied line
     * @param line a string representation of candidates
     * @return true if this succeeded, false otherwise
     */
    public boolean getCandidatesFromIRVLine(String line)
    {

        // split the raw input line into an array of candidates names
        String[] candidates_arr = line.split(",");

        // populate the candidates arrayList with candidates
        for (int i = 0; i < candidates_arr.length; i++)
        {
            candidates.add(new CandidateIRV(candidates_arr[i]));
        }

        return true;
    }

    /**
     * Reads through all the ballots and creates a ballot from each ballot line in the input file
     *
     * @return true if successful, false if there is an error
     */
    public boolean readBallots()
    {
        // read each line
        for (int i = 0; i < numBallots; i++)
        {
            // Read in a ballot, and populate an array with the choices.
            String rawBallot = nextLine();

            fileHandler.auditLog("Read Ballot #" + i + " (" + rawBallot + ")");

            if (rawBallot != null)
            {
                String[] choice_arr = rawBallot.split(",");

                // Create a new ballot object. Notes on population in BallotIRV
                BallotIRV ballot = new BallotIRV(choice_arr, candidates, i);
            }

            else
            {
                System.out.println("Reached the end of the file!");
                return false;
            }
        }

        // return success
        return true;
    }

    /**
     * this function checks all of the candidates for a candidate that has the majority (>50%) amount of first choice ballots
     */
//    public boolean checkForWinner()
//    {
//
//        // Loop through all candidates and tally the amount of first choice ballots they have
//
//        int indexOfWinner;
//        int numBallotsOfTemp;
//        int indexOfLoser;
//        int numBallotsOfLoser;
//
//        // WILL NEED TO POSSIBLY ADJUST NUMCANDIDATES BASED ON ANY BALLOTS THAT AREN'T ASSIGNED
//        for (int i = 0; i < numCandidates; i++)
//        {
//
//            // create candidate
//            CandidateIRV candidate = (CandidateIRV) candidates.get(i);
//
//            // check to see if the candidate hasn't already lost the election
//
//            if (candidate.hasLost() == false)
//            {
//
//                // check amount of ballots
//                int numBallotsCandHas = candidate.getBallots().size();
//
//                // Compare the ballots. This is where we would need to handle tie edgecases. Lot of code to write here.
//                if (numBallotsCandHas > numBallotsOfTemp)
//                {
//                    indexOfWinner = i;
//                    numBallotsOfTemp = numBallotsCandHas;
//                }
//
//                if (numBallotsOfLoser < numBallotsCandHas)
//                {
//                    indexOfLoser = i;
//                    numBallotsOfLoser = numBallotsCandHas;
//                }
//
//                // Check that the numberBallots is the majority relative to the whole number
//                // This means that the winner is declared, and that after the loop the won bool is set to true
//                if ((double) (numBallots / numBallotsOfTemp) > .50)
//                {
//                    break;
//                }
//
//                // No majority. Not sure how to connect the bottom two else statments
//
//                else
//                {
//                    CandidateIRV loser_candidate = (CandidateIRV) candidates.get(indexOfLoser);
//                    loser_candidate.lose();
//                    return false;
//                }
//
//                // IN THE EVENT OF A TIE
//                // Code would return false, meaning no winner
//                else if (i == (numCandidates - 1))
//            {
//                return false;
//            }
//            }
//        }
//
//        // Set the candidate with the most first place votes as the winner
//        CandidateIRV winner_candidate = (CandidateIRV) candidates.get(indexOfWinner);
//        winner_candidate.win();
//        return true;
//    }


    /**
     * calculates the winner of this election according to IRV
     */
    public void calculateWinner()
    {
        Candidate winner = null;
        int voteGoal = numBallots / 2 + 1;

        //move ballots around until a victor is found
        while (winner == null)
        {
            //loop through all unassigned ballots
            for (BallotIRV ballot : unassignedBallots)
            {
                //get each ballot's next best candidate
                CandidateIRV candidate = (CandidateIRV) ballot.nextCandidate();

                //if there is a best candidate then the ballot is reassigned to that candidate
                if (candidate != null)
                {
                    candidate.addBallot(ballot);
                    fileHandler.auditLog("Ballot #" + ballot.getId() + " was assigned to " + candidate.getName());

                    //if the candidate has enough votes to win the election
                    if (candidate.getNumVotes() > voteGoal)
                    {
                        //mark them down as the winner
                        candidate.win();
                        winner = candidate;
                        fileHandler.auditLog(candidate.getName() + " now has " + candidate.getNumVotes() + " and has won the election!");

                        //stop looping through ballots
                        break;
                    }
                }

                //otherwise the ballot is forgotten
                else
                {
                    fileHandler.auditLog("Ballot #" + ballot.getId() + " has no more candidates marked. It will now be forgotten.");
                }
            }

            //clear the list of unassigned ballots. ALl ballots will have been assigned or should be forgotten.
            unassignedBallots.clear();

            //if no winner has been found
            if (winner == null)
            {
                fileHandler.auditLog("All unassigned ballots have been assigned and there is no winner.");

                //get the candidate with the least votes
                CandidateIRV lowestCandidate = getLowestCandidate();
                fileHandler.auditLog(lowestCandidate.getName() + " has the least votes. Their votes will now be unassigned.");

                //mark that they have lost the election
                lowestCandidate.lose();

                //add them to the loser list
                losers.add(lowestCandidate);

                //take them off the standard candidate list
                candidates.remove(lowestCandidate);

                //unassign their ballots
                for (BallotIRV ballot : lowestCandidate.getBallots())
                {
                    unassignedBallots.add(ballot);
                    fileHandler.auditLog("Ballot #" + ballot.getId() + " has been unassigned.");
                }

                //clear the candidate's ballots
                lowestCandidate.getBallots().clear();
            }
        }
    }

    /**
     * finds the CandidateIRV in this ElectionIRV's 'candidates' list who has the least number of votes
     *
     * @return the next CandidateIRV who has the least votes
     */
    private CandidateIRV getLowestCandidate()
    {
        ArrayList<Candidate> lowestCandidates = new ArrayList<>();
        lowestCandidates.add(candidates.get(0));

        //loop through all candidates
        for (Candidate candidate : candidates)
        {
            //if this candidate has fewer votes than the candidates tied for the least votes
            if (candidate.getNumVotes() < lowestCandidates.get(0).getNumVotes())
            {
                //clear the list of lowest candidates
                lowestCandidates.clear();

                //add this candidate to the list
                lowestCandidates.add(candidate);
            }

            //if this candidate is tied for the least number of votes
            else if (candidate.getNumVotes() == lowestCandidates.get(0).getNumVotes())
            {
                //add it to the list
                lowestCandidates.add(candidate);
            }
        }

        //defaults the lowestCandidate to the first one in the list
        CandidateIRV lowestCandidate = (CandidateIRV) lowestCandidates.get(0);

        //if multiple candidates are tied for the lowest number of votes
        if (lowestCandidates.size() > 1)
        {
            //choose one randomly instead
            int index = (int) (fairRandom() * lowestCandidates.size());
            lowestCandidate = (CandidateIRV) lowestCandidates.get(index);

            //log this to the audit file
            fileHandler.auditLog("The following candidates were tied for the least number of votes:");

            for (Candidate cand : lowestCandidates)
            {
                fileHandler.auditLog("\t" + cand.getName());
            }

            fileHandler.auditLog(lowestCandidate.getName() + " was randomly selected to be the loser.");
        }

        //return the candidate with the fewest votes
        return lowestCandidate;
    }

    /**
     * gets a fair random number.
     *
     * @return a randomly generated double which will not fall subject to any fake randomness (via early random calling)
     */
    private double fairRandom()
    {
        double rand = -1;

        //flips 10000 coins to get out of any fake randomness caused by early random function calling
        for (int lcv = 0; lcv < 10000; lcv++)
        {
            rand = Math.random();
        }

        if (rand < 0)
        {
            System.out.println("Coin flip returned -1! This should be impossible.");
        }

        return rand;
    }

    /**
     * generates a report from the current state of the election
     */
    public void generateReport()
    {
        fileHandler.reportLog("\nCandidate:\tVotes:\tWinner/Loser:");

        for (Candidate candidate : candidates)
        {
            if (((CandidateIRV) candidate).hasWon())
            {
                fileHandler.reportLog(candidate.getName() + "\t" + candidate.getNumVotes() + "\tWinner");
            }
            else
            {
                fileHandler.reportLog(candidate.getName() + "\t" + candidate.getNumVotes() + "\tLoser");
            }
        }
    }
}

