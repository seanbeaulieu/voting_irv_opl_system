package Misc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Used to get election information from a given input file, and to output information to audit/report files
 * Written by Jasper Rutherford
 */
public class FileHandler
{
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
     * true if the supplied input file exists
     * false otherwise
     */
    private boolean inputFileExists;

    /**
     * creates a file handler which draws from the supplied input file
     * @param inputFile the file to draw
     */
    public FileHandler(String inputFile)
    {
        setInputFile(inputFile);

        //set up the audit file
        try
        {
            audit = new FileWriter("audit.txt");
        }
        catch (IOException e)
        {
            System.out.println("An error occurred while setting up the audit file.");
            e.printStackTrace();
        }

        //set up the report file
        try
        {
            report = new FileWriter("report.txt");
        }
        catch (IOException e)
        {
            System.out.println("An error occurred while setting up the audit file.");
            e.printStackTrace();
        }
    }

    /**
     * true if the supplied input file exists
     * false otherwise
     * @return a boolean representing whether or not the supplied input file exists
     */
    public boolean inputFileExists()
    {
        return inputFileExists;
    }

    /**
     * tries to set the supplied inputFile
     * @param inputFile the file to draw input from
     */
    public void setInputFile(String inputFile)
    {
        try
        {
            input = new Scanner(new File(inputFile));
            inputFileExists = true;
        }
        catch (FileNotFoundException e)
        {
            inputFileExists = false;
        }
    }

    /**
     * gets the next line from the input file
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
     *
     *      if the file is:
     *      "
     *      4 5
     *      6
     *      7
     *      8 9 1
     *      2
     *      "
     *      and nextInt() is called 5 times, it should return 4, 6, 7, 8, 2
     *
     * @return an int representing the first number found on the next line
     */
    public int nextInt()
    {
        int out = input.nextInt();
        String dump = input.nextLine();

        return out;
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
    }

    /**
     * records the provided string to the audit file
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
}
