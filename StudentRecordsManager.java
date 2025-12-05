/* Layla  Dawood - 23100624
* studentrecordsmanager.java 
 Manages the array of student objects, file i/o and analysis.*/





import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class StudentRecordsManager {
    // Array to hold student records
    private Student[] studentArray; 
    private int recordCount;
    private final int MAX_RECORDS = 100; // Define a fixed array size

    public StudentRecordsManager() {
        studentArray = new Student[MAX_RECORDS];
        recordCount = 0;
        loadDataFromFile("student_data.csv"); // Load data at program start 
    }
    

    // 1. File Handling 
    

   
     // Loads student records from a CSV file.
   
    private void loadDataFromFile(String fileName) {
        System.out.println("\n--- Loading Data from " + fileName + " ---");
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine(); // Skip header row 
            if (line == null) return; // File is empty
            
            while ((line = br.readLine()) != null && recordCount < MAX_RECORDS) {
                // Manually split the CSV line (no ArrayLists)
                String[] fields = line.split(","); 
                
                // Basic check for correct number of fields
                if (fields.length != 8) {
                    System.out.println("Error: Skipping invalid line (incorrect field count): " + line);
                    continue;
                }
                
                try {
                    // Data parsing
                    String studentID = fields[0];
                    String firstName = fields[1];
                    String familyName = fields[2];
                    String courseEnrolled = fields[3];
                    int yearLevel = Integer.parseInt(fields[4].trim());
                    double cwa = Double.parseDouble(fields[5].trim());
                    String status = fields[6];
                    int creditsEarned = Integer.parseInt(fields[7].trim());
                    
                    Details details = new Details(courseEnrolled, yearLevel, cwa, status, creditsEarned);
                    studentArray[recordCount] = new Student(studentID, firstName, familyName, details);
                    recordCount++;
                } catch (NumberFormatException e) {
                    System.out.println("Error: Skipping line due to invalid number format: " + line);
                }
            }
            System.out.println(recordCount + " student records loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error: Could not read file " + fileName + ". Starting with an empty system.");
        }
    }

    
     // Saves all current student records back to the CSV file
  
    public void saveDataToFile(String fileName) {
        System.out.println("\n--- Saving Data to " + fileName + " ---");
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            // Write CSV header 
            pw.println("StudentID,FirstName,FamilyName,CourseEnrolled,YearLevel,CWA,Status,CreditsEarned");
            
            // Manually iterate over the array
            for (int i = 0; i < recordCount; i++) {
                pw.println(studentArray[i].getCSVRecord());
            }
            System.out.println(recordCount + " records saved.");
        } catch (IOException e) {
            System.out.println("Error: Could not write data to file " + fileName + ".");
        }
    }

 
    // 2. Core Functionalities

  
     //Helper method to find a student's index by ID.
    
    public int findStudentIndex(String studentID) {
        for (int i = 0; i < recordCount; i++) {
            if (studentArray[i].getStudentID().equalsIgnoreCase(studentID)) {
                return i;
            }
        }
        return -1;
    }
    
   
      //Adds a new student to the array
     
    public void addNewStudent(Student newStudent) {
        if (recordCount >= MAX_RECORDS) {
            System.out.println("System capacity reached. Cannot add new student.");
            return;
        }
        
        if (findStudentIndex(newStudent.getStudentID()) != -1) {
             System.out.println("Error: Student ID already exists.");
            return;
        }
        
        studentArray[recordCount] = newStudent;
        recordCount++;
        System.out.println("Student " + newStudent.getFirstName() + " added successfully.");
    }

  
     // Allows editing the non-ID fields of an existing student
    
    public Student getStudent(String studentID) {
        int index = findStudentIndex(studentID);
        if (index != -1) {
            return studentArray[index];
        }
        return null;
    }

   
     // Lists all students and their details in a neat format
     
    public void viewAllStudents() {
        if (recordCount == 0) {
            System.out.println("The system currently holds no student records.");
            return;
        }
        
        System.out.println("\n==============================================");
        System.out.println("      VIEWING ALL " + recordCount + " STUDENT RECORDS");
        System.out.println("================================================");
        for (int i = 0; i < recordCount; i++) {
            System.out.println((i + 1) + ". " + studentArray[i].getDisplayRecord());
            System.out.println("----------------------------------------------");
        }
    }


    // 3. Filtering Functionalities


     // Filters and displays students enrolled in a specific course
    
    public void filterByCourse(String course) {
        System.out.println("\n--- Students Filtered by Course: " + course + " ---");
        int foundCount = 0;
        for (int i = 0; i < recordCount; i++) {
            if (studentArray[i].getDetails().getCourseEnrolled().equalsIgnoreCase(course)) {
                System.out.println(studentArray[i].getDisplayRecord());
                System.out.println("----------------------------------------------");
                foundCount++;
            }
        }
        if (foundCount == 0) {
            System.out.println("No students found in the course: " + course);
        }
    }

   
     // Filters and displays students based on their status (FT/PT)
   
    public void filterByStatus(String status) {
        System.out.println("\n--- Students Filtered by Status: " + status + " ---");
        int foundCount = 0;
        for (int i = 0; i < recordCount; i++) {
            if (studentArray[i].getDetails().getStatus().equalsIgnoreCase(status)) {
                System.out.println(studentArray[i].getDisplayRecord());
                System.out.println("----------------------------------------------");
                foundCount++;
            }
        }
        if (foundCount == 0) {
            System.out.println("No students found with status: " + status);
        }
    }

  
    // 4. Analysis Functionalities
   

    //Finds and displays the students with the highest CWA[
   
    public void highestCWAAnalysis() {
        if (recordCount == 0) {
            System.out.println("No records available for CWA analysis.");
            return;
        }
        
        double highestCWA = -1.0;
        
        // 1. Find the highest CWA value
        for (int i = 0; i < recordCount; i++) {
            if (studentArray[i].getDetails().getCWA() > highestCWA) {
                highestCWA = studentArray[i].getDetails().getCWA();
            }
        }
        
        System.out.printf("\n--- Highest CWA Analysis (CWA: %.2f) ---\n", highestCWA);
        
        // 2. Display all students with that CWA
        for (int i = 0; i < recordCount; i++) {
            if (studentArray[i].getDetails().getCWA() == highestCWA) {
                System.out.println(studentArray[i].getDisplayRecord());
                System.out.println("----------------------------------------------");
            }
        }
    }

    //Calculate and display the average CWA for each course
     
    public void averageCWAAnalysis() {
        if (recordCount == 0) {
            System.out.println("No records available for CWA analysis.");
            return;
        }
        
        // implementation of a map using parallel arrays
        final int MAX_COURSES = 50;
        String[] courseNames = new String[MAX_COURSES];
        double[] totalCWA = new double[MAX_COURSES];
        int[] courseCounts = new int[MAX_COURSES];
        int uniqueCourseCount = 0;

        // 1. Accumulate totals
        for (int i = 0; i < recordCount; i++) {
            String currentCourse = studentArray[i].getDetails().getCourseEnrolled();
            double currentCWA = studentArray[i].getDetails().getCWA();
            int courseIndex = -1;

            // Find course in our parallel array
            for (int j = 0; j < uniqueCourseCount; j++) {
                if (courseNames[j].equalsIgnoreCase(currentCourse)) {
                    courseIndex = j;
                    break;
                }
            }

            if (courseIndex == -1) {
                // New course found
                if (uniqueCourseCount < MAX_COURSES) {
                    courseNames[uniqueCourseCount] = currentCourse;
                    courseIndex = uniqueCourseCount;
                    uniqueCourseCount++;
                } else {
                    System.out.println("Warning: Maximum unique courses reached. Skipping " + currentCourse);
                    continue;
                }
            }

            // Update totals
            totalCWA[courseIndex] += currentCWA;
            courseCounts[courseIndex]++;
        }

        System.out.println("\n--- Average CWA for Each Course ---");
        
        // 2. Calculate and display averages
        for (int i = 0; i < uniqueCourseCount; i++) {
            double average = totalCWA[i] / courseCounts[i];
            System.out.printf("Course: %s | Average CWA: %.2f (from %d students)\n", 
                              courseNames[i], average, courseCounts[i]);
        }
    }

     // Identifies and displays students eligible for graduation (400+ credits)
     
    public void creditAnalysis() {
        final int GRADUATION_CREDITS = 400;
        System.out.println("\n--- Graduation Eligibility Analysis (Credits >= " + GRADUATION_CREDITS + ") ---");
        int eligibleCount = 0;
        
        for (int i = 0; i < recordCount; i++) {
            if (studentArray[i].getDetails().getCreditsEarned() >= GRADUATION_CREDITS) {
                System.out.println(studentArray[i].getDisplayRecord());
                System.out.println("----------------------------------------------");
                eligibleCount++;
            }
        }
        
        if (eligibleCount == 0) {
            System.out.println("No students are currently eligible for graduation.");
        }
    }
}
