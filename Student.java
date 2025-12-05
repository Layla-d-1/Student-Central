/**
 * Layla Dawood -23100624
 * student class 
 * Contains the personal details and an object of the Details class
 */
 
 
public class Student {
    private String studentID;
    private String firstName;
    private String familyName;
    private Details details; // Contains the academic data 

    // Constructor
    public Student(String studentID, String firstName, String familyName, Details details) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.familyName = familyName;
        this.details = details;
    }

    //  Getters 
    public String getStudentID() { return studentID; }
    public String getFirstName() { return firstName; }
    public String getFamilyName() { return familyName; }
    public Details getDetails() { return details; }

    // Setters 
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setFamilyName(String familyName) { this.familyName = familyName; }
    public void setDetails(Details details) { this.details = details; }
    
    // Helper method to format the student record for viewing.

    public String getDisplayRecord() {
        return String.format(
            "ID: %s | Name: %s %s\n%s", 
            studentID, firstName, familyName, details.getDisplayDetails()
        );
    }
    
    // Helper method to format the student record for CSV output.

    public String getCSVRecord() {
        return String.format(
            "%s,%s,%s,%s", 
            studentID, firstName, familyName, details.getCSVData()
        );
    }
}
