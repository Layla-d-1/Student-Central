COMP1007 Student central. 

This is a Java program built to provide a menu to handle student data. 

Files in This Folder

-Main.java: The file with the main method. It runs the menu and handles all the input checking (validation).
-StudentRecordsManager.java: This is the brain. It manages the student array, handles loading/saving the file, and runs all the filtering and analysis tools.
-Student.java: The main object class. Holds IDs, names, and the Details object.
-Details.java: The nested object class. Holds all the academic info (CWA, Course, Credits, etc.).
-student_data.csv: The simple text file where all data is saved permanently.

How to Run:
Use your command line or terminal.
1.Compile the code 
2.Execute the program: use 'java main'

feautures:

-Add new students and edit existing ones.
-view all records.
-Filter by course name or enrollment status (FT/PT).
-Find the students with the highest CWA.
-Calculate the average CWA for every course.
-Check which students are eligible for graduation (400+ credits).

The program automatically loads the data when it starts and saves any changes when you select Exit (9).
