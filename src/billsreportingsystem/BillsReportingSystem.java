package billsreportingsystem;

// Layout & Java GUI Imports
import javax.swing.*;
import java.awt.*;

/**
 * The main file for Bills Reporting System
 *
 * @author Harrison Howard
 */
public class BillsReportingSystem extends JFrame {

    // Variables to define the amount of students and questions
    // Will be defined by read file
    int numOfStudents = 0;
    int numOfQuestions = 0;

    // Variables to define the amount of rows and columns
    // Will be updated by read file
    int totalX = 2;
    int totalY = 1;
    // Variables to define the maximum rows and columns
    // Used for the creation of fields
    int maxTotalX = 30;
    int maxTotalY = 20;

    // Creates a new multi-dimensional array for our text fields
    JTextField[][] txtFields = new JTextField[maxTotalX + (totalX + 1)][maxTotalY + (totalY + 1)];
    // Define buttons
    JButton btnSort, btnFind, btnBinSearch, btnClearFind;
    JTextField txtFind, txtBinary;

    // Define MenuBar, Menu and MenuItem
    JMenuBar myMenuBar;
    JMenu menuFile, menuEdit;
    JMenuItem itemNewStudent, itemNewQuestion,
            itemSaveTable, itemSaveRAF,
            itemDeleteStudent, itemDeleteQuestion,
            itemClearAll, itemClearStudents, itemClearQuestions,
            itemRestoreAll;

    // String array to define the default headings for X & Y
    String[] headingsX = {"Answers", "Average"};
    String[] headingsY = {"Questions", "Results"};

    // Creates a new instance of EventHandling
    // EventHandling handles all of the events
    EventHandling event = new EventHandling();

    public static void main(String[] args) {
        // Creates a new instance of BillsReportingSystem
        // To execute a non static method in a static context
        new BillsReportingSystem().InitailizeFrame();
    }

    /**
     * Instantiate the Frame and set the properties
     */
    private void InitailizeFrame() {
        // Make a new instance of FileManagement
        // Call the method "getTotals"
        // set the current entries to the new totals
        FileManagement filemng = new FileManagement();
        int[] totals = filemng.getTotals();
        setCurrentEntries(totals[0], totals[1]);

        // Set the size of the frame
        // Set the location to the centre of the screen
        setSize(totalY * 75 + 120, totalX * 25 + 148);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);

        // Set the title of the frame
        setTitle("Reporting System");

        // Call method
        displayGUI();

