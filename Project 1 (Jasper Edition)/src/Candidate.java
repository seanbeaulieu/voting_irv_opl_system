/**
 * Represents one Candidate
 */
public class Candidate
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
}