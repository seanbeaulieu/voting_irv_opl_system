package Everything.Elections;

import Everything.Candidates.Candidate;
import Everything.Candidates.CandidateOPL;
import Everything.FileHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 * Represents an OPL based Election
 * Written by Jasper Rutherford and Shey Pemberton
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
     * Reads all the information (candidates, ballots, parties, etc) from the supplied input file into this ElectionOPL object
     *
     * @return a boolean - true if ran successfully, false if errors were encountered
     */
    @Override
    public boolean readInputs()
    {
        int currentFilenameNumber = 1;
        int numFilenames = fileHandler.getNumFilenames();

        //loop until all filenames are expended or until a file fails to open
        while (fileHandler.nextInputFile())
        {
            String electionType = fileHandler.nextLine();
            numCandidates = fileHandler.nextInt();

            int tempNumSeats = fileHandler.nextInt();

            if (numSeats != -1 && tempNumSeats != numSeats)
            {
                System.out.println("Read a number of seats which did not match up with other files in file #" + currentFilenameNumber);
                return false;
            }
            else
            {
                numSeats = tempNumSeats;
            }

            int partialBallots = fileHandler.nextInt();
            numBallots += partialBallots;

            String rawCandidates = fileHandler.nextLine();

            if (!getCandidatesFromLine(rawCandidates))
            {
                System.out.println("Error while trying to read Candidates in file #" + currentFilenameNumber);
                return false;
            }
            else if (!tempCandidates.equals(candidates) && candidates.size() != 0)
            {
                System.out.println("File #" + currentFilenameNumber + " had different candidates than the previous file");
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

            if (!readBallots(partialBallots))
            {
                System.out.println("Error while trying to read ballots from file #" + currentFilenameNumber);
                return false;
            }

            //successfully read the file
            currentFilenameNumber++;
        }
        //if not all files were opened
        if (currentFilenameNumber != numFilenames + 1)
        {
            System.out.println("Failed to open file #" + currentFilenameNumber);
            return false;
        }

        //successful read
        return true;
    }

    /**
     * calculates the winners of this election according to OPL
     */
    public void calcResults()
    {
        //fill seats according to droop
        int droopSeats = fillDroopSeats();

        //fill any seats which were not allocated via droop
        fillExcessSeats(numSeats - droopSeats);
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
            String candidateName = candidates_arr[i].substring(2);

            //gets a candidate's party
            String partyName = candidates_arr[i + 1].substring(0, candidates_arr[i + 1].length() - 2);

            //adds a candidate with the provided information to the list of candidates
            tempCandidates.add(new CandidateOPL(candidateName, partyName));

            //if this party has not yet been added to the party tracker
            if (!parties.keySet().contains(partyName))
            {
                //add the party to the tracker (set the party's number of votes to zero)
                parties.put(partyName, 0);
            }
        }

        //return success
        return true;
    }

    /**
     * Reads the ballots in from the input file
     *
     * @param partialBallots the number of ballots to read in
     * @return true if successful and false otherwise
     */
    private boolean readBallots(int partialBallots)
    {
        // read each line
        for (int i = 0; i < partialBallots; i++)
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
                    parties.put(partyName, parties.get(partyName) - droop);
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
            for (int partyIndex = 0; partyIndex < sortedPartyNames.size() && remainingSeats > 0; partyIndex++)
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

                    //get the Candidate with the most votes (within this party)
                    Candidate winner = remainingCandidates.get(0);

                    //add that Candidate to the list of winners
                    winners.add(winner);

                    //remove that Candidates.Candidate from this party's list of candidates
                    remainingCandidates.remove(0);

                    //remove that Candidate from this election's list of candidates who have not won
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
        ArrayList<String> partyNamesSorted = new ArrayList<>();
        ArrayList<String> partyNames = new ArrayList<>(parties.keySet());

        ArrayList<Integer> partyVoteScores = new ArrayList<>();
        ArrayList<Integer> partyVoteScoresSorted = new ArrayList<>();

        for (String partyName : parties.keySet())
        {
            partyVoteScores.add(parties.get(partyName));
            partyVoteScoresSorted.add(parties.get(partyName));
        }

        Collections.sort(partyVoteScoresSorted);

        //loop through all the scores in sorted order
        for (int lcv = partyVoteScores.size() - 1; lcv > -1; lcv--)
        {
            ArrayList<String> tiedParties = new ArrayList<>();

            //get all the parties which are tied for the next position
            int score = partyVoteScoresSorted.get(lcv);
            int nextScore = score;
            do
            {
                int index = partyVoteScores.indexOf(nextScore);
                String party = partyNames.get(index);
                partyNames.remove(index);
                partyVoteScores.remove(index);
                partyVoteScoresSorted.remove(partyVoteScoresSorted.size() - 1);

                tiedParties.add(party);

                nextScore = lcv != 0 ? partyVoteScores.get(lcv - 1) : -1;
                if (nextScore == score)
                {
                    lcv--;
                }
            }
            while (nextScore == score);

            //add those tied parties to the list in a random order
            for (int i = tiedParties.size() - 1; i > -1; i--)
            {
                String randParty = tiedParties.get((int) (ElectionIRV.fairRandom() * tiedParties.size()));
                partyNamesSorted.add(randParty);
                tiedParties.remove(randParty);
            }
        }


        return partyNamesSorted;
    }

    /**
     * generates a report from the current state of the election
     */
    public void generateReport()
    {
        //calculate how many seats/votes each party has won
        HashMap<String, Integer> partySeatsWon = new HashMap<>();
        HashMap<String, Integer> partyVotes = new HashMap<>();

        //initialize each party to zero seats
        for (String partyName : parties.keySet())
        {
            partySeatsWon.put(partyName, 0);
            partyVotes.put(partyName, 0);
        }

        //count how many seats each party has won
        for (Candidate winner : winners)
        {
            String partyName = ((CandidateOPL) winner).getParty();
            partySeatsWon.put(partyName, partySeatsWon.get(partyName) + 1);
        }

        //create a list with all winners and losers
        ArrayList<Candidate> all = new ArrayList<>();
        all.addAll(winners);
        all.addAll(candidates);

        //count how many total votes each party received
        for (Candidate candidate : all)
        {
            String partyName = ((CandidateOPL) candidate).getParty();
            partyVotes.put(partyName, partyVotes.get(partyName) + candidate.getNumVotes());
        }

        //log everything to the report file
        fileHandler.reportLog("Party:\tVotes:\tSeats Won:");

        //report party info
        for (String partyName : parties.keySet())
        {
            fileHandler.reportLog(partyName + "\t" + partyVotes.get(partyName) + "\t" + partySeatsWon.get(partyName));
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