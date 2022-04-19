package Candidates;

/**
 * Represents one Candidate in an OPL based Election
 * Written by Jasper Rutherford
 */
public class CandidateOPL extends Candidate
{
    /**
     * This CandidateOPL's party
     */
    private String party;

    /**
     * Constructs a CandidateOPL
     * @param name the name of the CandidateOPL
     * @param party the party that the CandidateOPL is affiliated with
     */
    public CandidateOPL(String name, String party)
    {
        super(name);
        this.party = party;
    }

    /**
     * Gets this CandidateOPL's party
     * @return a String representing this CandidateOPL's party
     */
    public String getParty()
    {
        return party;
    }
}