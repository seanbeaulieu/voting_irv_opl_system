package Everything;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Runs tests that involve the FileHandler class
 * Written by Ann Huynh and Sean Beaulieu
 */
class FileHandlerTest {

    @Test
    @DisplayName("Test to Add Files as a List")
    void addFilesAsList() {
        FileHandler fh = new FileHandler();
        ArrayList<String> test_files = new ArrayList<>();

        String file1 = "irvm1.csv";
        String file2 = "irvm2.csv";
        String file3 = "irvm3.csv";

        test_files.add(file1);
        test_files.add(file2);
        test_files.add(file3);

        fh.addFilename("irvm1.csv");
        fh.addFilename("irvm2.csv");
        fh.addFilename("irvm3.csv");

        assertEquals(test_files, fh.getFilenames());
    }

    @Test
    @DisplayName("Test the Get Number of Files")
    void testGetSize() {
        FileHandler fh = new FileHandler();
        ArrayList<String> test_files = new ArrayList<>();

        fh.addFilename("irvm1.csv");
        fh.addFilename("irvm2.csv");
        fh.addFilename("irvm3.csv");

        assertEquals(3, fh.getNumFilenames());
    }





}
