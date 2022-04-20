package Everything.Candidates;

/**
 * Represents one Candidate in an Election.
 * Written by Jasper Rutherford
 */
public class Candidate implements Comparable<Candidate>
{
    /**
     * Tracks this candidate's name
     */
    private final String name;

    /**
     * Tracks this candidate's number of votes
     */
    private int numVotes;

    /**
     * Creates a Candidate with the provided name
     * @param name the name of this Candidate
     */
    public Candidate(String name)
    {
        this.name = name;
        this.numVotes = 0;
    }

    /**
     * Gets this Candidate's name
     *
     * @return a String representing this candidate's name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gets this Candidate's number of votes
     *
     * @return an int representing this Candidate's number of votes
     */
    public int getNumVotes()
    {
        return numVotes;
    }

    /**
     * Sets this Candidate's number of votes
     * @param numVotes the new number of votes for the candidate to have
     */
    public void setNumVotes(int numVotes)
    {
        this.numVotes = numVotes;
    }

    /**
     * compares two candidates by their number of votes
     * (used for sorting arraylists of candidates)
     * @param candidate the candidate being compared with this one
     * @return -1 if the provided candidate has a larger voteCount, 1 if smaller, 0 otherwise
     */
    @Override
    public int compareTo(Candidate candidate)
    {
        return Integer.compare(getNumVotes(), candidate.getNumVotes());
    }
}