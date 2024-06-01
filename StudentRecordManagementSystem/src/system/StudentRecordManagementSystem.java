package system;
import java.io.BufferedReader;
import system.Student;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
public class StudentRecordManagementSystem  {
	private static final String FILE_NAME = "student.txt";
    private static HashMap<Integer,Student> studentRecords = new HashMap<>();

    public static void main(String[] args) { 
        loadStudentRecords();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome To The Student Record Management System, press: \n1. To add a new student record\n2. To view existing records\n3. To update a student's record\n4. To delete a student's record\n5. To exit\nPlease enter your choice");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1: addStudentRecord(sc);break;
                case 2: viewStudentRecords();break;
                case 3:updateStudentRecord(sc); break;
                case 4: deleteStudentRecord(sc); break;
                case 5: saveStudentRecords();System.exit(0);break;
                default:System.out.println("Invalid choice. Please try again.");
                }}}
    private static void loadStudentRecords() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine())!= null) {
                String[] fields = line.split(",");
                Student student = new Student(fields[0], Integer.parseInt(fields[1]), Double.parseDouble(fields[2]));
                studentRecords.put(student.getRoll_NUM(), student);
            }
        } catch (IOException e) {
            System.out.println("Error loading student records: ") ;
        }
    }

    private static void saveStudentRecords() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Student student : studentRecords.values()) {
                writer.write(student.getName() + "," + student.getRoll_NUM() + "," + student.getGrade());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving student records: ");
        }
    }
    private static void addStudentRecord(Scanner scanner) {
        System.out.print("Enter student name: ");
        String name = scanner.next();
        System.out.print("Enter roll number: ");
        int rollNumber = Integer.parseInt(scanner.next());
        System.out.print("Enter grade: ");
        double grade = Double.parseDouble(scanner.next());
        if (findStudentByRollNumber(rollNumber) != null) {
            System.out.println("Roll number already exists. Please enter a unique roll number.");
            return;
        }
        Student student = new Student(name, rollNumber, grade);
        studentRecords.put(rollNumber, student);
        System.out.println("Student record added successfully!");
    }

    private static void viewStudentRecords() {
        System.out.println("Student Records:");
        for (Student student : studentRecords.values()) {
            System.out.println(student.getName() + " - " + student.getRoll_NUM() + " - " + student.getGrade());
        }
    }

    private static void updateStudentRecord(Scanner sc) {
        System.out.print("Enter roll number of student to update: ");
        int rollNumber = Integer.parseInt(sc.next());
        Student studentToUpdate = findStudentByRollNumber(rollNumber);
        if (studentToUpdate!= null) {
            System.out.print("Enter new name: ");
            String name = sc.next();
            System.out.print("Enter new grade: ");
            int grade = Integer.parseInt(sc.next());
            studentToUpdate.setName(name);
            studentToUpdate.setGrade(grade);
            System.out.println("Student record updated successfully!");
        } else {
            System.out.println("Student not found!");
        }
      
    }

    private static void deleteStudentRecord(Scanner sc) {
        System.out.print("Enter roll number of student to delete: ");
        int roll = Integer.parseInt(sc.next());
        Student rem = findStudentByRollNumber(roll);
        if (rem!= null) {
            studentRecords.remove(rem.getRoll_NUM());
            System.out.println("Student record deleted successfully!");
        } else {
            System.out.println("Student not found!");
        }
    }

    private static Student findStudentByRollNumber(int rollNumber) {
        for (Student student : studentRecords.values()) {
            if (student.getRoll_NUM()==rollNumber) {
                return student;
            }
        }
        return null;
    }
}


