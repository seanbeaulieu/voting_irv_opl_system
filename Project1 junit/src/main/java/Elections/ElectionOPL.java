package Elections;

import Candidates.Candidate;
import Candidates.CandidateIRV;
import Candidates.CandidateOPL;
import Misc.BallotIRV;
import Misc.FileHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 * Represents an OPL based Election
 * Written by Jasper Rutherford
 */
public class ElectionOPL extends Election
{
    /**
     * A Map from party names to party vote counts
     */
    private HashMap<String, Integer> parties;

    /**
     * Creates an OPL based election
     *
     * @param fileHandler the fileHandler to be used for inputs/outputs
     * @param shuffle     true if ballot order should be shuffled, false otherwise
     */
    public ElectionOPL(FileHandler fileHandler, boolean shuffle)
    {
        super(fileHandler, shuffle);
        parties = new HashMap<>();
    }

    /**
     * Calculates the results of this Elections.ElectionOPL
     */
    @Override
    public boolean calcResults()
    {
        if (fileHandler.inputFileExists())
        {
            String electionType = fileHandler.nextLine();
            if (electionType.equals("OPL"))
            {
                numCandidates = fileHandler.nextInt();
                numSeats = fileHandler.nextInt();
                numBallots = fileHandler.nextInt();

                String rawCandidates = fileHandler.nextLine();

                if (!getCandidatesFromLine(rawCandidates))
                {
                    System.out.println("Error while trying to read Candidates");
                    return false;
                }

                if (!readBallots())
                {
                    System.out.println("Error while trying to read ballots");
                    return false;
                }

                //fill seats according to droop
                int droopSeats = fillDroopSeats();

                //fill any seats which were not allocated via droop
                fillExcessSeats(numSeats - droopSeats);

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
     * Takes a String and tries to parse CandidateOPLs and Parties from it
     * The candidates end up stored in super.candidates
     * The parties and their vote counts end up stored in this.parties
     *
     * @param line the line to parse into candidates and parties
     * @return Whether this was successful or not
     */
    private boolean getCandidatesFromLine(String line)
    {
        // split the raw input line into an array of candidates names
        String[] candidates_arr = line.split(",");

        // populate the candidates arrayList with candidates
        for (int i = 0; i < candidates_arr.length; i += 2)
        {
            //gets a candidate's name
            String candidateName = candidates_arr[i].substring(1);

            //gets a candidate's party
            String partyName = candidates_arr[i + 1].substring(0, candidates_arr[i + 1].length() - 1);

            //adds a candidate with the provided information to the list of candidates
            candidates.add(new CandidateOPL(candidateName, partyName));

            //set the party's number of votes to zero
            parties.put(partyName, 0);
        }

        //return success
        return true;
    }

    /**
     * Reads the ballots in from the input file
     *
     * @return true if successful and false otherwise
     */
    private boolean readBallots()
    {
        // read each line
        for (int i = 0; i < numBallots; i++)
        {
            // Read in a ballot, and populate an array with the choices.
            String rawBallot = fileHandler.nextLine();

            if (rawBallot != null)
            {
                String[] choice_arr = rawBallot.split(",");

                int index = Arrays.asList(choice_arr).indexOf("1");

                if (index == -1)
                {
                    //error out
                    System.out.println("Invalid ballot read!");
                    return false;
                }

                CandidateOPL candidate = (CandidateOPL) candidates.get(index);
                String party = candidate.getParty();

                candidate.setNumVotes(candidate.getNumVotes() + 1);
                parties.put(party, parties.get(party) + 1);
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
     * Performs the first pass of the count of the votes
     * Calculates how many seats each party should receive according to droop
     * Fills as many of those seats as can be filled
     *
     * @return The number of seats which were allocated via droop
     */
    private int fillDroopSeats()
    {
        int allocatedSeats = 0;
        int droop = (int) Math.floor(numBallots * 1.0 / (numSeats + 1)) + 1;

        //loop through each party
        for (String partyName : parties.keySet())
        {
            //get this party's number of votes
            int partyVotes = parties.get(partyName);

            //calculate how many droop seats the party has secured
            int droopSeats = partyVotes / droop;

            //get this party's Candidates
            ArrayList<Candidate> partyCandidates = getCandidatesByParty(partyName);

            //sort party candidates into descending order by votes
            partyCandidates.sort(Collections.reverseOrder());

            //loop through and try to fill the droop seats that this party has won
            for (int droopSeat = 0; droopSeat < droopSeats; droopSeat++)
            {
                //if there are candidates available to fill seats that were won
                if (partyCandidates.size() > 0)
                {
                    //get the next available candidate with the most votes
                    Candidate winner = partyCandidates.get(0);

                    //add that candidate to the list of winners
                    winners.add(winner);

                    //remove that candidate from this party's list of candidates
                    partyCandidates.remove(0);

                    //remove that candidate from this election's list of candidates who have not won
                    candidates.remove(winner);

                    //increase the number of seats which have successfully been filled via droop
                    allocatedSeats++;

                    //decrease this party's number of votes by the droop value
                    parties.put(partyName, partyVotes - droop);
                }
            }
        }

        return allocatedSeats;
    }

    /**
     * gets a list of remaining candidates who belong to this party
     *
     * @param partyName the party whose candidates will be listed
     * @return an ArrayList of Candidates who belong to the requested party
     */
    private ArrayList<Candidate> getCandidatesByParty(String partyName)
    {
        //get a list of all candidates in this party
        ArrayList<Candidate> partyCandidates = new ArrayList<>();
        for (Candidate candidate : candidates)
        {
            if (((CandidateOPL) candidate).getParty().equals(partyName))
            {
                partyCandidates.add(candidate);
            }
        }

        return partyCandidates;
    }

    /**
     * Fills all extra seats by party with the most remaining votes
     *
     * @param excessSeats the number of seats left to fill
     */
    private void fillExcessSeats(int excessSeats)
    {
        ArrayList<String> sortedPartyNames = getSortedPartyNames();
        int remainingSeats = excessSeats;

        //loop until all remaining seats have been allocated
        while (remainingSeats > 0)
        {
            //loop through parties in descending order of remaining votes
            for (int partyIndex = 0; partyIndex < sortedPartyNames.size(); partyIndex++)
            {
                //assign each seat to the next party with the most remaining votes
                String partyName = sortedPartyNames.get(partyIndex);

                //get all remaining candidates from that party
                ArrayList<Candidate> remainingCandidates = getCandidatesByParty(partyName);

                //if that party has candidates
                if (remainingCandidates.size() > 0)
                {
                    //sort the Candidates into decreasing order by number of votes
                    remainingCandidates.sort(Collections.reverseOrder());

                    //get the Candidates.Candidate with the most votes (within this party)
                    Candidate winner = remainingCandidates.get(0);

                    //add that Candidates.Candidate to the list of winners
                    winners.add(winner);

                    //remove that Candidates.Candidate from this party's list of candidates
                    remainingCandidates.remove(0);

                    //remove that Candidates.Candidate from this election's list of candidates who have not won
                    candidates.remove(winner);

                    //decrease the number of seats remaining
                    remainingSeats--;
                }
            }

            //if all candidates have seats and there are still seats to fill, then ignore the extra seats.
            if (candidates.size() < 1)
            {
                remainingSeats = 0;
            }
        }
    }

    /**
     * Gets all the party names and puts them in a list in descending order by that party's number of votes
     *
     * @return an ArrayList of party names
     */
    private ArrayList<String> getSortedPartyNames()
    {
        ArrayList<String> sortedPartyNames = new ArrayList<>();

        //sort all party names into the list
        for (String partyName : parties.keySet())
        {
            int partyVal = parties.get(partyName);

            int bigLocation = 0;
            int bigVal = -1;

            for (int lcv = 0; lcv < sortedPartyNames.size(); lcv++)
            {
                int newVal = parties.get(sortedPartyNames.get(lcv));

                if (partyVal > bigVal)
                {
                    bigLocation = lcv;
                    bigVal = newVal;
                }
            }

            sortedPartyNames.add(bigLocation + 1, partyName);
        }

        return sortedPartyNames;
    }

    /**
     * generates a report from the current state of the election
     */
    public void generateReport()
    {
        //calculate how many seats each party has won
        HashMap<String, Integer> partySeatsWon = new HashMap<>();

        //initialize each party to zero seats
        for (String partyName : parties.keySet())
        {
            partySeatsWon.put(partyName, 0);
        }

        //count how many seats each party has won
        for (Candidate winner : winners)
        {
            String partyName = ((CandidateOPL) winner).getParty();
            partySeatsWon.put(partyName, partySeatsWon.get(partyName) + 1);
        }

        //log everything to the report file
        fileHandler.reportLog("Party:\tVotes:\tSeats Won:");

        //report party info
        for (String partyName : parties.keySet())
        {
            fileHandler.reportLog(partyName + "\t" + parties.get(partyName) + "\t" + partySeatsWon.get(partyName));
        }

        //report individual candidate info
        fileHandler.reportLog("\nCandidate:\tVotes:\tWinner/Loser:");

        for (Candidate candidate : winners)
        {
            fileHandler.reportLog(candidate.getName() + "\t" + candidate.getNumVotes() + "\tWinner");
        }

        for (Candidate candidate : candidates)
        {
            fileHandler.reportLog(candidate.getName() + "\t" + candidate.getNumVotes() + "\tLoser");
        }
    }
}