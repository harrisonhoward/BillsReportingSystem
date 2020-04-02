package billsreportingsystem;

// ActionListener Imports 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// WindowListener Imports
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
// KeyListener Imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.Comparator;
// Swing Imports
import javax.swing.*;
// Misc
import java.awt.Color;
import java.awt.Font;
import static javax.swing.JOptionPane.*;
import javax.swing.JOptionPane;

/**
 * The event handling for Bills Reporting System
 *
 * @author Harrison Howard
 */
public class EventHandling implements WindowListener, ActionListener, KeyListener {

    // The current X & Y Axis
    int totalX = 2;
    int totalY = 1;
    // The maximum X & Y Axis
    int maxTotalX = 30;
    int maxTotalY = 20;

    // Define the Frame, Layout, Fields and Buttons
    JFrame frame;
    SpringLayout layout;
    JTextField[][] txtFields;
    JTextField txtFind, txtBinary;
    JButton btnSort, btnFind, btnBinSearch, btnClearFind;

    // Define the MenuItems
    JMenuItem itemNewStudent, itemNewQuestion,
            itemSaveTable, itemSaveRAF,
            itemDeleteStudent, itemDeleteQuestion,
            itemClearAll, itemClearStudents, itemClearQuestions,
            itemRestoreAll;

    // The instance of the main instance in BillsReportingSystem
    // Note: This is so we can access public methods in the main instance
    //       This is so we don't have to create a new instance
    BillsReportingSystem billsObj;

    /**
     * Set the frame/screen
     *
     * @param myObj the instance of BillsReportingSystem
     * @param myLayout the layout of the frame
     */
    public void setFrame(BillsReportingSystem myObj, SpringLayout myLayout) {
        // Instantiate billsObj with the current instance
        billsObj = myObj;
        // Convert the instance to a JFrame
        frame = myObj;
        layout = myLayout;
    }

    /**
     * Set the components
     *
     * @param btnArray an Array of JButtons
     * @param txtArray the Multi-Dimensional Array of JTextFields
     * @param btnFields an Array of JTextFields (Which are related to a JButton)
     * @param txtFieldAxis an Array of the X & Y Axis
     */
    public void setComponents(JButton[] btnArray, JTextField[][] txtArray, JTextField[] btnFields, int[] txtFieldAxis) {
        btnSort = btnArray[0];
        btnFind = btnArray[1];
        btnBinSearch = btnArray[2];
        btnClearFind = btnArray[3];

        txtFind = btnFields[0];
        txtBinary = btnFields[1];

        txtFields = txtArray;

        totalX = txtFieldAxis[0];
        totalY = txtFieldAxis[1];
    }

    /**
     * Set the Menu Items
     *
     * @param fileItems the items in the file menu
     * @param editItems the items in the edit menu
     */
    public void setMenuItems(JMenuItem[] fileItems, JMenuItem[] editItems) {
        // FILE ITEMS
        itemNewStudent = fileItems[0];
        itemNewQuestion = fileItems[1];
        itemSaveTable = fileItems[2];
        itemSaveRAF = fileItems[3];
        itemDeleteStudent = fileItems[4];
        itemDeleteQuestion = fileItems[5];

        // EDIT ITEMS
        itemClearAll = editItems[0];
        itemClearStudents = editItems[1];
        itemClearQuestions = editItems[2];
        itemRestoreAll = editItems[3];
    }

