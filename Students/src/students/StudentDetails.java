
package students;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class StudentDetails extends JFrame implements ActionListener {

    private Choice crollno;
    private JTable table;
    private JButton search, update, cancel, addStudent;
    private DefaultTableModel tableModel;

    public StudentDetails() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel heading = new JLabel("Search by Student ID");
        heading.setBounds(20, 20, 150, 20);
        add(heading);

        crollno = new Choice();
        crollno.setBounds(180, 20, 150, 20);
        add(crollno);

        // Create the table with a blank table model (no data initially)
        tableModel = new DefaultTableModel(
            new Object [][] {}, // Initially empty data
            new String [] {"Student ID", "First Name", "Last Name", "Gender", "Course", "Year Level"}
        );
        table = new JTable(tableModel);

        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 100, 900, 600);
        add(jsp);

        search = new JButton("Search");
        search.setBounds(400, 20, 100, 30);
        search.setBackground(Color.BLACK);
        search.setForeground(Color.WHITE);
        search.addActionListener(this);
        add(search);

        update = new JButton("Update");
        update.setBounds(100, 700, 100, 30);
        update.setBackground(Color.BLACK);
        update.setForeground(Color.WHITE);
        update.addActionListener(this);
        add(update);

        cancel = new JButton("Cancel");
        cancel.setBounds(600, 700, 100, 30);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.addActionListener(this);
        add(cancel);

        addStudent = new JButton("Add Student");
        addStudent.setBounds(250, 700, 150, 30);
        addStudent.setBackground(Color.BLACK);
        addStudent.setForeground(Color.WHITE);
        addStudent.addActionListener(this);
        add(addStudent);

        // Add default students to the table
        addDefaultStudents();

        setSize(900, 800);
        setLocation(350, 50);
        setVisible(true);
    }

    private void addDefaultStudents() {
        // Add your details
        addStudentToTable("23-05368", "Carl Vener", "Wee", "Male", "Informatiion Tefhnology", "Sophomore");
        
        // Add your friend's details
        addStudentToTable("23-05367", "Tyrone Josh", "Tang-o", "Male", "Information Technology", "Sophomore");
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            // Dummy search action
            String selectedStudentId = crollno.getSelectedItem();
            JOptionPane.showMessageDialog(null, "Searching for Student ID: " + selectedStudentId);
        } else if (ae.getSource() == update) {
            // Show Update Student dialog
            String selectedStudentId = crollno.getSelectedItem();
            new UpdateStudentDialog(this, selectedStudentId); // Passing the selected student ID to the dialog
        } else if (ae.getSource() == cancel) {
            setVisible(false);
            new Project(); // Go back to the main page
        } else if (ae.getSource() == addStudent) {
            // Show the Add Student dialog
            new AddStudentDialog(this);
        }
    }

    // Method to add a new student to the table
    public void addStudentToTable(String studentId, String firstName, String lastName, String gender, String course, String yearLevel) {
        tableModel.addRow(new Object[]{studentId, firstName, lastName, gender, course, yearLevel});
    }

    // Method to update a student's details in the table
    public void updateStudentInTable(String studentId, String firstName, String lastName, String gender, String course, String yearLevel) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).equals(studentId)) {
                tableModel.setValueAt(firstName, i, 1);
                tableModel.setValueAt(lastName, i, 2);
                tableModel.setValueAt(gender, i, 3);
                tableModel.setValueAt(course, i, 4);
                tableModel.setValueAt(yearLevel, i, 5);
                break;
            }
        }
    }

    public static void main(String[] args) {
        new StudentDetails();
    }

    private static class Project {

        public Project() {
        }
    }
}

// Dialog for adding a new student
class AddStudentDialog extends JDialog {

    private JTextField tfStudentId, tfFirstName, tfLastName, tfEmail, tfPhoneNumber, tfAddress;
    private JComboBox<String> cbGender, cbCourse, cbYearLevel;
    private JButton addButton, cancelButton;

    public AddStudentDialog(JFrame parent) {
        super(parent, "Add Student", true);
        setLayout(new GridLayout(8, 2, 10, 10));

        // Initialize fields
        tfStudentId = new JTextField();
        tfFirstName = new JTextField();
        tfLastName = new JTextField();
        tfEmail = new JTextField();
        tfPhoneNumber = new JTextField();
        tfAddress = new JTextField();

        cbGender = new JComboBox<>(new String[]{"Male", "Female"});
        cbCourse = new JComboBox<>(new String[]{"Information Technology", "Computer Science", "Entertainment & Multimedia Computing", "Nursing", "Psychology"});
        cbYearLevel = new JComboBox<>(new String[]{"Freshman", "Sophomore", "Junior", "Senior"});

        add(new JLabel("Student ID:"));
        add(tfStudentId);

        add(new JLabel("First Name:"));
        add(tfFirstName);

        add(new JLabel("Last Name:"));
        add(tfLastName);

        add(new JLabel("Gender:"));
        add(cbGender);

        add(new JLabel("Course:"));
        add(cbCourse);

        add(new JLabel("Year Level:"));
        add(cbYearLevel);

        addButton = new JButton("Add");
        addButton.addActionListener(e -> addStudent());
        add(addButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);

        setSize(300, 350);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void addStudent() {
        String studentId = tfStudentId.getText();
        String firstName = tfFirstName.getText();
        String lastName = tfLastName.getText();
        String gender = (String) cbGender.getSelectedItem();
        String course = (String) cbCourse.getSelectedItem();
        String yearLevel = (String) cbYearLevel.getSelectedItem();

        // Add student data to the table in StudentDetails frame
        ((StudentDetails) getParent()).addStudentToTable(studentId, firstName, lastName, gender, course, yearLevel);

        // Close the dialog
        dispose();
    }
}

// Dialog for updating a student's details
class UpdateStudentDialog extends JDialog {

    private JTextField tfFirstName, tfLastName, tfCourse, tfYearLevel;
    private JComboBox<String> cbGender;
    private JButton updateButton, cancelButton;
    private String studentId;

    public UpdateStudentDialog(JFrame parent, String studentId) {
        super(parent, "Update Student", true);
        this.studentId = studentId;
        setLayout(new GridLayout(6, 2, 10, 10));

        // Initialize fields
        tfFirstName = new JTextField();
        tfLastName = new JTextField();
        tfCourse = new JTextField();
        tfYearLevel = new JTextField();
        cbGender = new JComboBox<>(new String[]{"Male", "Female"});

        add(new JLabel("First Name:"));
        add(tfFirstName);

        add(new JLabel("Last Name:"));
        add(tfLastName);

        add(new JLabel("Gender:"));
        add(cbGender);

        add(new JLabel("Course:"));
        add(tfCourse);

        add(new JLabel("Year Level:"));
        add(tfYearLevel);

        updateButton = new JButton("Update");
        updateButton.addActionListener(e -> updateStudent());
        add(updateButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);

        setSize(300, 300);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void updateStudent() {
        String firstName = tfFirstName.getText();
        String lastName = tfLastName.getText();
        String gender = (String) cbGender.getSelectedItem();
        String course = tfCourse.getText();
        String yearLevel = tfYearLevel.getText();

        // Update student details in the table
        ((StudentDetails) getParent()).updateStudentInTable(studentId, firstName, lastName, gender, course, yearLevel);

        // Close the dialog
        dispose();
    }
}