package Elections;

import Candidates.Candidate;
import Misc.FileHandler;

import java.util.ArrayList;

public class Election
{
    /**
     * Used to handle file inputs/outputs
     */
    protected final FileHandler fileHandler;

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

    protected boolean shuffle;

    /**
     * Creates an election with the given inputs (TODO)
     */
    public Election(FileHandler fileHandler, boolean shuffle)
    {
        this.fileHandler = fileHandler;
        this.shuffle = shuffle;

        numCandidates = 0;
        numBallots = 0;
        numSeats = 0;
        candidates = new ArrayList<>();
        winners = new ArrayList<>();
    }

    /**
     * Calculates the results of the election
     * returns true if the election was ran successfully
     * returns false if there was an error TODO: fix this comment to reflect return val
     */
    public boolean calcResults()
    {
        System.out.println("Somehow called Election's calcResults method. This should be impossible.");
        return false;
    }

    public boolean inputFileExists()
    {
        return fileHandler.inputFileExists();
    }

    public String nextLine()
    {
        return fileHandler.nextLine();
    }

    public int nextInt()
    {
        return fileHandler.nextInt();
    }

    public ArrayList<Candidate> getWinners()
    {
        return winners;
    }
}
