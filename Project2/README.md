# repo-Team21
(Jasper Rutherford, Ann Huynh, Shey Pemberton, Sean Beaulieu)

This Voting System software allows an election official to run three different types of elections: 
Instant Runoff Voting (IRV), Open Party Listing (OPL), and Popularity Only (PO). 

........................................................................................................................

HOW TO RUN THE VOTING PROGRAM

1. Navigate to "repo-Team21/Project2/src/main/java/Everything"
2. Run Main.main

........................................................................................................................

COMMANDS

 Note: Commands are not case sensitive. ADDFILENAME is the same as addFileName is the same as addfilename, etc.

"addFilename" - Add a filename to the list of filenames which the program will draw inputs from.
 - After running this command, you will be prompted to insert a filename. The program will assume that this file is in the Project2/testing/ directory. 
 - Available until an election is ran.

"deleteFilename" - Delete a filename from the list of filenames which the program will draw inputs from. 
 - After running this command, you will be prompted to insert a filename. If this filename has already been added to the list of filenames, it will be removed. 
 - Available until an election is ran.

"shuffle" - Toggles whether or not to shuffle the vote order before running an IRV election. 
 - Optional, defaults to true.
 - Available until an election is ran.

"runOPL" - Run an OPL election based on the files which have been supplied.
 - Only available after at least one filename has been supplied. 
 - Available until an election is ran.

"runIRV" - Run an IRV election based on the files which have been supplied.
 - Only available after at least one filename has been supplied. 
 - Available until an election is ran.

"runPO" - Run a PO election based on the files which have been supplied.
 - Only available after at least one filename has been supplied. 
 - Available until an election is ran.

"generateReport" - Generate a report which contains some information about how many votes each candidate recieved. 
 - Only available after an election has been ran. 

"displayWinners" - Displays the candidates who won the election. 
 - Only available after an election has been ran. 

"exit" - Exits the program. 
 - Note: Any files which have been generated will be blank until this command is ran and the program closes. 

........................................................................................................................

TESTING

For testing we used JUnit, and to run the tests we do the following:
1. Open "repo-Team21/Project2" in IntelliJ
2. Press Alt + 1 to open the Project menu
3. Navigate to "repo-Team21/Project2/src/test/java"
4. Right click the "repo-Team21/Project2/src/test/java" folder (it should be green) and select "Run 'All Tests'"

........................................................................................................................

BUG LIST

 - When using "generateReport" after a PO election, the names of the candidates display incorrectly in the report.
 - When using "displayWinners" after a PO election, the names of the candidates display incorrectly. 
 - "generateReport" after all election types will typically generate a file with formatting issues. Everything should be in nice columns, but it often is not. 

........................................................................................................................

NOTES

Many of the files in the "repo-Team21/Project2/testing" folder are ".txt" files. This is because we realized late into the process that we were supposed to support
".csv" files, and we did not have a chance to go back and convert all the test files from ".txt" to ".csv". The program does have full support for ".csv" files.

........................................................................................................................