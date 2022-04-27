package Everything.Candidates;

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
     *
     * @param name  the name of the CandidateOPL
     * @param party the party that the CandidateOPL is affiliated with
     */
    public CandidateOPL(String name, String party)
    {
        super(name);
        this.party = party;
    }

    /**
     * Gets this CandidateOPL's party
     *
     * @return a String representing this CandidateOPL's party
     */
    public String getParty()
    {
        return party;
    }

    /**
     * Checks whether this CandidateOPL has the same name and party as the supplied CandidateOPL
     *
     * @param other the CandidateOPL to compare to this one
     * @return true if both CandidateOPLs share the same name and party, false otherwise
     */
    public boolean equals(Object other)
    {
        if (other instanceof CandidateOPL)
        {
            return this.party.equals(((CandidateOPL) other).party) && this.getName().equals(((CandidateOPL) other).getName());
        }
        else
        {
            return false;
        }
    }
}