    /**
     * The Action Event
     *
     * @param e the cause of the event
     */
    public void actionPerformed(ActionEvent e) {
        /*
         * BUTTON EVENTS
         */
        // BUTTON SORT
        if (e.getSource() == btnSort) {
            // Create an multi-dimensional array for the values to be sorted in
            String[][] sortArray = new String[totalX - 2][totalY];
            // Loop the text fields on the X Axis (Excluding 2 (Students))
            for (int x = 0; x < totalX - 2; x++) {
                // Set the first value to the student name
                sortArray[x][0] = txtFields[x + 2][0].getText();
                // Loop the text fields on the Y Axis (Answers)
                for (int y = 0; y < totalY; y++) {
                    // Set the value to the answer correlating the student
                    sortArray[x][y] = txtFields[x + 2][y].getText();
                }
            }
            // Sort the multi-dimensional array with the custom comparator
            // Comparing with the student name (0)
            Arrays.sort(sortArray, new String2DComparator(0));

            // Loop the text fields on the X Axis (Starting from 2)
            for (int x = 2; x < totalX; x++) {
                // Set the students name to the sorted student name
                txtFields[x][0].setText(sortArray[x - 2][0]);
                // Loop the text fields on the Y Axis (Starting from 1)
                for (int y = 1; y < totalY; y++) {
                    // Set the student answers to the sorted student answers
                    txtFields[x][y].setText(sortArray[x - 2][y]);
                }
            }
        }

        // BUTTON FIND
        if (e.getSource() == btnFind) {
            // Create a variable for the user's input
            String userInput = "";
            // Making sure that the user has entered input
            // Else show a dialog explaining why
            if (txtFind.getText().length() > 0) {
                // Set the variable with the user's input (all lower case)
                userInput = txtFind.getText().toLowerCase();
                // Create an int array for the highlight colour in R,G,B
                int[] highlightColor = new int[]{255, 230, 117};
                // Student Name's X Axis
                int xName = 0;

                // Create a variable if it is found and the x positing
                boolean isFound = false;
                int x = 2;
                // Loop until the value is found
                // And X is less than the total fields on the X Axis
                while (isFound == false && x < totalX) {
                    // Create a variable for the students name
                    // Set the students name (all lower case)
                    String studentName = txtFields[x][0].getText().toLowerCase();
                    // If the students name contains the users input
                    // Then update isFound and set the Student Name's X Axis
                    if (studentName.indexOf(userInput) > -1) {
                        isFound = true;
                        xName = x;
                    }
                    x++;
                }

                // Making sure that something was found
                // Else show a dialog explaining why
                if (isFound == true) {
                    // Loop the text fields on the Y Axis
                    for (int y = 0; y < totalY; y++) {
                        // Set the students name and answers to the highlight colour
                        txtFields[xName][y].setBackground(new Color(highlightColor[0], highlightColor[1], highlightColor[2]));
                    }
                    // Make the find button disappear
                    btnFind.setVisible(false);
                    // Show the clear button
                    btnClearFind.setVisible(true);
                    // Make sure the user can't input anything
                    txtFind.setEnabled(false);
                } else {
                    showMessageDialog(null, "Can not find \"" + userInput + "\"");
                }
            } else {
                showMessageDialog(null, "Please enter something to find");
            }
        }

        // BUTTON CLEAR FIND
        if (e.getSource() == btnClearFind) {
            // Show the find button
            btnFind.setVisible(true);
            // Hide the clear button
            btnClearFind.setVisible(false);
            // Allow the user to input in the field
            // Set the field to nothing
            txtFind.setEnabled(true);
            txtFind.setText("");

            // Create an int array for the colour of the 
            // Student Name & Answers in R,G,B
            int[] nameColor = new int[]{248, 255, 120};
            int[] ansColor = new int[]{255, 255, 255};
            // Loop the text fields on the X Axis (Starting from 2)
            for (int x = 2; x < totalX; x++) {
                // Set the students name to the name colour
                txtFields[x][0].setBackground(new Color(nameColor[0], nameColor[1], nameColor[2]));
                // Loop the text fields on the Y Axis (Starting from 1)
                for (int y = 1; y < totalY; y++) {
                    // Set the students answers to the answer colour
                    txtFields[x][y].setBackground(new Color(ansColor[0], ansColor[1], ansColor[2]));
                }
            }
        }

        // BUTTON BINARY SEARCH
        if (e.getSource() == btnBinSearch) {
            // Create an array for the student's names
            String[] studentNames = new String[totalX - 2];
            // Loop the text fields on the X Axis (Starting at 2)
            for (int x = 2; x < totalX; x++) {
                // Set the array to the student's name
                studentNames[x - 2] = txtFields[x][0].getText();
            }
            // Create a new instance of Binary Search Popup
            // TOTAL NAMES, STUDENT NAMES, SEARCH PARAMETER
            new BinarySearchPopup(totalX - 2, studentNames, txtBinary.getText());
            // Set the field to nothing
            txtBinary.setText("");
        }

        /*
         * MENU ITEM EVENTS
         */
        // ITEM NEW STUDENT
        if (e.getSource() == itemNewStudent) {
            // If the number of students is less than the max number of students
            // Else show a dialog explaining why
            if ((totalX - 2) < maxTotalX) {
                // Update screen with +1 student
                updateScreen(new int[]{totalX + 1, totalY});
            } else {
                showMessageDialog(null, "Can't add more than " + maxTotalX + " students");
            }
        }

        // ITEM NEW QUESTION
        if (e.getSource() == itemNewQuestion) {
            // If the number of questions is less than the max number of questions
            // Else show a dialog explaining why
            if (totalY <= maxTotalY) {
                // Update screen with +1 question
                updateScreen(new int[]{totalX, totalY + 1});
            } else {
                showMessageDialog(null, "Can't add more than " + maxTotalY + " questions");
            }
        }

        // ITEM DELETE STUDENT
        if (e.getSource() == itemDeleteStudent) {
            // If the number of students is greater than 0
            // Else show a dialog explaining why
            if ((totalX - 2) > 0) {
                // Update the screen with -1 student
                updateScreen(new int[]{totalX - 1, totalY});
            } else {
                showMessageDialog(null, "There is no more students left to delete");
            }
        }

        // ITEM DELETE QUESTION
        if (e.getSource() == itemDeleteQuestion) {
            // If the number of questions is greater than 0
            // Else show a dialog explaining why
            if ((totalY - 1) > 0) {
                // Update the screen with -1 question
                updateScreen(new int[]{totalX, totalY - 1});
            } else {
                showMessageDialog(null, "There is no more questions left to delete");
            }
        }

        // ITEM SAVE TABLE
        if (e.getSource() == itemSaveTable) {
            // Create a new instance of FileManagement
            // Save the table to a CSV File
            FileManagement filemng = new FileManagement(txtFields, new int[]{totalX, totalY});
            filemng.saveTable();
        }

        // ITEM SAVE RAF
        if (e.getSource() == itemSaveRAF) {
            // Show a input dialog asking for the name of the file
            // Put the users input into a variable
            String userInput = showInputDialog(null, "Name of the file (I.E. \"myfile.txt\")", "Random Access File", JOptionPane.INFORMATION_MESSAGE);
            // Create a new instance of FileManagement
            FileManagement filemng = new FileManagement(txtFields, new int[]{totalX, totalY});
            // If the user has inputted something
            if (userInput != null) {
                // Save the table to a RAF File
                filemng.saveRAF(userInput);
            }
        }

        // ITEM CLEAR ALL
        if (e.getSource() == itemClearAll) {
            // Loop the text fields on the X Axis (Starting from 1)
            // Loop the text fields on the Y Axis
            for (int x = 1; x < totalX; x++) {
                for (int y = 0; y < totalY; y++) {
                    // If X is at start position clear Y at +1
                    // Else clear everything on Y Axis
                    // Note: x == 1 is for making sure we don't clear
                    //       The word answer on the second line
                    if (x == 1) {
                        txtFields[x][y + 1].setText("");
                    } else {
                        txtFields[x][y].setText("");
                    }
                }
            }
            // Create a new instance of FileManagement
            // Calculate the results
            // Note: Since we cleared everything we need to re-check the results
            FileManagement filemng = new FileManagement(txtFields, new int[]{totalX, totalY});
            filemng.calculateResults();
        }

        // ITEM CLEAR STUDENTS
        if (e.getSource() == itemClearStudents) {
            // Loop the text fields on the X Axis (Starting from 2)
            // Loop the text fields on the Y Axis
            for (int x = 2; x < totalX; x++) {
                for (int y = 0; y < totalY; y++) {
                    // Clear everything on the Y Axis
                    txtFields[x][y].setText("");
                }
            }
            // Create a new instance of FileManagement
            // Calculate the results
            // Note: Since we cleared students we need to re-check the results
            FileManagement filemng = new FileManagement(txtFields, new int[]{totalX, totalY});
            filemng.calculateResults();
        }

        // ITEM CLEAR ANSWERS
        if (e.getSource() == itemClearQuestions) {
            // Loop the text fields on the Y Axis
            for (int y = 0; y < totalY; y++) {
                // Clear Y at +1
                // Note: y + 1 is for making sure we don't clear
                //       The word answer on the second line
                txtFields[1][y + 1].setText("");
            }
            // Create a new instance of FileManagement
            // Calculate the results
            // Note: Since we cleared answers we need to re-check the results
            FileManagement filemng = new FileManagement(txtFields, new int[]{totalX, totalY});
            filemng.calculateResults();
        }

        // ITEM RESTORE ALL
        if (e.getSource() == itemRestoreAll) {
            // Create a new instance of FileManagement
            // Read & Display the file
            // Check the results
            FileManagement filemng = new FileManagement(txtFields, new int[]{totalX, totalY});
            filemng.readFile();
            filemng.calculateResults();
        }
    }

