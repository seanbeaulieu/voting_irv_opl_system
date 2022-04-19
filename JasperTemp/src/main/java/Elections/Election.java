package Elections;

import Candidates.Candidate;
import Misc.FileHandler;

import java.util.ArrayList;

/**
 * Represents an election with no specified type.
 * Written by Jasper Rutherford
 */
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

    /**
     * Whether or not to shuffle the ballots
     */
    protected boolean shuffle;

    /**
     * Creates an election with the given inputs
     * @param fileHandler how this election will get inputs from files/send outputs to files
     * @param shuffle whether or not this election should shuffle its ballots (only used if the election is IRV based)
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
     * @return true if the election was ran successfully, false if there was an error
     */
    public boolean calcResults()
    {
        System.out.println("Somehow called Election's calcResults method. This should be impossible.");
        return false;
    }

    /**
     * gets the list of winners of this election
     * @return an ArrayList of Candidates which represent which candidates have won this election
     */
    public ArrayList<Candidate> getWinners()
    {
        return winners;
    }

    /**
     * generates a report from the current state of the election
     */
    public void generateReport()
    {
        System.out.println("Tried to generate a report from an election with no type. This should be impossible.");
    }
}
