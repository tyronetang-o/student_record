package students;

import javax.swing.SwingUtilities;


public class Students{

    // Main method to launch the application
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(()-> new StudentDetails());
        
    }
    
}