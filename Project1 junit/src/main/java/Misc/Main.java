package Misc;

import Candidates.Candidate;
import Elections.Election;
import Elections.ElectionIRV;
import Elections.ElectionOPL;

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

    //where election data will be read from
    private static String filename;

    private static Election election;

    //whether or not the following commands are available to be ran
    private static boolean filenameAvailable = true;
    private static boolean runoplAvailable = false;
    private static boolean runirvAvailable = false;
    private static boolean shuffleAvailable = true;
    private static boolean generatereportAvailable = false;
    private static boolean displaywinnersAvailable = false;

    /**
     * Used to start the program
     * @param args anything sent in from the user (all ignored)
     */
    public static void main(String[] args)
    {
        //used for command line input
        Scanner scanner = new Scanner(System.in);

        //loop until program should exit
        while (!exit)
        {
            System.out.println("Awaiting Input.");
            String input = scanner.nextLine().toLowerCase();

            //filename command
            if (filenameAvailable && input.equals("filename"))
            {
                System.out.println("Awaiting File Name.");
                String filename = scanner.nextLine();
                System.out.println("'" + filename + "'");

                setFileName(filename);
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
     * Sets the filename to be the supplied string
     *
     * @param filename the file to be set as the input file for election data
     */
    private static void setFileName(String filename)
    {
        //save the inputfile
        Main.filename = filename;

        //if filehandler does not exist, create it
        if (fileHandler == null)
        {
            fileHandler = new FileHandler(filename);
        }

        //otherwise just update the active fileHandler
        else
        {
            fileHandler.setInputFile(filename);
        }

        //update which commands are available
        runirvAvailable = true;
        runoplAvailable = true;
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
     * runs whatever election exists.
     */
    private static void runElection()
    {
        boolean success = election.calcResults();

        //good election, no errors
        if (success)
        {
            System.out.println("Successfully ran the election.");

            //update which commands are available
            shuffleAvailable = false;
            filenameAvailable = false;
            generatereportAvailable = true;
            displaywinnersAvailable = true;
        }

        //bad election, errors
        else
        {
            System.out.println("There was a problem with the supplied input file. Please input a new file.");

            //update which commands are available
            runirvAvailable = false;
            runoplAvailable = false;
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