        // Set the frame resizablity
        // Set the frame visibility
        setResizable(false);
        setVisible(true);
    }

    /**
     * Display the Graphic User Interface Components
     */
    private void displayGUI() {
        // New instance of SpringLayout
        // Set the frame layout to springLayout
        SpringLayout springLayout = new SpringLayout();
        setLayout(springLayout);

        // Give the event instance the Frame and SpringLayout
        event.setFrame(this, springLayout);

        // Display all the components
        displayTextFields(springLayout);
        displayButtons(springLayout);
        setFields();

        // Display Menus
        displayMenuBar();
        displayMenu();
        displayMenuItem();
        // Set MenuBar to frame
        setJMenuBar(myMenuBar);
    }

    /**
     * Display the MenuBar
     */
    private void displayMenuBar() {
        // Create a new MenuBar instance
        myMenuBar = new JMenuBar();
    }

    /**
     * Display the Menus
     */
    private void displayMenu() {
        // Create the Menus
        // MENUBAR, MENU, MENUNAME
        menuFile = LibraryComponents.LocateMenu(myMenuBar, menuFile, "File");
        menuEdit = LibraryComponents.LocateMenu(myMenuBar, menuEdit, "Edit");
    }

    /**
     * Display the MenuItems
     */
    private void displayMenuItem() {
        // Create the MenuItems
        // EVENT, MENU, MENUITEM, MENUITEMNAME
        // MENU FILE ITEMS
        itemNewStudent = LibraryComponents.LocateMenuItem(event, menuFile, itemNewStudent, "New Student");
        itemNewQuestion = LibraryComponents.LocateMenuItem(event, menuFile, itemNewQuestion, "New Question");
        menuFile.addSeparator();
        itemSaveTable = LibraryComponents.LocateMenuItem(event, menuFile, itemSaveTable, "Save as CSV");
        itemSaveRAF = LibraryComponents.LocateMenuItem(event, menuFile, itemSaveRAF, "Save as RAF");
        menuFile.addSeparator();
        itemDeleteStudent = LibraryComponents.LocateMenuItem(event, menuFile, itemDeleteStudent, "Delete a Student");
        itemDeleteQuestion = LibraryComponents.LocateMenuItem(event, menuFile, itemDeleteQuestion, "Delete a Question");

        // MENU EDIT ITEMS
        itemClearAll = LibraryComponents.LocateMenuItem(event, menuEdit, itemClearAll, "Clear All");
        itemClearStudents = LibraryComponents.LocateMenuItem(event, menuEdit, itemClearStudents, "Clear Students");
        itemClearQuestions = LibraryComponents.LocateMenuItem(event, menuEdit, itemClearQuestions, "Clear Answers");
        menuEdit.addSeparator();
        itemRestoreAll = LibraryComponents.LocateMenuItem(event, menuEdit, itemRestoreAll, "Restore All");

        // Set the MenuItems in the event instance
        event.setMenuItems(
                // FILE ITEMS
                new JMenuItem[]{itemNewStudent, itemNewQuestion,
                    itemSaveTable, itemSaveRAF,
                    itemDeleteStudent, itemDeleteQuestion},
                // EDIT ITEMS
                new JMenuItem[]{itemClearAll, itemClearStudents, itemClearQuestions,
                    itemRestoreAll});
    }

    /**
     * Display the text fields
     *
     * @param layout The layout used on the frame
     */
    private void displayTextFields(SpringLayout layout) {
        // Loop through all the Y Positions and X Positions
        for (int y = 0; y <= totalY; y++) {
            for (int x = 0; x <= totalX; x++) {
                // Set the X & Y position of the text field
                int xPos = x * 25 + 20;
                int yPos = y * 75 + 20;
                // Create the text field
                // Add a key listener to the text field
                // FRAME, LAYOUT, TEXT, SIZE, LOCATION
                txtFields[x][y] = LibraryComponents.LocateTextField(this, layout, "", new int[]{70, 20}, new int[]{xPos, yPos});
                txtFields[x][y].addKeyListener(event);
            }
        }

        // Create a new FileManagement instance
        // Read & Display the file
        FileManagement filemng = new FileManagement(txtFields, new int[]{totalX, totalY});
        filemng.readFile();
        // Set the X & Y Position of the text field
        int txtXValue = (totalX + 1) * 25 + 31;
        int txtYValue = totalY * 75 + 10;
        // Create the text fields
        // FRAME, LAYOUT, TEXT, SIZE, LOCATION
        txtFind = LibraryComponents.LocateTextField(this, layout, "", new int[]{100, 23}, new int[]{txtXValue, 105});
        txtBinary = LibraryComponents.LocateTextField(this, layout, "", new int[]{100, 23}, new int[]{txtXValue, 349});
    }

    /**
     * Display the buttons
     *
     * @param layout The layout used on the frame
     */
    private void displayButtons(SpringLayout layout) {
        // Set the X & Y Position of the buttons
        int btnXValue = (totalX + 1) * 25 + 30;
        int btnYValue = totalY * 75 + 10;
        // Create the buttons
        // EVENT, FRAME, LAYOUT, NAME, LOCATION, SIZE
        btnSort = LibraryComponents.LocateButton(event, this, layout, "Sort", new int[]{btnYValue, btnXValue}, new int[]{80, 25});
        btnFind = LibraryComponents.LocateButton(event, this, layout, "Find", new int[]{20, btnXValue}, new int[]{80, 25});
        btnBinSearch = LibraryComponents.LocateButton(event, this, layout, "Binary Search", new int[]{230, btnXValue}, new int[]{114, 25});

        // Buttons the are not visible upon loading
        btnClearFind = LibraryComponents.LocateButton(event, this, layout, "Clear", new int[]{20, btnXValue}, new int[]{80, 25});
        btnClearFind.setVisible(false);

        // Set the components in the event instance
        // Add the frame to the window listener in the event instance
        event.setComponents(
                new JButton[]{btnSort, btnFind, btnBinSearch, btnClearFind},
                txtFields,
                new JTextField[]{txtFind, txtBinary},
                new int[]{totalX, totalY});
        this.addWindowListener(event);

        // Create a new FileManagement instance
        // Calculate & Display the student results
        FileManagement filemng = new FileManagement(txtFields, new int[]{totalX, totalY});
        filemng.calculateResults();
    }

    /**
     * Used to set the colouring/formatting and default names of fields
     */
    public void setFields() {
        // Defining the different colours being used
        // Background colours
        int[] yColor = new int[]{89, 89, 255}; // LIGHTESH BLUE
        int[] yLightColor = new int[]{143, 143, 255}; // LIGHTER BLUE
        int[] nameColor = new int[]{248, 255, 120}; // LIGHTESH YELLOW
        int[] xyplusColor = new int[]{99, 242, 114}; // LIGHTEST GREEN
        int[] lastColor = new int[]{168, 168, 168}; // GRAY
        // Foreground colours
        int[] whiteFG = new int[]{255, 255, 255}; // WHITE
        int[] blackFG = new int[]{0, 0, 0}; // BLACK

        // Set all the fields on the Y AXIS
        txtFields[0][0].setText(headingsY[0]);
        fieldProperties(0, 0, yColor, whiteFG, false);
        txtFields[0][totalY].setText(headingsY[1]);
        fieldProperties(0, totalY, yColor, whiteFG, false);
        // Set all the Questions
        for (int y = 1; y < totalY; y++) {
            txtFields[0][y].setText("Q" + y);
            fieldProperties(0, y, yColor, whiteFG, false);
        }
        // Set the Average
        for (int y = 1; y < totalY; y++) {
            fieldProperties(totalX, y, xyplusColor, blackFG, false);
        }

        // Set all the fields o nthe X AXIS
        txtFields[1][0].setText(headingsX[0]);
        fieldProperties(1, 0, yLightColor, blackFG, false);
        txtFields[totalX][0].setText(headingsX[1]);
        fieldProperties(totalX, 0, yLightColor, blackFG, false);
        // Set all the Names
        for (int x = 2; x < totalX; x++) {
            fieldProperties(x, 0, nameColor, blackFG, true);
        }
        // Set all the Results
        for (int x = 1; x < totalX; x++) {
            fieldProperties(x, totalY, xyplusColor, blackFG, false);
        }

        // Set the bottom right field (Average)
        fieldProperties(totalX, totalY, lastColor, whiteFG, false);
        txtFields[totalX][totalY].setFont(txtFields[totalX][totalY].getFont().deriveFont(Font.BOLD, 12f));
    }

    /**
     * Set the editable state, background and foreground of a text field
     *
     * @param x the x position
     * @param y the y position
     * @param color an int array to signify the background in R,G,B
     * @param fcolor an int array to signify the foreground in R,G,B
     * @param edit a boolean to signify the editable state
     */
    private void fieldProperties(int x, int y, int[] color, int[] fcolor, boolean edit) {
        // Set the editable state
        txtFields[x][y].setEditable(edit);
        // Set the background
        txtFields[x][y].setBackground(new Color(color[0], color[1], color[2]));
        // Set the foreground
        txtFields[x][y].setForeground(new Color(fcolor[0], fcolor[1], fcolor[2]));
    }

    /**
     * Set the currents entries in this instance
     *
     * @param studentNums The amount of students
     * @param questionNums The amount of questions
     */
    public void setCurrentEntries(int studentNums, int questionNums) {
        // Set the Students
        numOfStudents = studentNums;
        // Set the totalX (Students + 2)
        totalX = studentNums + 2;
        // Set the Questions
        numOfQuestions = questionNums;
        // Set the totalY (Questions + 1)
        totalY = questionNums + 1;
    }
}
