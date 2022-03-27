package Elections;

import Candidates.Candidate;
import Candidates.CandidateOPL;
import Misc.FileHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ElectionOPL extends Election
{
    /**
     * A Map from party names to party vote counts
     */
    private HashMap<String, Integer> parties;

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
        if (inputFileExists())
        {
            String electionType = nextLine();
            if (electionType.equals("OPL"))
            {
                //TODO: theoretically the file could fail when reading these ints? I think we can assume that the file is written properly but if not then we should get on that
                numCandidates = nextInt();
                numSeats = nextInt();
                numBallots = nextInt();

                String rawCandidates = nextLine();

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
    public boolean getCandidatesFromLine(String line)
    {
        //Used to shrink down the provided string into smaller chunks
        String shortened = line;

        //loops once for every candidate
        for (int lcv = 0; lcv < numCandidates; lcv++)
        {
            int bracketPos = shortened.indexOf('[');
            int commaPos = shortened.indexOf(',');

            if (bracketPos == -1 || commaPos == -1)
            {
                //error out
                return false;
            }

            //gets a candidate's name
            String candidateName = shortened.substring(bracketPos + 1, commaPos);

            shortened = shortened.substring(commaPos + 1);
            bracketPos = shortened.indexOf(']');

            if (bracketPos == -1)
            {
                //error out
                return false;
            }

            //gets a candidate's party
            String partyName = shortened.substring(0, bracketPos);

            //adds a candidate with the provided information to the list of candidates
            candidates.add(new CandidateOPL(candidateName, partyName));

            //set the party's number of votes to zero
            parties.put(partyName, 0);

            shortened = shortened.substring(bracketPos + 2);
        }

        //return success
        return true;
    }

    /**
     * Reads the ballots in from the input file
     *
     * @return true if successful and false otherwise
     */
    public boolean readBallots()
    {
        //loop once for each ballot
        for (int lcv = 0; lcv < numBallots; lcv++)
        {
            String rawBallot = nextLine();
            int index = rawBallot.indexOf(1);

            if (index == -1)
            {
                //error out
                return false;
            }

            CandidateOPL candidate = (CandidateOPL) candidates.get(index);
            String party = candidate.getParty();

            candidate.setNumVotes(candidate.getNumVotes() + 1);
            parties.put(party, parties.get(party) + 1);
        }

        return true;
    }

    /**
     * Performs the first pass of the count of the votes
     * Calculates how many seats each party should receive according to droop
     * Fills as many of those seats as can be filled
     *
     * @return The number of seats which were allocated via droop
     */
    public int fillDroopSeats()
    {
        int allocatedSeats = 0;
        int droop = (int) Math.floor(numBallots * 1.0 / (numSeats + 1));        //TODO: this might need an extra +1 on the end?

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
    public ArrayList<Candidate> getCandidatesByParty(String partyName)
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
    public void fillExcessSeats(int excessSeats)
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
        }
    }

    /**
     * Gets all the party names and puts them in a list in descending order by that party's number of votes
     *
     * @return an ArrayList of party names
     */
    public ArrayList<String> getSortedPartyNames()
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
}