    /**
     * Key Events
     *
     * @param e the source of the event
     */
    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        // Create a new instance of FileManagement
        // Calculate the results
        FileManagement filemng = new FileManagement(txtFields, new int[]{totalX, totalY});
        filemng.calculateResults();
    }

    /**
     * Window Events
     *
     * @param we the source of the event
     */
    public void windowClosing(WindowEvent we) {
        System.exit(0);
    }

    public void windowIconified(WindowEvent we) {
    }

    public void windowOpened(WindowEvent we) {
    }

    public void windowClosed(WindowEvent we) {
    }

    public void windowDeiconified(WindowEvent we) {
    }

    public void windowActivated(WindowEvent we) {
    }

    public void windowDeactivated(WindowEvent we) {
    }

    /**
     * Used to update the screen when a new student of questions is
     * added/removed
     *
     * @param txtFieldAxis The new X & Y Axis
     */
    private void updateScreen(int[] txtFieldAxis) {
        // Setting variables
        // Making a variable for the new X & Y Axis
        int newTotalX = txtFieldAxis[0];
        int newTotalY = txtFieldAxis[1];

        // Updating the frame
        // Calculating the new size of the frame
        frame.setSize(newTotalY * 75 + 120, newTotalX * 25 + 148);
        // Updating the buttons
        // Calculating the new position of buttons
        int btnXValue = (newTotalX + 1) * 25 + 30;
        int btnYValue = newTotalY * 75 + 10;
        // Set the new location constaint for the buttons
        setConstraint(btnSort, new int[]{btnYValue, btnXValue});
        setConstraint(btnFind, new int[]{20, btnXValue});
        setConstraint(btnBinSearch, new int[]{230, btnXValue});
        setConstraint(btnClearFind, new int[]{20, btnXValue});

        // Updating text fields
        // Calculating the new position of the text fields
        int txtXValue = (newTotalX + 1) * 25 + 31;
        int txtYValue = newTotalY * 75 + 10;
        // Set the new location constaint for the text fields
        setConstraint(txtFind, new int[]{105, txtXValue});
        setConstraint(txtBinary, new int[]{349, txtXValue});

        // Updating the multi dimensional text fields
        // If there is a new question
        if (totalY < newTotalY) {
            // Loop the text fields on the Y Axis (Starting at totalY) (Ending at newTotalY)
            // Loop the text fields on the X Axis (Ending at newTotalX)
            for (int y = totalY; y <= newTotalY; y++) {
                for (int x = 0; x <= newTotalX; x++) {
                    // If Y is at start position
                    // Set the last Y Column to nothing
                    // Else
                    // Add a new text field from LocateTextField
                    if (y == totalY) {
                        txtFields[x][y].setEditable(true);
                        txtFields[x][y].setBackground(Color.WHITE);
                        txtFields[x][y].setText("");
                        txtFields[x][y].setFont(txtFields[x][y].getFont().deriveFont(~Font.BOLD, 12f));
                    } else {
                        int xPos = x * 25 + 20;
                        int yPos = y * 75 + 20;
                        txtFields[x][y] = LibraryComponents.LocateTextField(frame, layout, "", new int[]{70, 20}, new int[]{xPos, yPos});
                        txtFields[x][y].addKeyListener(this);
                    }
                }
            }
        }
        // If a question has been deleted
        if (totalY > newTotalY) {
            // Loop the text fields on the Y Axis (Starting at newTotalY + 1) (Ending at totalY)
            // Loop the text fields on the X Axis (Ending at newTotalX)
            for (int y = newTotalY + 1; y <= totalY; y++) {
                for (int x = 0; x <= newTotalX; x++) {
                    // Clears the answer the is under the word "Result"
                    // Note: This is because when we remove a column
                    //       the answer that resides on the "new last" column
                    //       stays where it was previously
                    txtFields[1][y - 1].setText("");
                    // Remove the text field from the frame
                    // Remove the listener from the textfield
                    // Set the textfield to null
                    frame.remove(txtFields[x][y]);
                    txtFields[x][y].removeKeyListener(this);
                    txtFields[x][y] = null;
                }
            }
        }
        // If there is a new student
        if (totalX < newTotalX) {
            // Loop the text fields on the X Axis (Starting at totalX) (Ending at newTotalX)
            // Loop the text fields on the Y Axis (Ending at newTotalY)
            for (int x = totalX; x <= newTotalX; x++) {
                for (int y = 0; y <= newTotalY; y++) {
                    // If X is at start position
                    // Set the last X Column to nothing
                    // Else
                    // Add a new text field from LocateTextField
                    if (x == totalX) {
                        txtFields[x][y].setEditable(true);
                        txtFields[x][y].setBackground(Color.WHITE);
                        txtFields[x][y].setText("");
                        txtFields[x][y].setFont(txtFields[x][y].getFont().deriveFont(~Font.BOLD, 12f));
                    } else {
                        int xPos = x * 25 + 20;
                        int yPos = y * 75 + 20;
                        txtFields[x][y] = LibraryComponents.LocateTextField(frame, layout, "", new int[]{70, 20}, new int[]{xPos, yPos});
                        txtFields[x][y].addKeyListener(this);
                    }
                }
            }
        }
        // If a student has been deleted
        if (totalX > newTotalX) {
            // Loop the text fields on the X Axis (Starting at newTotalX + 1) (Ending at totalX)
            // Loop the text fields on the Y Axis (Ending at newTotalY)
            for (int x = newTotalX + 1; x <= totalX; x++) {
                for (int y = 0; y <= newTotalY; y++) {
                    // Remove the text field from the frame
                    // Remove the listener from the textfield
                    // Set the textfield to null
                    frame.remove(txtFields[x][y]);
                    txtFields[x][y].removeKeyListener(this);
                    txtFields[x][y] = null;
                }
            }
        }

        // Call the setCurrentEntries and setFields from the main instance
        // in BillsReportingSystem
        billsObj.setCurrentEntries(newTotalX - 2, newTotalY - 1);
        billsObj.setFields();

        // Set totalX to the new totalX
        // Set totalY to the new totalY
        totalX = newTotalX;
        totalY = newTotalY;

        // Create a new instance of FileManagenment
        // Calculate the results
        // Note: We are calculating the results again since we has removed and
        //       Re-added the results/average from the frame
        FileManagement filemng = new FileManagement(txtFields, new int[]{totalX, totalY});
        filemng.calculateResults();
    }

    /**
     * Sets a constraint on a JButton
     *
     * @param button the button
     * @param axis the X & Y Axis
     */
    private void setConstraint(JButton button, int[] axis) {
        layout.putConstraint(SpringLayout.WEST, button, axis[0], SpringLayout.WEST, frame);
        layout.putConstraint(SpringLayout.NORTH, button, axis[1], SpringLayout.NORTH, frame);
    }

    /**
     * Sets a constraint on a JTextField
     *
     * @param field the textfield
     * @param axis the X & Y Axis
     */
    private void setConstraint(JTextField field, int[] axis) {
        layout.putConstraint(SpringLayout.WEST, field, axis[0], SpringLayout.WEST, frame);
        layout.putConstraint(SpringLayout.NORTH, field, axis[1], SpringLayout.NORTH, frame);

    }

    /**
     * Allows the comparison of 2D Arrays (Multi-dimensional)
     *
     * @author Harrison Howard
     * @param <T> Java Generic Type
     *
     * Reference:
     * https://stackoverflow.com/questions/33918532/sort-a-two-dimensional-string-array-with-arrays-sort-based-on-a-chosen-column?rq=1
     */
    class String2DComparator<T extends Comparable<T>> implements Comparator<T[]> {

        // Create a new variable for the column
        // This column is final and can not be changed (once set)
        private final int column;

        /**
         * Get the column from the creation of the instance
         *
         * @param column the column provided
         */
        String2DComparator(int column) {
            // Set the class column to the column number provided
            this.column = column;
        }

        /**
         * Compare 2 objects (T is a generic type)
         *
         * @param obj1 the first row
         * @param obj2 the second row
         * @return the comparison
         */
        @Override
        public int compare(T[] obj1, T[] obj2) {
            return obj1[column].compareTo(obj2[column]);
        }
    }
}
