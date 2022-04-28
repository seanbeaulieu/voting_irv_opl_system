package Everything;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Used to get election information from a given input file, and to output information to audit/report files
 * Written by Jasper Rutherford
 */
public class FileHandler
{
    /**
     * The list of all filenames which have been supplied by the user
     */
    private ArrayList<String> filenames;

    /**
     * Where stuff will be read from
     */
    private Scanner input;

    /**
     * Where Audit information will be logged
     */
    private FileWriter audit;

    /**
     * Where Report information will be logged
     */
    private FileWriter report;

    /**
     * Where InvalidBallot information will be logged
     */
    private FileWriter invalidBallot;

    /**
     * true if the supplied input file exists
     * false otherwise
     */
    private boolean inputFileExists;

    /**
     * creates a default FileHandler which has no files to draw input from.
     */
    public FileHandler()
    {
        filenames = new ArrayList<>();
        inputFileExists = false;

        //set up the audit file
        try
        {
            audit = new FileWriter("./testing/audit.txt");
        }
        catch (IOException e)
        {
            System.out.println("An error occurred while setting up the audit file.");
            e.printStackTrace();
        }

        //set up the report file
        try
        {
            report = new FileWriter("./testing/report.txt");
        }
        catch (IOException e)
        {
            System.out.println("An error occurred while setting up the report file.");
            e.printStackTrace();
        }

        //set up the invalidBallot file
        try
        {
            //date code from https://www.tutorialkart.com/java/how-to-get-current-date-in-yyyy-mm-dd-format-in-java/
            LocalDate dateObj = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
            String date = dateObj.format(formatter);
            invalidBallot = new FileWriter("./testing/invalidated_" + date + ".txt");
        }
        catch (IOException e)
        {
            System.out.println("An error occurred while setting up the invalid ballots file.");
            e.printStackTrace();
        }
    }

    /**
     * blindly adds the given file to the list of filenames
     *
     * @param filename the filename to be added to this FileHandler's list of filenames
     */
    public void addFilename(String filename)
    {
        filenames.add(filename);
    }

    /**
     * gets the next line from the input file
     *
     * @return a string representing the next line from the input file
     */
    public String nextLine()
    {
        if (inputFileExists)
        {
            return input.nextLine();
        }
        else
        {
            System.out.println("Tried to get the next input line when the file did not exist. This is expected to never happen.");
            return null;
        }
    }

    /**
     * reads in the next line of input and returns the first number found in that line
     * This is expected to work as such:
     * <p>
     * if the file is:
     * "
     * 4 5
     * 6
     * 7
     * 8 9 1
     * 2
     * "
     * and nextInt() is called 5 times, it should return 4, 6, 7, 8, 2
     *
     * @return an int representing the first number found on the next line
     */
    public int nextInt()
    {
        String[] nextLineArgs = input.nextLine().split(",");
        return Integer.parseInt(nextLineArgs[0]);
    }

    /**
     * closes all the files
     */
    public void close()
    {
        //close input file
        if (inputFileExists)
        {
            input.close();
        }

        //close audit file
        try
        {
            audit.close();
        }
        catch (IOException e)
        {
            System.out.println("There was an exception while closing the audit file");
        }

        //close report file
        try
        {
            report.close();
        }
        catch (IOException e)
        {
            System.out.println("There was an exception while closing the report file");
        }

        //close invalid ballots file
        try
        {
            invalidBallot.close();
        }
        catch (IOException e)
        {
            System.out.println("There was an exception while closing the invalidBallots file");
        }
    }

    /**
     * records the provided string to the audit file
     *
     * @param log the string to record to the audit file
     */
    public void auditLog(String log)
    {
        //write to audit file
        try
        {
            audit.write(log + "\n");
        }
        catch (IOException e)
        {
            System.out.println("There was an exception while writing to the audit file");
        }
    }

    /**
     * records the provided string to the report file
     *
     * @param log the string to record to the report file
     */
    public void reportLog(String log)
    {
        //write to report file
        try
        {
            report.write(log + "\n");
        }
        catch (IOException e)
        {
            System.out.println("There was an exception while writing to the report file");
        }
    }

    /**
     * records the provided string to the invalidBallots file
     *
     * @param log the string to record to the invalidBallots file
     */
    public void invalidBallotLog(String log)
    {
        //write to report file
        try
        {
            invalidBallot.write(log + "\n");
        }
        catch (IOException e)
        {
            System.out.println("There was an exception while writing to the invalid ballots file");
        }
    }

    /**
     * Advances the FileHandler's Scanner to the next file in the list
     *
     * @return true if a file was successfully opened, false otherwise
     */
    public boolean nextInputFile()
    {
        //close any previously opened input file
        if (inputFileExists)
        {
            input.close();
        }

        //return false if there are no more filenames
        if (filenames.isEmpty())
        {
            return false;
        }

        //get the next filename
        String nextFilename = filenames.get(0);
        try
        {
            //open a new Scanner with that filename
            input = new Scanner(new File(nextFilename));

            //remove that filename from the list
            filenames.remove(0);

            inputFileExists = true;
            //success!
            return true;
        }
        catch (FileNotFoundException e)
        {
            //fail
            System.out.println("Failed to open " + nextFilename);
            return false;
        }
    }

    /**
     * Gets the number of filenames that are currently stored in this FileHandler object
     *
     * @return an int equal to filenames.size()
     */
    public int getNumFilenames()
    {
        return filenames.size();
    }

    /**
     * Gets a list of all filenames currently stored in this FileHandler object
     *
     * @return an ArrayList of Strings representing all filenames currently stored in this FileHandler object
     */
    public ArrayList<String> getFilenames()
    {
        return filenames;
    }
}
