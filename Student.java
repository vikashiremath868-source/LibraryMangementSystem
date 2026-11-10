import java.io.Serializable;

public class Student implements Serializable {

    int studentId;
    String studentName;
    String department;

    Student(int StudentId, String studentName,
            String department) {

        this.studentId = StudentId;
        this.studentName = studentName;
        this.department = department;
    }

    void displayStudent() {

        System.out.println("Student ID: " + studentId);
        System.out.println("Student Name: " + studentName);
        System.out.println("Department: " + department);
        System.out.println("----------------------");
    }
}
