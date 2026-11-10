import java.util.*;
import java.io.*;
import java.time.LocalDate;

class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    int id;
    String title;
    String author;
    boolean isIssued;
    String issuedTo;
    String issueDate;
    String returnDate;

    Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isIssued = false;
        this.issuedTo = "";
        this.issueDate = "";
        this.returnDate = "";
    }

    void displayBook() {
        System.out.println("Book ID: " + id);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Status: " +
                (isIssued ? "Issued" : "Available"));

        if (isIssued) {
            System.out.println("Issued To: " + issuedTo);
            System.out.println("Issue Date: " + issueDate);
        }

        if (!returnDate.isEmpty()) {
            System.out.println("Last Return Date: " + returnDate);
        }

        System.out.println("----------------------");
    }
}

public class LibraryManagementSystem {

    static ArrayList<Book> books = new ArrayList<>();
    static ArrayList<Student> students = new ArrayList<>();

    static Scanner sc = new Scanner(System.in);

    // ADMIN LOGIN
    static boolean adminLogin() {

        int attempts = 3;

        while (attempts > 0) {

            System.out.println("\n===== ADMIN LOGIN =====");

            System.out.print("Enter Username: ");
            String username = sc.nextLine();

            System.out.print("Enter Password: ");
            String password = sc.nextLine();

            if (username.equals("vikas") &&
                    password.equals("vikas@26")) {

                System.out.println("Login Successful!");
                return true;

            } else {
                attempts--;
                System.out.println("Invalid Username or Password!");
                System.out.println("Attempts remaining: " + attempts);
            }
        }

        System.out.println("Access Denied!");
        return false;
    }

