package billsreportingsystem;

// Import the applicable Java IO libraries
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.util.Arrays;
// Swing Imports
import javax.swing.*;

/**
 * The file management for Bills Reporting System
 *
 * @author Harrison Howard
 */
public class FileManagement {

    // The name of the file to save to
    private String dataFileName = "BillsReportingSystem.csv";

    // The amount of students and questions
    int numOfStudents = 0;
    int numOfQuestions = 0;

    // The amount of total fields on the X & Y axis
    private int totalX = 2;
    private int totalY = 1;

    // The multi-dimensional array of text fields
    private JTextField[][] txtFields;

    /**
     * Create a new instance of File Management with
     *
     * @param txtArray the multi-dimensional array of text fields
     * @param fieldsAxis an array of the X & Y axis
     */
    public FileManagement(JTextField[][] txtArray, int[] fieldsAxis) {
        txtFields = txtArray;
        totalX = fieldsAxis[0];
        totalY = fieldsAxis[1];
        // Call the method "calculateFields"
        calculateFields();
    }

    /**
     * Create a new instance of File Management Solely to calculate fields
     */
    public FileManagement() {
        // Call the method "calculateFields"
        calculateFields();
    }

    /**
     * Reads and displays the CSV File
     */
    public void readFile() {
        // Try to read and display the CSV File
        // Catch any exceptions
        try {
            // Instantiate a new Buffered Reader with the dataFileName
            BufferedReader br = new BufferedReader(new FileReader(dataFileName));

            // Loop the fields on the X Axis (Starting from 1)
            for (int x = 1; x < totalX; x++) {
                // Instantiate readLine
                String readLine;
                // Check if the Buffered Reader has a next line
                // Set the next line to readLine
                if ((readLine = br.readLine()) != null) {
                    // Loop the fields on the Y Axis
                    for (int y = 0; y < totalY; y++) {
                        // Instantiate the temp array
                        // Holds all the values in the CSV split with ","
                        String temp[] = readLine.split(",");
                        // Check if the there is a value
                        // Check if the value has a length bigger than 0
                        if (temp.length > y && temp[y].length() > 0) {
                            // Set the field to the value
                            txtFields[x][y].setText(temp[y]);
                        }
                    }
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error Reading:");
            // Print the error in console
            e.printStackTrace();
        }
    }

    /**
     * Save the table to a CSV File
     */
    public void saveTable() {
        // Try to save the file
        // Catch any exceptions
        try {
            // Instantiate a new Buffered Writer with the dataFileName
            BufferedWriter bw = new BufferedWriter(new FileWriter(dataFileName));

            // Loop the fields on the X Axis (Starting from 1)
            // Loop the fields on the Y Axis
            for (int x = 1; x < totalX; x++) {
                for (int y = 0; y < totalY; y++) {
                    // Print each value 1 by 1 seperated by a "," (except the last value)
                    bw.write(txtFields[x][y].getText() + (y == totalY - 1 ? "" : ","));
                }
                // Print a new line when finished
                bw.write("\n");
            }
            bw.close();
        } catch (Exception e) {
            System.out.println("Error Saving CSV:");
            // Print the error in console
            e.printStackTrace();
        }
    }

    /**
     * Save the table to a Random Access File (RAF)
     *
     * @param fileName the name of the file
     */
    public void saveRAF(String fileName) {
        // Try to save to a RAF
        // Catch any exceptions
        try {
            // Instantiate a new Random Access File with
            // The file name and with Read & Write access (rw)
            RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
            // Create a variable with acts as a new line
            byte[] newLine = "\n".getBytes("UTF-8");

            // Loop the fields on the X Axis (Starting from 1)
            // Loop the fields on the Y Axis
            for (int x = 1; x < totalX; x++) {
                for (int y = 0; y < totalY; y++) {
                    // Create a variable which holds the value with a "," (except the last value)
                    String line = txtFields[x][y].getText() + (y == totalY - 1 ? "" : ",");
                    // Convert the variable to an array of bytes
                    byte[] bytes = line.getBytes("UTF-8");
                    // Write the bytes to the Random Access File
                    raf.write(bytes);
                }
                // Write a new line
                raf.write(newLine);
            }
            raf.close();
        } catch (Exception e) {
            System.out.println("Error Saving RAF:");
            // Print the error in console
            e.printStackTrace();
        }
    }

    /**
     * Calculate the amount correct answers provided by students
     */
    public void calculateResults() {
        // Create an Array for the Answers
        String[] ansArr = new String[totalY - 1];
        // Create an Multi-Dimensional Array for the Students
        String[][] studentArr = new String[totalX][totalY];
        // Create an Int Array for the Results
        int[] resultsArr = new int[totalX < 2 ? 1 : totalX - 2];

        // Create a variable for the Correct Answers & Max Answers
        int correctAns = 0;
        int maxAns = totalY - 1;
        // Loop the fields on the X Axis (Starting from 1)
        // Loop the fields on the Y Axis (Starting from 1)
        for (int x = 1; x < totalX; x++) {
            for (int y = 1; y < totalY; y++) {
                // If Y is on start position reset Correct Answer
                if (y == 1) {
                    correctAns = 0;
                }
                // If X is on start position set the Answers Array
                if (x == 1) {
                    ansArr[y - 1] = txtFields[x][y].getText();
                } else {
                    // Create a string for the text on the text field
                    String fieldText = txtFields[x][y].getText();
                    // Add the text on the field to the multi-dimensional array for Students
                    studentArr[x][y] = fieldText;
                    // If the student answer matches the correct answer then + the correct answers
                    if (ansArr[y - 1].length() > 0 && fieldText.equals(ansArr[y - 1])) {
                        correctAns++;
                    }

                    // If Y is on end position update the results
                    // Update results array with correct answer
                    if (y == (totalY - 1)) {
                        txtFields[x][totalY].setText(correctAns + "/" + maxAns);
                        resultsArr[x - 2] = correctAns;
                    }
                }
            }
            // If X is on end position calculate the average
            if (x == (totalX - 1)) {
                // Create a variable for the average
                double avgValue = 0;
                // Loop the results array
                // Update the average with the new result
                for (int i = 0; i < resultsArr.length; i++) {
                    avgValue += resultsArr[i];
                }
                // Set the text field with the average
                // Set the cursor position to the start
                txtFields[x + 1][totalY].setText((avgValue / resultsArr.length) + "");
                txtFields[x + 1][totalY].setCaretPosition(0);
            }
        }
        // Create a variable for mode value and the max count
        int modeValue = 0, maxCount = 0;
        // Create an Int Array for the answers
        int[] xAns = new int[totalX < 2 ? 1 : totalX - 2];
        // Loop the multi-dimensional array for students
        for (int y = 1; y < totalY; y++) {
            for (int x = 2; x < totalX; x++) {
                // Set the current as the students answer
                String currentAns = studentArr[x][y];
                // Convert the students answer to a number (if A,B,C,D,E)
                // Otherwise set it to 0
                if (currentAns != null) {
                    switch (currentAns) {
                        case "A":
                            xAns[x - 2] = 1;
                            break;
                        case "B":
                            xAns[x - 2] = 2;
                            break;
                        case "C":
                            xAns[x - 2] = 3;
                            break;
                        case "D":
                            xAns[x - 2] = 4;
                            break;
                        case "E":
                            xAns[x - 2] = 5;
                            break;
                        default:
                            xAns[x - 2] = 0;
                            break;
                    }
                }
            }
            // Sort the answers array and calculate the mode
            Arrays.sort(xAns);
            // Loop the answers array
            for (int i = 0; i < xAns.length; i++) {
                // Set the current count to 0
                int count = 0;
                // Loop answers array again
                for (int j = 0; j < xAns.length; j++) {
                    // Compare the new value with the current value
                    // If the same + the count
                    if (xAns[j] == xAns[i]) {
                        count++;
                    }
                }
                // If the count is bigger than the max count
                // Update max count with count
                // Update the mode value with the new value
                if (count > maxCount) {
                    maxCount = count;
                    modeValue = xAns[i];
                }
            }
            // Convert the numbers back to the student answers
            String modeStr = "";
            switch (modeValue) {
                case 1:
                    modeStr = "A";
                    break;
                case 2:
                    modeStr = "B";
                    break;
                case 3:
                    modeStr = "C";
                    break;
                case 4:
                    modeStr = "D";
                    break;
                case 5:
                    modeStr = "E";
                    break;
                default:
                    modeStr = " ";
                    break;
            }
            // Set the mode value in the text field
            txtFields[totalX][y].setText(modeStr);
            // Reset the mode value and count
            maxCount = 0;
            modeValue = 0;
        }
    }

    /**
     * Calculate the amount of students and questions
     */
    private void calculateFields() {
        // Try to calculate the amount
        // Catch any exceptions
        try {
            // Instantiate a new Buffered Reader with dataFileName
            BufferedReader br = new BufferedReader(new FileReader(dataFileName));
            // Instantiate a new readline
            String readLine;
            // If the next line on Buffered Reader exists
            // Set the readLine to the next line
            while ((readLine = br.readLine()) != null) {
                // Increase the amount of students
                numOfStudents++;
                // Create an array with the values on readLine
                String temp[] = readLine.split(",");
                // Update the number of questions
                // If the amount of questions in temp is greater than the previous amount of questions
                numOfQuestions = numOfQuestions > temp.length - 1 ? numOfQuestions : temp.length - 1;
            }
            // Decrease the amount of students by 1
            // Note: This is because our file contains a line for the Answers
            //       This means that it's counted as a student even though it isn't
            numOfStudents--;

            br.close();
        } catch (Exception e) {
            System.out.println("Error Calculating Totals:");
            // Print the error in console
            e.printStackTrace();
        }
    }

    /**
     * Get the amount of students and questions
     *
     * @return returns an Int Array containing the values [Students, Questions]
     */
    public int[] getTotals() {
        return new int[]{numOfStudents, numOfQuestions};
    }
}
