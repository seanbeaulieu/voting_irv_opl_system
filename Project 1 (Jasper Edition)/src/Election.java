import java.util.ArrayList;

public class Election
{
    /**
     * Used to handle file inputs/outputs
     */
    private FileHandler fileHandler;

    /**
     * The number of Candidates in this election
     */
    protected int numCandidates;

    /**
     * The number of ballots in this election
     */
    protected int numBallots;

    /**
     * The number of seats up for grabs in this election
     */
    protected int numSeats;

    /**
     * The list of Candidates running for election
     */
    protected ArrayList<Candidate> candidates;

    /**
     * The list of Candidates who have won seats
     */
    protected ArrayList<Candidate> winners;

    /**
     * Creates an election with the given inputs (TODO)
     */
    public Election(FileHandler fileHandler)
    {
        this.fileHandler = fileHandler;
        numCandidates = 0;
        numBallots = 0;
        numSeats = 0;
        candidates = new ArrayList<>();
        winners = new ArrayList<>();
    }

    /**
     * Calculates the results of the election
     */
    public void calcResults()
    {

    }

    /**
     * Gets the results of the election
     * TODO: finish comment to reflect return value?
     */
    public void getResults()
    {

    }

    public boolean inputFileExists()
    {
        return fileHandler.inputHandlerExists();
    }

    public String nextLine()
    {
        return fileHandler.nextLine();
    }

    public int nextInt()
    {
        return fileHandler.nextInt();
    }
}
