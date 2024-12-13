import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static Stack studentStack;
    private static Stack studentStack_Quick;
    private static Stack studentStack_Bubble;

    private static Random random = new Random();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize the stack for managing students
        System.out.print("Specify the number of students to manage: ");
        int numberOfStudents = scanner.nextInt();
        studentStack = new Stack(numberOfStudents);
        studentStack_Bubble = new Stack(numberOfStudents);
        studentStack_Quick = new Stack(numberOfStudents);

        generateRandomStudents();

        long start_bubble = System.nanoTime();
        studentStack_Bubble.sortStudentsBubble();
        long end_bubble = System.nanoTime();

        System.out.println("Time bubble:"+ (end_bubble - start_bubble));


        long start_quick = System.nanoTime();


        studentStack_Quick.sortStudentsQuick();
        long end_quick = System.nanoTime();

        System.out.println("Time Quick:"+ (end_quick-start_quick));
    }

    private static void displayMenu() {
        System.out.println("\n--- Student Records Management System ---");
        System.out.println("1. Register a New Student");
        System.out.println("2. Update Student Information");
        System.out.println("3. Remove a Student Record");
        System.out.println("4. Find a Student");
        System.out.println("5. Organize Students (Quick Sort)");
        System.out.println("6. Organize Students (Bubble Sort)");
        System.out.println("7. Show All Students");
        System.out.println("8. Add Randomly Generated Students");
        System.out.println("9. Exit the Program");
    }

    private static void addStudent(Scanner scanner) {
        try {
            System.out.print("Provide a unique student ID: ");
            int id = scanner.nextInt();
            if (id < 0) {
                System.out.println("The ID cannot be less than zero. Please try again.");
                return;
            }

            scanner.nextLine(); // Consume newline left by nextInt()
            String name = getValidName(scanner);

            System.out.print("Enter the student's marks: ");
            double marks = scanner.nextDouble();
            if (marks < 0) {
                System.out.println("Marks must be a positive value. Please re-enter.");
                return;
            }

            Student student = new Student(id, name, marks);
            studentStack.push(student);
            System.out.println("Student has been successfully added.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid data detected. Please enter correct values.");
            scanner.next(); // Clear invalid input
        }
    }

    private static void editStudent(Scanner scanner) {
        try {
            System.out.print("Specify the ID of the student to update: ");
            int id = scanner.nextInt();
            if (id < 0) {
                System.out.println("The ID must be a non-negative number. Try again.");
                return;
            }

            scanner.nextLine(); // Consume newline left by nextInt()
            String name = getValidName(scanner);

            System.out.print("Input the updated marks for the student: ");
            double marks = scanner.nextDouble();
            if (marks < 0) {
                System.out.println("Marks cannot be negative. Please input a valid number.");
                return;
            }

            studentStack.editStudent(id, name, marks);
            System.out.println("Student details have been successfully updated.");
        } catch (InputMismatchException e) {
            System.out.println("Error: Input must be in the correct format. Please try again.");
            scanner.next(); // Clear invalid input
        }
    }

    private static void deleteStudent(Scanner scanner) {
        System.out.print("Enter the ID of the student to remove: ");
        int id = scanner.nextInt();
        studentStack.deleteStudent(id);
        System.out.println("The student record has been deleted.");
    }

    private static void searchStudent(Scanner scanner) {
        System.out.print("Provide the student ID to locate: ");
        int id = scanner.nextInt();
        Student foundStudent = studentStack.searchStudent(id);
        if (foundStudent != null) {
            System.out.printf("Student Found! ID: %d, Name: %s, Marks: %.2f, Rank: %s%n",
                    foundStudent.getId(), foundStudent.getName(), foundStudent.getMarks(), foundStudent.getRank());
        } else {
            System.out.println("No student with this ID exists in the records.");
        }
    }

    private static void generateRandomStudents() {
       // System.out.print("How many random students should be created? ");
        int count = 1000;
                //scanner.nextInt();

        for (int i = 0; i < count; i++) {
            int id =1+ random.nextInt(1000); // Random ID between 0 and 999
            String name = "AutoStudent" + (i + 1); // Naming convention for generated students
            double marks = 1 + (10 - 1) * random.nextDouble(); // Marks between 1.0 and 10.0
            Student student = new Student(id, name, marks);
            studentStack.push(student);
            studentStack_Quick.push(student);
            studentStack_Bubble.push(student);

        }
        System.out.println(count + " random students have been successfully added.");
    }

    private static String getValidName(Scanner scanner) {
        String name;
        while (true) {
            System.out.print("Input the full name of the student: ");
            name = scanner.nextLine();
            if (name.matches("[a-zA-Z ]+")) {
                return name;
            } else {
                System.out.println("The name should only contain letters and spaces. Please re-enter.");
            }
        }
    }
}
