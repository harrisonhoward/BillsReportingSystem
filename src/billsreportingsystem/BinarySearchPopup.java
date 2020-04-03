package billsreportingsystem;

// Layout & Java GUI Imports
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * Binary Search Form for EventHandling
 * @author Harrison Howard
 */
public class BinarySearchPopup extends JFrame {

    // Create a variable for the total names
    int totalNames = 0;

    // Create a variable for the user's search
    // And all of the student names provided
    String userSearch = "";
    String[] allNames = new String[totalNames];

    // Create a variable for the instantiation of the text area
    JTextArea txtaraBinary;

    /**
     * Create a new instance of BinarySearchPopup
     * @param namesTotal the amount of student names
     * @param namesAll all of the student names
     * @param search the user's search
     */
    public BinarySearchPopup(int namesTotal, String[] namesAll, String search) {
        totalNames = namesTotal;
        allNames = namesAll;
        userSearch = search;
        
        // Call the method "initializeFrame"
        initializeFrame();
    }

    /**
     * Initializes the frame
     */
    private void initializeFrame() {
        // Set the size of the frame
        // Set the location to the centre of the screen
        setSize(355, ((int) Math.round(totalNames / 1.6)) * 25 + 148);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);

        // Set the title of the frame
        setTitle("Binary Search Result");

        // Call the method "displayGUI"
        displayGUI();

        // Set the frame resizablity
        // Set the frame visibility
        setResizable(false);
        setVisible(true);
    }
    
    /**
     * Creates the Graphic User Interface
     */
    private void displayGUI() {
        // New instance of SpringLayout
        // Set the frame layout to springLayout
        SpringLayout springLayout = new SpringLayout();
        setLayout(springLayout);

        // Display text area
        displayTextArea(springLayout);
        
        // Call methid "binarySearch"
        binarySearch();
    }
    
    /**
     * Displays the text area
     * @param layout the layout of the frame
     */
    private void displayTextArea(SpringLayout layout) {
        // Creates the text area
        // FRAME, LAYOUT, TEXT, SIZE, LOCATION, EDITABLE STATE
        txtaraBinary = LibraryComponents.LocateTextArea(this, layout, "", new int[] {30, totalNames}, new int[] {10, 10}, false);
    }
    
    /**
     * Does the binary search
     */
    private void binarySearch() {
        // Set the text which appears at the top of the text area
        txtaraBinary.setText("Sorted Student Names\n");
        txtaraBinary.append("--------------------------\n");
        // If the amount of names is greater than 0
        // Else the binary search result is -1 (not found)
        if (totalNames > 0) {
            // Sort all of the student names
            Arrays.sort(allNames, 0, totalNames - 1);
            // Loop the student names
            for (int i = 0; i < totalNames; i++) {
                // Append the student names to the text area
                txtaraBinary.append(allNames[i] + "\n");
            }
            // Binary search the sorted array
            // Create a variable which will be the result of the binary search
            int result = Arrays.binarySearch(allNames, 0, totalNames - 1, userSearch);
            // Append the binary search result
            // Append what the user searched for
            // Set the cursor to the top of the text area
            txtaraBinary.append("\nBinary Search result = " + result);
            txtaraBinary.append("\nUser Search = " + userSearch);
            txtaraBinary.setCaretPosition(0);
        } else {
            txtaraBinary.append("\nBinary Search result = -1");
        }
    }
}
