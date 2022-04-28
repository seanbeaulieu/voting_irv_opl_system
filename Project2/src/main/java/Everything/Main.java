package Everything;

import Everything.Candidates.Candidate;
import Everything.Elections.Election;
import Everything.Elections.ElectionIRV;
import Everything.Elections.ElectionOPL;
import Everything.Elections.ElectionPO;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Used to take input commands from the user.
 * Written by Jasper Rutherford
 */
public class Main
{
    //whether or not IRV will be ran with shuffle
    private static boolean shuffle = true;

    //whether or not to exit the program
    private static boolean exit = false;

    //used for file input/output
    private static FileHandler fileHandler;

    //the election
    private static Election election;

    //whether or not the following commands are available to be ran
    private static boolean addFilenameAvailable = true;
    private static boolean deleteFilenameAvailable = false;
    private static boolean runoplAvailable = false;
    private static boolean runirvAvailable = false;
    private static boolean runpoAvailable = false;
    private static boolean shuffleAvailable = true;
    private static boolean generatereportAvailable = false;
    private static boolean displaywinnersAvailable = false;

    /**
     * Used to start the program
     *
     * @param args anything sent in from the user (all ignored)
     */
    public static void main(String[] args)
    {
        //used for command line input
        Scanner scanner = new Scanner(System.in);
        fileHandler = new FileHandler();

        //loop until program should exit
        while (!exit)
        {
            System.out.println("Awaiting Input.");
            String input = scanner.nextLine().toLowerCase();

            //addfilename command
            if (addFilenameAvailable && input.equals("addfilename"))
            {
                System.out.println("Awaiting File Name.");
                String filename = scanner.nextLine();
                System.out.println("'" + filename + "'");

                addFileName(filename);
            }

            //deletefilename command
            else if (deleteFilenameAvailable && input.equals("deletefilename"))
            {
                System.out.println("Awaiting File Name.");
                String filename = scanner.nextLine();
                System.out.println("'" + filename + "'");

                deleteFileName(filename);
            }

            //runopl command
            else if (runoplAvailable && input.equals("runopl"))
            {
                System.out.println("Running OPL.");
                runOPL();
            }

            //runirv command
            else if (runirvAvailable && input.equals("runirv"))
            {
                System.out.println("Running IRV.");
                runIRV();
            }

            //runpo command
            else if (runpoAvailable && input.equals("runpo"))
            {
                System.out.println("Running PO.");
                runPO();
            }

            //shuffle command
            else if (shuffleAvailable && input.equals("shuffle"))
            {
                shuffle = !shuffle;
                System.out.println("Shuffle toggled to " + shuffle + ".");
            }

            //generatereport command
            else if (generatereportAvailable && input.equals("generatereport"))
            {
                System.out.println("Generating a Report.");
                generateReport();
            }

            //displaywinners command
            else if (displaywinnersAvailable && input.equals("displaywinners"))
            {
                System.out.println("Displaying Winners.");
                displayWinners();
            }

            //exit command
            else if (input.equals("exit"))
            {
                System.out.println("Exiting.");
                exit = true;
            }

            //extraneous commands
            else
            {
                System.out.println("Command is currently unavailable or does not exist.");
            }
        }

        //close the scanner
        scanner.close();

        //close the fileHandler
        fileHandler.close();
    }

    /**
     * Adds the supplied String to the list of filenames to read input from
     *
     * @param filename a String representing the file to be added to the list of filenames to read input from
     */
    private static void addFileName(String filename)
    {
        //update the active fileHandler with the filename
        fileHandler.addFilename("./testing/" + filename);

        //update which commands are available
        deleteFilenameAvailable = true;
        runirvAvailable = true;
        runoplAvailable = true;
        runpoAvailable = true;
    }

    /**
     * Tries to remove the given filename from the list of filenames
     *
     * @param filename a String representing the file to try to delete from the list of filenames
     */
    private static void deleteFileName(String filename)
    {
        //get the filenames from the filehandler
        ArrayList<String> filenames = fileHandler.getFilenames();

        //try to remove the supplied filename from FileHandler's list of filenames
        if (filenames.remove("./testing/" + filename))
        {
            //report back to the user
            System.out.println("Successfully removed " + filename + " from the list of filenames");

            //if there are no more filenames, make this command unavailable
            deleteFilenameAvailable = filenames.size() != 0;
            runirvAvailable = deleteFilenameAvailable;
            runoplAvailable = deleteFilenameAvailable;
            runpoAvailable = deleteFilenameAvailable;
        }
        else
        {
            //report back to the user
            System.out.println("Failed to remove " + filename + " from the list of filenames");
        }
    }

    /**
     * runs an OPL election from the supplied input file
     */
    private static void runOPL()
    {
        //creates and runs an election
        election = new ElectionOPL(fileHandler, shuffle);
        runElection();
    }

    /**
     * runs an IRV election from the supplied input file
     */
    private static void runIRV()
    {
        //creates and runs an election
        election = new ElectionIRV(fileHandler, shuffle);
        runElection();
    }

    /**
     * runs a PO election from the supplied input file
     */
    private static void runPO()
    {
        //creates and runs an election
        election = new ElectionPO(fileHandler, shuffle);
        runElection();
    }

    /**
     * runs whatever election exists.
     */
    private static void runElection()
    {
        //try to read information form the supplied file
        boolean success = election.readInputs();

        //good read, no errors
        if (success)
        {
            election.calcResults();
            System.out.println("Successfully ran the election.");

            //update which commands are available
            shuffleAvailable = false;
            addFilenameAvailable = false;
            generatereportAvailable = true;
            displaywinnersAvailable = true;
        }

        //bad read, errors
        else
        {
            System.out.println("There was a problem with the supplied input file. Please input a new file.");

            //update which commands are available
            runirvAvailable = false;
            runoplAvailable = false;
            runpoAvailable = false;
        }
    }

    /**
     * generates a report from the results of the election
     */
    private static void generateReport()
    {
        election.generateReport();
    }

    /**
     * Displays the winners to the screen.
     */
    private static void displayWinners()
    {
        //gets the winners of the election
        ArrayList<Candidate> winners = election.getWinners();

        //special case if there is only one winner
        if (winners.size() == 1)
        {
            System.out.println(winners.get(0).getName() + " won the election!");
        }

        //standard case for any other possible number of winners
        else if (winners.size() > 1)
        {
            System.out.println("The following candidates won the election:");

            for (Candidate candidate : winners)
            {
                System.out.println("\t" + candidate.getName());
            }
        }
    }
}
