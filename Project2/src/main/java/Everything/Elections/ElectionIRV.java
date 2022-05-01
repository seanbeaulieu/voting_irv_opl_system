package Everything.Elections;

import Everything.Candidates.Candidate;
import Everything.Candidates.CandidateIRV;
import Everything.BallotIRV;
import Everything.FileHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Represents an IRV based Election
 * Written by Sean Beaulieu, Ann Huynh, and Jasper Rutherford
 */
public class ElectionIRV extends Election
{
    /**
     * A Stack of CandidateIRVs who have lost the election.
     */
    private Stack<CandidateIRV> losers;

    /**
     * The list of BallotIRVs which are not assigned to any CandidateIRV
     */
    public ArrayList<BallotIRV> unassignedBallots;

    /**
     * Creates an ElectionIRV with the provided fileHandler
     *
     * @param fileHandler how the election will get inputs/log to output files
     * @param shuffle     whether or not the election should shuffle the ballots
     */
    public ElectionIRV(FileHandler fileHandler, boolean shuffle)
    {
        super(fileHandler, shuffle);

        losers = new Stack<>();
        unassignedBallots = new ArrayList<>();
    }

    /**
     * Reads all the information (candidates, ballots, etc) from the supplied input file into this ElectionIRV object
     *
     * @return a boolean - true if ran successfully, false if errors were encountered
     */
    @Override
    public boolean readInputs()
    {
        int currentFilenameNumber = 1;
        int numFiles = fileHandler.getNumFilenames();
        // check if the next filename exists
        while (fileHandler.nextInputFile())
        {

            String dump = fileHandler.nextLine();
            // check if the number of candidates is the same across files
            int tempNumCandidates = fileHandler.nextInt();
            // Candidates are not the same, send error message to user
            if (numCandidates != -1 && tempNumCandidates != numCandidates)
            {
                System.out.println("Error: The number of candidates in file number " + (currentFilenameNumber) +
                        " is not consistent with the previous file's number of candidates.");
                return false;
            }
            // if it's the first file or the candidates are the same, record candidates
            else
            {
                numCandidates = tempNumCandidates;
            }

            // check if the number of seats is the same across files
            int tempNumSeats = fileHandler.nextInt();
            // if the number of seats is not the same, send error message to user
            if (numSeats != -1 && tempNumSeats != numSeats)
            {
                System.out.println("Error: The number of seats in file number " + (currentFilenameNumber) +
                        " is not consistent with the previous file's number of seats.");
                return false;
            }
            // if it's the first file or the candidates are the same, record candidates
            else
            {
                numSeats = tempNumSeats;
            }

            // record number of ballots, which can be different across multiple files
            int partialBallots = fileHandler.nextInt();
            numBallots += partialBallots;

            // record the line containing the candidates and parties
            String rawCandidates = fileHandler.nextLine();

            // parse candidates from the raw line
            // if parsing fails, send error message
            if (!getCandidatesFromIRVLine(rawCandidates))
            {
                System.out.println("Error while trying to read Candidates in file number " + (currentFilenameNumber));
                return false;
            }
            //
            else if (!candidates.equals(tempCandidates) && candidates.size() != 0)
            {
                System.out.println("Error: The candidates in file number " + (currentFilenameNumber) +
                        " is not consistent with the previous file's candidates.");
                return false;
            }
            else if (candidates.size() == 0)
            {
                candidates.addAll(tempCandidates);
                tempCandidates.clear();
            }
            else
            {
                tempCandidates.clear();
            }

            //read ballots
            if (!readBallots(partialBallots))
            {
                System.out.println("Error while trying to read ballots in file number " + (currentFilenameNumber));
                return false;
            }

            //successful read! increment file numbers
            currentFilenameNumber++;
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

        // check if the number of files expected is equal to the number of files read.
        if (numFiles != (currentFilenameNumber - 1))
        {
            System.out.println("File number " + currentFilenameNumber + " failed to open.");
            return false;
        }
        //  successfully read the correct number of files
        return true;
    }

    /**
     * Parses candidates from the supplied line
     *
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
            tempCandidates.add(new CandidateIRV(candidates_arr[i]));
        }

        return true;
    }

    /**
     * Reads through all the ballots and creates a ballot from each ballot line in the input file
     *
     * @return true if successful, false if there is an error
     */
    private boolean readBallots(int partialBallots)
    {
        // read each line
        for (int i = 0; i < partialBallots; i++)
        {
            // Read in a ballot, and populate an array with the choices.
            String rawBallot = fileHandler.nextLine();

            int ballotID = numBallots - partialBallots + i;
            fileHandler.auditLog("Read Ballot #" + ballotID + " (" + rawBallot + ")");

            if (rawBallot != null)
            {
                String[] choice_arr = rawBallot.split(",");

                // Create a new ballot object. Notes on population in BallotIRV
                BallotIRV ballot = new BallotIRV(choice_arr, candidates, ballotID);
                if (ballot.isValid(candidates.size()))
                {
                    fileHandler.auditLog("Ballot #" + ballot.getId() + " is valid and has been added to the list of unassigned ballots.");
                    unassignedBallots.add(ballot);
                }
                else
                {
                    fileHandler.auditLog("Ballot #" + ballot.getId() + " is invalid and will be ignored.");
                    fileHandler.invalidBallotLog(ballot.toString());
                }
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
     * calculates the winners of this election according to IRV
     */
    public void calcResults()
    {
        int voteGoal = numBallots / 2 + 1;
        //move ballots around until a victor is found
        while (winners.size() < numSeats && (candidates.size() > 0 || losers.size() > 0))
        {
            //loop through all unassigned ballots
            for (BallotIRV ballot : unassignedBallots)
            {
                //get each ballot's next best candidate
                CandidateIRV candidate = getNextCandidate(ballot);
                //if there is a best candidate then the ballot is reassigned to that candidate
                if (candidate != null)
                {
                    candidate.addBallot(ballot);
                    fileHandler.auditLog("Ballot #" + ballot.getId() + " was assigned to " + candidate.getName());

                    //if the candidate now has enough votes to win the election
                    if (candidate.getNumVotes() >= voteGoal)
                    {
                        //mark them down as the winner
                        winners.add(candidate);

                        //mark their peak votes
                        candidate.setPeakVotes(candidate.getNumVotes());

                        //remove from list of candidates who have neither won/lost
                        candidates.remove(candidate);

                        //log this winner to audit file
                        fileHandler.auditLog(candidate.getName() + " now has " + candidate.getNumVotes() + " votes and has won a seat!");
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
            if (winners.size() < numSeats)
            {
                fileHandler.auditLog("All unassigned ballots have been assigned and there are not enough winners.");

                //get the candidate with the least votes
                CandidateIRV lowestCandidate = getLowestCandidate();

                //if there are no more candidates
                if (lowestCandidate == null)
                {
                    //the next winner is the person who lost most recently
                    try
                    {
                        Candidate winner = losers.pop();

                        //add the next winner to the list of winners
                        winners.add(winner);

                        //log the new winner to the audit file
                        fileHandler.auditLog("There are no more candidates who have not lost. " + winner.getName() + " lost most recently and has been moved from the list of losers to the list of winners.");
                    }
                    catch (EmptyStackException e)
                    {
                        //this code should be unreachable. (to enter this step of the while loop there must be a nonzero number of losers, but to enter this catch there must be zero losers)
                        System.out.println("something bugged out really bad. (this message was expected to never be printed)");
                    }
                }

                else
                {
                    fileHandler.auditLog(lowestCandidate.getName() + " has the least votes (" + lowestCandidate.getNumVotes() + "). Their votes will now be unassigned.");

                    //add them to the loser list
                    losers.add(lowestCandidate);

                    //take them off the standard candidate list
                    candidates.remove(lowestCandidate);

                    //mark their peak number of votes
                    lowestCandidate.setPeakVotes(lowestCandidate.getNumVotes());

                    //unassign their ballots
                    for (BallotIRV ballot : lowestCandidate.getBallots())
                    {
                        unassignedBallots.add(ballot);
                        fileHandler.auditLog("Ballot #" + ballot.getId() + " has been unassigned from " + lowestCandidate.getName() + ".");
                    }

                    //clear the candidate's ballots
                    lowestCandidate.getBallots().clear();
                }
            }
        }
    }

    /**
     * finds the CandidateIRV in this ElectionIRV's 'candidates' list who has the least number of votes
     *
     * @return the next CandidateIRV who has the least votes (or null if there are no more candidates)
     */
    private CandidateIRV getLowestCandidate()
    {
        //if there are no more candidates
        if (candidates.size() == 0)
        {
            //return null
            return null;
        }

        ArrayList<Candidate> lowestCandidates = new ArrayList<>();
        lowestCandidates.add(candidates.get(0));

        //loop through all candidates
        for (Candidate candidate : candidates)
        {
            if (!lowestCandidates.contains(candidate))
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
    public static double fairRandom()
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
        fileHandler.reportLog("\nCandidate:\tPeak Votes:\tWinner/Loser:");

        for (Candidate candidate : winners)
        {
            fileHandler.reportLog(candidate.getName() + "\t" + ((CandidateIRV) candidate).getPeakVotes() + "\tWinner");
        }

        for (Candidate candidate : candidates)
        {
            fileHandler.reportLog(candidate.getName() + "\t" + ((CandidateIRV) candidate).getPeakVotes() + "\tLoser");
        }

        for (Candidate candidate : losers)
        {
            fileHandler.reportLog(candidate.getName() + "\t" + ((CandidateIRV) candidate).getPeakVotes() + "\tLoser");
        }
    }

    /**
     * gets the next valid candidate from the supplied ballot
     * (a candidate is valid if they have neither won nor lost)
     *
     * @param ballot a ballot to read the next candidate of
     * @return a CandidateIRV representing this ballot's next candidate
     */
    public CandidateIRV getNextCandidate(BallotIRV ballot)
    {
        //get the next candidate on the ballot
        Candidate next = ballot.nextCandidate();

        //ignore all non-null candidates who have already won/lost
        while (next != null && !candidates.contains(next))
        {
            fileHandler.auditLog("The next candidate on ballot #" + ballot.getId() + " is " + next.getName() + " but " + next.getName() + " has already won/lost. Moving on to the next candidate on the ballot.");
            next = ballot.nextCandidate();
        }

        return (CandidateIRV) next;
    }
}

