/* Layla Dawood - 23100624 
 * Provides user interface and handles user input.*/


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


public class Main {
    private static StudentRecordsManager manager = new StudentRecordsManager();
    // Use BufferedReader for uniformity and to avoid Scanner issues if it were used for file I/O
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        int option = 0;

        do {
            displayMenu();
            try {
                String input = reader.readLine().trim();
                option = Integer.parseInt(input);
                
                switch (option) {
                    case 1: addNewStudent(); break; // Add new student 
                    case 2: editExistingStudent(); break; // Edit existing student 
                    case 3: manager.viewAllStudents(); break; // View all students 
                    case 4: filterByCourse(); break; // Filter by course
                    case 5: filterByStatus(); break; // Filter by status 
                    case 6: manager.highestCWAAnalysis(); break; // Highest CWA 
                    case 7: manager.averageCWAAnalysis(); break; // Average CWA for each course 
                    case 8: manager.creditAnalysis(); break; // Credit Analysis / Graduation 
                    case 9: System.out.println("Exiting system..."); break; // Exit 
                    default: System.out.println("Invalid option. Please choose a number between 1 and 9.");
                }
                
                // Pause for user to review output before menu refresh
                if (option != 9) {
                    System.out.print("\nPress Enter to continue...");
                    reader.readLine();
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid menu number (1-9).");
                option = 0; // Keep the loop going
            } catch (IOException e) {
                System.out.println("An unexpected input error occurred.");
                option = 0;
            }
        } while (option != 9);
        
        // Save changes on exit 
        manager.saveDataToFile("student_data.csv");
    }
    
   
    // Display the menu of options.
   
    private static void displayMenu() {
        System.out.println("\n=================================");
        System.out.println( "Welcome to Student Central ");
        System.out.println("===================================");
        System.out.println("1> Add new student.");
        System.out.println("2> Edit student.");
        System.out.println("3> View all students.");
        System.out.println("4> Filter by course.");
        System.out.println("5> Filter by status.");
        System.out.println("6> Highest CWA.");
        System.out.println("7> Average CWA for each course.");
        System.out.println("8> Credit Analysis.");
        System.out.println("9> Exit.");
        System.out.print("Enter your option (1-9): ");
    }
    
  
    // 1. Add/Edit Helpers (Input and Validation) 
    
    private static String readValidatedString(String prompt) throws IOException {
        String input;
        do {
            System.out.print(prompt);
            input = reader.readLine().trim();
            if (input.isEmpty()) {
                System.out.println("Error: Input cannot be empty. Please re-enter.");
            }
        } while (input.isEmpty());
        return input;
    }

   
     // Read and validates CWA input 
  
    private static double readValidatedCWA(String prompt) throws IOException {
        while (true) {
            System.out.print(prompt);
            try {
                double cwa = Double.parseDouble(reader.readLine().trim());
                if (cwa >= 0.0 && cwa <= 100.0) {
                    return cwa;
                } else {
                    System.out.println("Error: CWA must be between 0.0 and 100.0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid number format for CWA.");
            }
        }
    }

    //Reads and validates integer input 
    
    private static int readValidatedInt(String prompt, int min, int max) throws IOException {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(reader.readLine().trim());
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.printf("Error: Value must be between %d and %d.\n", min, max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid number format. Please enter an integer.");
            }
        }
    }

    //Gathers and validates all Details fields, returning a new Details object.
     
    private static Details getValidatedDetailsInput() throws IOException {
        System.out.println("\n--- Enter Academic Details ---");
        String course = readValidatedString("Course Enrolled: ");
        // Year Level (1 to 4) 
        int year = readValidatedInt("Year Level (1-4): ", 1, 4);
        // CWA (0.0 to 100.0)
        double cwa = readValidatedCWA("CWA (0.0-100.0): ");
        String status = readValidatedString("Status (e.g., FT or PT): ");
        // Credits Earned (0 to 400) 
        int credits = readValidatedInt("Credits Earned (0-400): ", 0, 400);
        
        return new Details(course, year, cwa, status, credits);
    }


    // 2. Menu Option Handlers
    
    private static void addNewStudent() {
        try {
            System.out.println("\n--- ADD NEW STUDENT ---");
            String studentID;
            // Validate Student ID is non-empty and unique
            do {
                studentID = readValidatedString("Enter Student ID: ");
                if (manager.findStudentIndex(studentID) != -1) {
                    System.out.println("Error: Student ID already exists. Please enter a unique ID.");
                }
            } while (manager.findStudentIndex(studentID) != -1);
            
            String firstName = readValidatedString("First Name: ");
            String familyName = readValidatedString("Family Name: ");
            
            Details details = getValidatedDetailsInput();
            
            Student newStudent = new Student(studentID, firstName, familyName, details);
            manager.addNewStudent(newStudent);
        } catch (IOException e) {
            System.out.println("An error occurred during input for adding a student.");
        }
    }

    private static void editExistingStudent() {
        try {
            System.out.println("\n--- EDIT EXISTING STUDENT ---");
            String studentID = readValidatedString("Enter Student ID to edit: ");
            
            Student student = manager.getStudent(studentID);
            
            if (student == null) {
                System.out.println("Error: Student with ID " + studentID + " not found.");
                return;
            }
            
            System.out.println("Editing: " + student.getDisplayRecord());
            
            // Edit personal details
            String newFirstName = readValidatedString("Enter new First Name (was: " + student.getFirstName() + "): ");
            String newFamilyName = readValidatedString("Enter new Family Name (was: " + student.getFamilyName() + "): ");
            
            student.setFirstName(newFirstName);
            student.setFamilyName(newFamilyName);
            
            // Edit academic details by setting a new Details object
            System.out.println("\n--- Enter NEW Academic Details ---");
            Details newDetails = getValidatedDetailsInput();
            student.setDetails(newDetails);
            
            System.out.println("Student details updated successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred during input for editing a student.");
        }
    }
    
    private static void filterByCourse() {
        try {
            String course = readValidatedString("Enter the Course to filter by: ");
            manager.filterByCourse(course);
        } catch (IOException e) {
            System.out.println("An error occurred during input.");
        }
    }
    
    private static void filterByStatus() {
        try {
            String status = readValidatedString("Enter the Status to filter by (e.g., FT or PT): ");
            manager.filterByStatus(status);
        } catch (IOException e) {
            System.out.println("An error occurred during input.");
        }
    }
}
