package Candidates;

import Candidates.Candidate;

public class CandidateOPL extends Candidate
{
    /**
     * This Candidates.CandidateOPL's party
     */
    private String party;

    /**
     * Constructs a Candidates.CandidateOPL
     * @param name the name of the Candidates.CandidateOPL
     * @param party the party that the Candidates.CandidateOPL is affiliated with
     */
    public CandidateOPL(String name, String party)
    {
        super(name);
        this.party = party;
    }

    /**
     * Gets this Candidates.CandidateOPL's party
     * @return a String representing this Candidates.CandidateOPL's party
     */
    public String getParty()
    {
        return party;
    }
}