    // SAVE BOOKS
    static void saveBooks() {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(
                             new FileOutputStream("books.dat"))) {

            out.writeObject(books);

        } catch (IOException e) {
            System.out.println("Error saving books!");
        }
    }

    // LOAD BOOKS
    @SuppressWarnings("unchecked")
    static void loadBooks() {

        try (ObjectInputStream in =
                     new ObjectInputStream(
                             new FileInputStream("books.dat"))) {

            books = (ArrayList<Book>) in.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("No previous book records.");
        } catch (Exception e) {
            System.out.println("Error loading books!");
        }
    }

    // SAVE STUDENTS
    static void saveStudents() {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(
                             new FileOutputStream("students.dat"))) {

            out.writeObject(students);

        } catch (IOException e) {
            System.out.println("Error saving students!");
        }
    }

    // LOAD STUDENTS
    @SuppressWarnings("unchecked")
    static void loadStudents() {

        try (ObjectInputStream in =
                     new ObjectInputStream(
                             new FileInputStream("students.dat"))) {

            students = (ArrayList<Student>) in.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("No previous student records.");
        } catch (Exception e) {
            System.out.println("Error loading students!");
        }
    }

    // ADD BOOK
    static void addBook() {

        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Book b : books) {
            if (b.id == id) {
                System.out.println("Book ID already exists!");
                return;
            }
        }

        System.out.print("Enter Book Title: ");
        String title = sc.nextLine();

        System.out.print("Enter Author: ");
        String author = sc.nextLine();

        books.add(new Book(id, title, author));

        saveBooks();

        System.out.println("Book Added Successfully!");
    }

    // DISPLAY BOOKS
    static void displayBooks() {

        if (books.isEmpty()) {
            System.out.println("No Books Available!");
            return;
        }

        for (Book b : books) {
            b.displayBook();
        }
    }

    // SEARCH BOOK
    static void searchBook() {

        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();

        for (Book b : books) {
            if (b.id == id) {
                b.displayBook();
                return;
            }
        }

        System.out.println("Book Not Found!");
    }

    // REGISTER STUDENT
    static void addStudent() {

        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Student s : students) {
            if (s.studentId == id) {
                System.out.println("Student ID already exists!");
                return;
            }
        }

        System.out.print("Enter Student Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Department: ");
        String department = sc.nextLine();

        students.add(new Student(id, name, department));

        saveStudents();

        System.out.println("Student Registered Successfully!");
    }

    // DISPLAY STUDENTS
    static void displayStudents() {

        if (students.isEmpty()) {
            System.out.println("No Students Registered!");
            return;
        }

        for (Student s : students) {
            s.displayStudent();
        }
    }
    static void adminDashboard(){
        int totalBooks=books.size();
        int issuedBooks=0;
        for(Book b:books){
            if(b.isIssued){
                issuedBooks++;
            }
        }
        int availableBooks=totalBooks-issuedBooks;
        int totalStudents=students.size();
        System.out.println("\n===== ADMIN DASHBOARD ======");
        System.out.println("Total Books: " + totalBooks);
        System.out.println("Issued Books: " + issuedBooks);
        System.out.println("Available Books: " + availableBooks);
        System.out.println("Total Students: " + totalStudents);

        System.out.println("=================================");
    }

    // SEARCH STUDENT
    static void searchStudent() {

        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();

        for (Student s : students) {
            if (s.studentId == id) {
                s.displayStudent();
                return;
            }
        }

        System.out.println("Student Not Found!");
    }

    // ISSUE BOOK
    static void issueBook() {

        System.out.print("Enter Student ID: ");
        int studentId = sc.nextInt();

        Student foundStudent = null;

        for (Student s : students) {
            if (s.studentId == studentId) {
                foundStudent = s;
                break;
            }
        }

        if (foundStudent == null) {
            System.out.println("Student Not Registered!");
            return;
        }

        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();

        for (Book b : books) {

            if (b.id == id) {

                if (!b.isIssued) {

                    b.isIssued = true;
                    b.issuedTo = String.valueOf(studentId);
                    b.issueDate = LocalDate.now().toString();
                    System.out.println("issue date:"+b.issueDate);

                    saveBooks();

                    System.out.println("Book Issued Successfully!");
                } else {
                    System.out.println("Book Already Issued!");
                }

                return;
            }
        }

        System.out.println("Book Not Found!");
    }

    // RETURN BOOK
    static void returnBook() {

        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();

        for (Book b : books) {

            if (b.id == id) {

                if (b.isIssued) {

                    b.isIssued = false;
                    b.returnDate = LocalDate.now().toString();
                    System.out.println("return date:"+b.returnDate);

                    saveBooks();

                    System.out.println("Book Returned Successfully!");

                } else {
                    System.out.println("Book Was Not Issued!");
                }

                return;
            }
        }

        System.out.println("Book Not Found!");
    }

    // MAIN METHOD
    public static void main(String[] args) {

        loadBooks();
        loadStudents();

        if (!adminLogin()) {
            sc.close();
            return;
        }

        int choice;

        do {

            System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Book");
            System.out.println("2. Display Books");
            System.out.println("3. Search Book");
            System.out.println("4. Issue Book");
            System.out.println("5. Return Book");
            System.out.println("6. Register Student");
            System.out.println("7. Display Students");
            System.out.println("8. Search Student");
            System.out.println("9. Exit");
            System.out.println("10.LOgout");
            System.out.println("11. Admin Dashboard");

            System.out.print("Enter Your Choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    addBook();
                    break;

                case 2:
                    displayBooks();
                    break;

                case 3:
                    searchBook();
                    break;

                case 4:
                    issueBook();
                    break;

                case 5:
                    returnBook();
                    break;

                case 6:
                    addStudent();
                    break;

                case 7:
                    displayStudents();
                    break;

                case 8:
                    searchStudent();
                    break;

                case 9:
                    System.out.println("Thank You!");
                    break;
                case 10:
                    System.out.println("admin logged out sucessfully");
                    break;
                case 11:
                    adminDashboard();
                    break;


                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 9 && choice !=10);


                
            

        

        sc.close();
    }
}