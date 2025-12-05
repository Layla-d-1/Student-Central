/* COMP1007- PDI Assignment  *
 * Layla Dawood - 23100624   *
 * - class Details */


 // contains academic and enrolment data for a student.
 // field within student class. 
 
public class Details {
    private String courseEnrolled;
    private int yearLevel; // Between 1 and 4 
    private double CWA; // Course Weighted Average, between 0 and 100 
    private String status; //  FT (Full Time) / PT (Part Time) 
    private int creditsEarned; // Up to 400

    //  Add Constructor
    public Details(String courseEnrolled, int yearLevel, double CWA, String status, int creditsEarned) {
        this.courseEnrolled = courseEnrolled;
        this.yearLevel = yearLevel;
        this.CWA = CWA;
        this.status = status;
        this.creditsEarned = creditsEarned;
    }

    // Add Getters
    public String getCourseEnrolled() { return courseEnrolled; }
    public int getYearLevel() { return yearLevel; }
    public double getCWA() { return CWA; }
    public String getStatus() { return status; }
    public int getCreditsEarned() { return creditsEarned; }

    //Add Setters 
    public void setCourseEnrolled(String courseEnrolled) { this.courseEnrolled = courseEnrolled; }
    public void setYearLevel(int yearLevel) { this.yearLevel = yearLevel; }
    public void setCWA(double CWA) { this.CWA = CWA; }
    public void setStatus(String status) { this.status = status; }
    public void setCreditsEarned(int creditsEarned) { this.creditsEarned = creditsEarned; }
    
    
     // Helper method to format details for display (View all students)
     
    public String getDisplayDetails() {
        return String.format(
            "   Course: %s | Year: %d | CWA: %.2f | Status: %s | Credits: %d",
            courseEnrolled, yearLevel, CWA, status, creditsEarned
        );
    }
    
    //Helper method to format details for file output.
    
    public String getCSVData() {
        return String.format(
            "%s,%d,%.2f,%s,%d",
            courseEnrolled, yearLevel, CWA, status, creditsEarned
        );
    }
}


    









 


