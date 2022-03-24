import java.util.ArrayList;
import java.util.HashMap;

public class ElectionOPL extends Election
{
    /**
     * A Map from party names to party vote counts
     */
    private HashMap<String, Integer> parties;

    public ElectionOPL(FileHandler fileHandler)
    {
        super(fileHandler);
        parties = new HashMap<>();
    }

    /**
     * Calculates the results of this ElectionOPL
     */
    @Override
    public void calcResults()
    {
        if (inputFileExists())
        {
            String electionType = nextLine();
            if (electionType.equals("OPL"))
            {
                numCandidates = nextInt();
                numSeats = nextInt();
                numBallots = nextInt();

                String rawCandidates = nextLine();

                if (!getCandidatesFromLine(rawCandidates))
                {
                    //error out
                    //TODO
                }

                if (!readBallots())
                {
                    //error out
                    //(TODO)
                }

                countVotesFirstPass();

                countVotesSecondPass();
            }
            else
            {
                //bad
            }
        }
        else
        {
            //fail
        }
//        for(numBallots){
//            int index = read ballot index
//            candidate = candidates[index]**
//            candidate.votes++;
//            parties[candidateParty]++;***
//            // log to audit file?
//        }
//    }
//catch{
//        file error -> report this
//        exit
//    }
//
//        for(numSeats) {
//            candidate = getBestCandidate()
//            winnersAdd(candidate)
//            candidateWon = true;
//        }
//
//
//
//**
//        candidate char party -> index of this candidate's party
//        int votes
//
//***
//        Parties -> hashmap string ->int
//        Parties[R] -> num votes for R Party

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
     */
    public void countVotesFirstPass()
    {
        int droop = (int) Math.floor(numBallots * 1.0 / (numSeats + 1));        //TODO: this might need an extra +1 on the end?

        parties.forEach((partyName, partyVote) ->
        {
            //calculate how many droop seats the party has secured
            int droopSeats = partyVote / droop;

            ArrayList<Candidate>
            //get all the candidates in the party
            for (Candidate candidate : candidates)
            {

            }
        });
    }
}