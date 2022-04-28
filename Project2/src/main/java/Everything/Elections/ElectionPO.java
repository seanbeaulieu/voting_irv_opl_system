package Everything.Elections;

import Everything.Candidates.Candidate;
import Everything.Candidates.CandidateOPL;
import Everything.FileHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Represents a PO based Election
 * Written by Jasper Rutherford
 */
public class ElectionPO extends Election
{
    /**
     * Creates an ElectionPO with the given inputs
     *
     * @param fileHandler how this election will get inputs from files/send outputs to files
     * @param shuffle whether or not to shuffle the ballots (this is ignored in this election type)
     */
    public ElectionPO(FileHandler fileHandler, boolean shuffle)
    {
        super(fileHandler, shuffle);
    }

    /**
     * Reads all the information (candidates, ballots, parties, etc) from the supplied input file into this ElectionPO object
     *
     * @return a boolean - true if ran successfully, false if errors were encountered
     */
    public boolean readInputs()
    {
        int currentFilenameNumber = 1;
        int numFilenames = fileHandler.getNumFilenames();

        //loop until all filenames are expended or until a file fails to open
        while (fileHandler.nextInputFile())
        {
            String electionType = fileHandler.nextLine();

            numCandidates = fileHandler.nextInt();

            String rawCandidates = fileHandler.nextLine();
            if (!getCandidatesFromLine(rawCandidates))
            {
                System.out.println("Error while trying to read Candidates in file #" + currentFilenameNumber);
                return false;
            }
            else if (!tempCandidates.equals(candidates) && candidates.size() != 0)
            {
                System.out.println("File #" + currentFilenameNumber + " had different candidates than the previous file");
                return false;
            }
            else if (candidates.size() == 0)
            {
                candidates.addAll(tempCandidates);
                tempCandidates.clear();
            }
            else
            {
                tempCandidates.clear();
            }

            int partialBallots = fileHandler.nextInt();
            numBallots += partialBallots;

            if (!readBallots(partialBallots))
            {
                System.out.println("Error while trying to read ballots from file #" + currentFilenameNumber);
                return false;
            }

            //successfully read the file
            currentFilenameNumber++;
        }
        //if not all files were opened
        if (currentFilenameNumber != numFilenames + 1)
        {
            System.out.println("Failed to open file #" + currentFilenameNumber);
            return false;
        }

        //successful read
        return true;
    }


    /**
     * calculates the winners of this election according to PO
     */
    public void calcResults()
    {
        //create a list for candidates who have the most votes
        ArrayList<Candidate> bigWinners = new ArrayList<>();
        bigWinners.add(candidates.get(0));

        //loop through all candidates (except candidate 0 who starts in the list)
        for (int candNum = 1; candNum < candidates.size(); candNum++)
        {
            //get this candidate
            Candidate candidate = candidates.get(candNum);

            //if this candidate is tied for the most votes
            if (candidate.getNumVotes() == bigWinners.get(0).getNumVotes())
            {
                //add them to the list
                bigWinners.add(candidate);
            }

            //if they have more votes than the previous candidate with the most votes
            else if (candidate.getNumVotes() > bigWinners.get(0).getNumVotes())
            {
                //remove all previous candidates from the list
                bigWinners.clear();

                //add this candidate to the list
                bigWinners.add(candidate);
            }
        }

        //choose a random candidate from the list to win (fairly)
        int winnerIndex = (int)(bigWinners.size() * ElectionIRV.fairRandom());
        winners.add(bigWinners.get(winnerIndex));
        candidates.remove(bigWinners.get(winnerIndex));
    }

    /**
     * Takes a String and tries to parse Candidates from it
     * The candidates end up stored in super.candidates
     *
     * @param line the line to parse into candidates
     * @return Whether this was successful or not
     */
    private boolean getCandidatesFromLine(String line)
    {
        // split the raw input line into an array of candidates names
        String[] candidates_arr = line.split(",");

        // populate the candidates arrayList with candidates
        for (int i = 0; i < candidates_arr.length; i += 2)
        {
            //gets a candidate's name
            String candidateName = candidates_arr[i].substring(2);

            //gets a candidate's party
            String partyName = candidates_arr[i + 1].substring(0, candidates_arr[i + 1].length() - 2);

            //adds a candidate with the provided information to the list of candidates
            tempCandidates.add(new Candidate(candidateName + " (" + partyName + ")"));
        }

        //return success
        return true;
    }

    /**
     * Reads the ballots in from the input file (and assign them to the relevant candidates)
     *
     * @param partialBallots the number of ballots to read in
     * @return true if successful and false otherwise
     */
    private boolean readBallots(int partialBallots)
    {
        // read each line
        for (int i = 0; i < partialBallots; i++)
        {
            // Read in a ballot, and populate an array with the choices.
            String rawBallot = fileHandler.nextLine();

            if (rawBallot != null)
            {
                String[] choice_arr = rawBallot.split(",");

                int index = Arrays.asList(choice_arr).indexOf("1");

                if (index == -1)
                {
                    //error out
                    System.out.println("Invalid ballot read!");
                    return false;
                }

                Candidate candidate = candidates.get(index);

                candidate.setNumVotes(candidate.getNumVotes() + 1);
            }

            else
            {
                System.out.println("Reached the end of the file!");
                return false;
            }
        }

        // return success
        return true;
    }

    /**
     * generates a report from the current state of the election
     */
    public void generateReport()
    {
        for (Candidate candidate : winners)
        {
            fileHandler.reportLog(candidate.getName() + "\t" + candidate.getNumVotes() + "\tWinner");
        }

        for (Candidate candidate : candidates)
        {
            fileHandler.reportLog(candidate.getName() + "\t" + candidate.getNumVotes() + "\tLoser");
        }
    }
}
