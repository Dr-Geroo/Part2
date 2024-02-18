import java.io.*;
import java.util.*;

class Course {
    private String courseCode;
    private Lecturer assignedLecturer;
    private List<Student> enrolledStudents;
    private List<Course> prerequisites;
    private int credits;

    /**
     * Constructor for Course.
     *
     * @param courseCode       The code identifying the course.
     * @param assignedLecturer The lecturer assigned to the course.
     * @param prerequisites    The list of prerequisite courses.
     * @param credits          The number of credits for the course.
     */
    public Course(String courseCode, Lecturer assignedLecturer, List<Course> prerequisites, int credits) {
        this.courseCode = courseCode;
        this.assignedLecturer = assignedLecturer;
        this.enrolledStudents = new ArrayList<>();
        this.prerequisites = prerequisites;
        this.credits = credits;
    }

    /**
     * Enrolls a student in the course.
     *
     * @param student The student to be enrolled.
     */
    public void enrollStudent(Student student) {
        enrolledStudents.add(student);
    }

    /**
     * Checks if a student has completed the prerequisites for the course.
     *
     * @param student The student to be checked.
     * @return True if the student has completed all prerequisites, false otherwise.
     */
    public boolean hasCompletedPrerequisites(Student student) {
        for (Course prerequisites : prerequisites) {
            if (!student.hasCompletedCourse(prerequisites)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if a student meets the prerequisites and credit requirements for the
     * course.
     *
     * @param student The student to be checked.
     * @return True if the student meets the requirements, false otherwise.
     */
    public boolean meetsPrerequisites(Student student) {
        if (prerequisites.isEmpty()) {
            return true;
        }

        if (!hasCompletedPrerequisites(student)) {
            return false;
        }

        if (getcourseCode().equals("CS316") && student.getTotalCredits() < 15) {
            return false;
        }

        return true;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public Lecturer getAssignedLecturer() {
        return assignedLecturer;
    }

    public void setAssignedLecturer(Lecturer assignedLecturer) {
        this.assignedLecturer = assignedLecturer;
    }

    /**
     * Gets the course code
     *
     * @return the course code.
     */

    public String getcourseCode() {
        return courseCode;
    }

    /**
     * Gets the prerequisites of a course
     *
     * @return the prerequisites.
     */

    public List<Course> getprerequisites() {
        return prerequisites;
    }

    /**
     * Gets the credits of a course
     *
     * @return the credits.
     */

    public int getCredits() {
        return credits;
    }
}

class User {
    private String username;
    private String password;

    /**
     * Constructor for User.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the username of the user.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password of the user.
     *
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Authenticates the user with the provided username and password.
     *
     * @param enteredUsername The entered username for authentication.
     * @param enteredPassword The entered password for authentication.
     * @return True if authentication is successful, false otherwise.
     */

    public boolean authenticate(String enteredUsername, String enteredPassword) {
        return username.equals(enteredUsername) && password.equals(enteredPassword);
    }
}

class Admin extends User {
    private List<Admin> admins;
    private List<Student> students;
    private List<Lecturer> lecturers;
    private List<Course> courses;
    private Set<String> courseCodeSet;
    private Map<String, Integer> studentCreditsMap;

    /**
     * Constructor for Admin.
     *
     * @param username The username of the admin.
     * @param password The password of the admin.
     */

    public Admin(String username, String password) {
        super(username, password);

        this.admins = new ArrayList<>();
        this.students = new ArrayList<>();
        this.lecturers = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.courseCodeSet = new HashSet<>();
        this.studentCreditsMap = new HashMap<>();

    }

    /**
     * adds course codes to a set
     */

    public void addCourseCodeToSet(String courseCode) {
        courseCodeSet.add(courseCode);
    }

    public void addStudentCredits(String studentUsername, int credits) {
        studentCreditsMap.put(studentUsername, credits);
    }

    public void printStudentCredistMap() {
        System.out.println("Student Credits in the Map");
        for (Map.Entry<String, Integer> entry : studentCreditsMap.entrySet()) {
            System.out.println("-" + entry.getKey() + ": " + entry.getValue() + " credits");
        }
    }

    public void printCourseCodesSet() {
        System.out.println("Course Code int the Set:");
        for (String courseCode : courseCodeSet) {
            System.out.println("- " + courseCode);
        }
    }

    public void registerFirstAdmin() {
        Admin firstAdmin = new Admin("Farajalla", "Farajalla");
        admins.add(firstAdmin);
        System.out.println("First Admin registered successfully");
    }

    public Admin loginAdmin(String username, String password) {
        if (getUsername().equals(username) && getPassword().equals(password)) {
            return this;
        }

        for (Admin admin : admins) {
            if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                return admin;
            }
        }

        return null;
    }

    public Student loginStudent(String username, String password) {
        for (Student student : students) {
            if (student.authenticate(username, password)) {
                return student;
            }
        }
        return null;
    }

    public Lecturer loginLecturer(String username, String password) {
        for (Lecturer lecturer : lecturers) {
            if (lecturer.authenticate(username, password)) {
                return lecturer;
            }
        }
        return null;
    }

    public Course findCourseByCode(String courseCode) {
        for (Course course : courses) {
            if (course.getcourseCode().equals(courseCode)) {
                return course;
            }
        }

        return null;

    }

    public void createStudent(String username, String password) {
        Student student = new Student(username, password);
        students.add(student);
    }

    public void createLecturer(String username, String password) {
        Lecturer lecturer = new Lecturer(username, password);
        lecturers.add(lecturer);
    }

    public void createCourse(String courseCode, Lecturer lecturer, List<Course> prerequisites, int credits) {
        Course course = new Course(courseCode, lecturer, prerequisites, credits);
        courses.add(course);
    }

    public void assignCourseToLecturer(Course course, Lecturer lecturer) {
        course.setAssignedLecturer(lecturer);
    }

    public void saveToCSV(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("Type,User,Password");

            for (Admin admin : admins) {
                writer.println("Admin," + admin.getUsername() + "," + admin.getPassword());
            }

            for (Student student : students) {
                writer.println("Student," + student.getUsername() + "," + student.getPassword());
            }

            for (Lecturer lecturer : lecturers) {
                writer.println("Lecturer," + lecturer.getUsername() + "," + lecturer.getPassword());
            }

            for (Course course : courses) {
                writer.println("Course," + course.getcourseCode() + "," + course.getAssignedLecturer().getUsername());
            }

            System.out.println("Data saved to CSV fille: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Admin loadFromCSV(String fileName) {
        Admin loadedAdmin = new Admin("Farajalla", "Farajalla");

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] parts = line.split(",");
                String type = parts[0];
                String username = parts[1];
                String password = parts[2];

                switch (type) {
                    case "Admin":
                        loadedAdmin.admins.add(new Admin(username, password));
                        break;

                    case "Student":
                        loadedAdmin.students.add(new Student(username, password));
                        break;

                    case "Lecturer":
                        loadedAdmin.lecturers.add(new Lecturer(username, password));
                        break;

                    case "Course":
                        if (parts.length >= 5) {
                            Lecturer lecturer = loadedAdmin.findLecturerByUsername(parts[3]);
                            List<Course> prerequisites = loadedAdmin.findCoursePrerequisites(parts[1]);
                            int credits = Integer.parseInt(parts[4]);
                            loadedAdmin.courses.add(new Course(parts[1], lecturer, prerequisites, credits));
                        } else {
                            System.out.println("Invalid data for Course. Skipping...");
                        }
                        break;
                }
            }

            System.out.println("Data loaded from CSV file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return loadedAdmin;
    }

    Lecturer findLecturerByUsername(String username) {
        for (Lecturer lecturer : lecturers) {
            if (lecturer.getUsername().equals(username)) {
                return lecturer;
            }
        }

        return null;
    }

    public List<Course> findCoursePrerequisites(String courseCode) {
        List<Course> prerequisites = new ArrayList<>();

        switch (courseCode) {
            case "CS214":
            case "CS224":

                prerequisites.add(findCourseByCode("CS113"));
                prerequisites.add(findCourseByCode("CS123"));
                break;

            case "CS316":

                prerequisites.add(findCourseByCode("CS133"));
                prerequisites.add(findCourseByCode("CS214"));
                break;

            default:
                break;
        }

        return prerequisites;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Lecturer> getLecturers() {
        return lecturers;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public Set<String> getCourseCodeSet() {
        return courseCodeSet;
    }

    public void viewStudentsInCourse(Lecturer lecturer, Course course) {
        List<Student> students = course.getEnrolledStudents();
        System.out.println("Students enrolled in " + course.getcourseCode() + " for " + lecturer.getUsername() + ":");
        for (Student student : students) {
            System.out.println("- " + student.getUsername());
        }
    }

    public void viewAllStudentsAndLecturers() {
        System.out.println("\nAll Students:");
        for (Student student : students) {
            System.out.println("- " + student.getUsername());
        }

        System.out.println("\nAll Lecturers:");
        for (Lecturer lecturer : lecturers) {
            System.out.println("- " + lecturer.getUsername());
        }
    }

}

/**
 * The {@code Student} class represents a user with student privileges in a
 * university system.
 * It extends the {@link User} class, inheriting basic authentication
 * functionality.
 * This class includes features specific to students, such as course
 * registration,
 * viewing past, current, and future subjects, and checking credit limits.
 * 
 * @author [Salah]
 * @version 1.0
 * @since [18/02/2024]
 */

class Student extends User {
    /**
     * List of courses that the student is currently registered for.
     */
    private List<Course> registeredCourses;
    /**
     * List of courses that the student has completed in the past.
     */
    private List<Course> pastSubjects;
    /**
     * List of courses that the student is currently taking.
     */
    private List<Course> currentSubjects;
    /**
     * List of courses that the student plans to take in the future.
     */
    private List<Course> futureSubjects = new ArrayList<>();
    /**
     * Maximum number of credits a student can take in a trimester.
     */
    private static final int MAX_CREDITS_PER_TRIMESTER = 12;
    /**
     * Minimum number of credits a student must take in a trimester.
     */
    private static final int MIN_CREDITS_PER_TRIMESTER = 3;
    private List<Course> courses;

    /**
     * Constructs a new {@code Student} object with the specified username and
     * password.
     * Initializes the registeredCourses list.
     *
     * @param username The username of the student.
     * @param password The password of the student.
     */

    public Student(String username, String password) {
        super(username, password);
        this.registeredCourses = new ArrayList<>();
        this.courses = new ArrayList<>();
    }

    /**
     * Calculates and returns the total number of credits for the registered
     * courses.
     *
     * @return The total number of credits for registered courses.
     */

    public int getTotalCredits() {
        int totalCredits = 0;
        for (Course course : registeredCourses) {
            totalCredits += course.getCredits();
        }
        return totalCredits;
    }

    /**
     * Displays the list of past, current, and future subjects for the student.
     */

    public void viewSubjects() {
        System.out.println("Past Subjects:");
        for (Course course : pastSubjects) {
            System.out.println("- " + course.getcourseCode());
        }

        System.out.println("Current Subjects:");
        for (Course course : currentSubjects) {
            System.out.println("- " + course.getcourseCode());
        }

        System.out.println("Future Subjects:");
        for (Course course : futureSubjects) {
            System.out.println("- " + course.getcourseCode());
        }
    }

    /**
     * Checks if adding a specified number of credits falls within the valid credit
     * range
     * for a trimester.
     *
     * @param creditsToAdd The number of credits to be added.
     * @return {@code true} if the credit range is valid, {@code false} otherwise.
     */

    public boolean isValidCreditRange(int creditsToAdd) {
        int totalCredits = getTotalCredits() + creditsToAdd;
        return totalCredits >= MIN_CREDITS_PER_TRIMESTER && totalCredits <= MAX_CREDITS_PER_TRIMESTER;
    }

    public void registerForCourse(Course course) {
        if (course != null && canRegisterForCourse(course)) {
            registeredCourses.add(course);
            System.out.println(getUsername() + " successfully registered for " + course.getcourseCode());
        } else {
            System.out.println("Unable to register for the course.");
        }
    }
    

    /**
     * Checks if the student is eligible to register for a specific course based on
     * prerequisites.
     *
     * @param course The course to check for eligibility.
     * @return {@code true} if eligible, {@code false} otherwise.
     */

    private boolean canRegisterForCourse(Course course) {
        for (Course prerequisite : course.getprerequisites()) {
            if (!hasCompletedCourse(prerequisite)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the student has completed a specific course.
     *
     * @param course The course to check for completion.
     * @return {@code true} if the course is completed, {@code false} otherwise.
     */

    public boolean hasCompletedCourse(Course course) {
        return registeredCourses.contains(course);
    }

    /**
     * Attempts to log in a student based on provided username and password.
     *
     * @param students List of students to search for the login credentials.
     * @param username The username for authentication.
     * @param password The password for authentication.
     * @return The logged-in student or {@code null} if login fails.
     */

    public static Student loginStudent(List<Student> students, String username, String password) {
        for (Student student : students) {
            if (student.authenticate(username, password)) {
                return student;
            }
        }
        return null;
    }

    /**
     * Retrieves the list of registered courses for the student.
     *
     * @return The list of registered courses.
     */

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public Course findCourseByCode(String courseCode) {
        for (Course course : courses) {
            if (course.getcourseCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }

    public void viewRegisteredCourses() {
        System.out.println("Registered Courses:");
        for (Course course : registeredCourses) {
            System.out.println("- " + course.getcourseCode());
        }
    }
}

/**
 * The {@code Lecturer} class represents a user with lecturer privileges in a
 * university system.
 * It extends the {@link User} class, inheriting basic authentication
 * functionality.
 * This class includes features specific to lecturers,
 * such as viewing students
 * in assigned courses
 * and assigning courses to lecturers.
 * 
 * @author [Salah]
 * @version 1.0
 * @since [18/02/2024]
 */

class Lecturer extends User {
    /**
     * List of courses assigned to the lecturer.
     */
    private List<Course> assignedCourses;

    /**
     * Constructor for Lecturer.
     *
     * @param username The username of the lecturer.
     * @param password The password of the lecturer.
     */

    public Lecturer(String username, String password) {
        super(username, password);
        this.assignedCourses = new ArrayList<>();
    }

    /**
     * Attempts to log in a lecturer based on provided username and password.
     *
     * @param lecturers List of lecturers to search for the login credentials.
     * @param username  The username for authentication.
     * @param password  The password for authentication.
     * @return The logged-in lecturer or {@code null} if login fails.
     */

    public static Lecturer loginLecturer(List<Lecturer> lecturers, String username, String password) {
        for (Lecturer lecturer : lecturers) {
            if (lecturer.authenticate(username, password)) {
                return lecturer;
            }
        }
        return null;
    }

    /**
     * Displays the list of students in the courses assigned to the lecturer.
     * If no courses are assigned, a corresponding message is printed.
     */

    public void viewStudentsInAssignedCourses() {
        if (assignedCourses.isEmpty()) {
            System.out.println("You are not assigned to any courses.");
        } else {
            System.out.println("Students in Assigned Courses:");
            for (Course course : assignedCourses) {
                System.out.println("Course: " + course.getcourseCode());
                List<Student> studentsInCourse = course.getEnrolledStudents();
                if (studentsInCourse.isEmpty()) {
                    System.out.println("No students enrolled in this course.");
                } else {
                    for (Student student : studentsInCourse) {
                        System.out.println("- " + student.getUsername());
                    }
                }
            }
        }
    }

    /**
     * Assigns a course to the lecturer.
     *
     * @param course The course to be assigned.
     */

    public void assignCourse(Course course) {
        assignedCourses.add(course);
    }

    /**
     * Retrieves the list of courses assigned to the lecturer.
     *
     * @return The list of assigned courses.
     */

    public List<Course> getAssignedCourses() {
        return assignedCourses;
    }
}


public class part2{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Admin admin = new Admin("admin", "admin"); // Assuming a default admin for system initialization

        while (true) {
            System.out.println("\n===== University System =====");
            System.out.println("1. Admin Login");
            System.out.println("2. Student Login");
            System.out.println("3. Lecturer Login");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    adminLogin(admin, scanner);
                    break;
                case 2:
                    studentLogin(admin.getStudents(), scanner);
                    break;
                case 3:
                    lecturerLogin(admin.getLecturers(), scanner);
                    break;
                case 4:
                    System.out.println("Exiting University System. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void adminLogin(Admin admin, Scanner scanner) {
        System.out.print("Enter Admin Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine();

        Admin loggedInAdmin = admin.loginAdmin(username, password);
        if (loggedInAdmin != null) {
            adminMenu(loggedInAdmin, scanner);
        } else {
            System.out.println("Invalid username or password. Login failed.");
        }
    }

    private static void studentLogin(List<Student> students, Scanner scanner) {
        System.out.print("Enter Student Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Student Password: ");
        String password = scanner.nextLine();

        Student loggedInStudent = Student.loginStudent(students, username, password);
        if (loggedInStudent != null) {
            studentMenu(loggedInStudent, scanner);
        } else {
            System.out.println("Invalid username or password. Login failed.");
        }
    }

    private static void lecturerLogin(List<Lecturer> lecturers, Scanner scanner) {
        System.out.print("Enter Lecturer Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Lecturer Password: ");
        String password = scanner.nextLine();

        Lecturer loggedInLecturer = Lecturer.loginLecturer(lecturers, username, password);
        if (loggedInLecturer != null) {
            lecturerMenu(loggedInLecturer, scanner);
        } else {
            System.out.println("Invalid username or password. Login failed.");
        }
    }

    private static void adminMenu(Admin admin, Scanner scanner) {
        while (true) {
            System.out.println("\n===== Admin Menu =====");
            System.out.println("1. Create Student");
            System.out.println("2. Create Lecturer");
            System.out.println("3. Create Course");
            System.out.println("4. Assign Lecturer to Course");
            System.out.println("5. View All Students and Lecturers");
            System.out.println("6. Save to CSV");
            System.out.println("7. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    System.out.print("Enter student username: ");
                    String studentUsername = scanner.nextLine();
                    System.out.print("Enter student password: ");
                    String studentPassword = scanner.nextLine();
                    admin.createStudent(studentUsername, studentPassword);
                    System.out.println("Student created successfully!");
                    break;
                case 2:
                    System.out.print("Enter lecturer username: ");
                    String lecturerUsername = scanner.nextLine();
                    System.out.print("Enter lecturer password: ");
                    String lecturerPassword = scanner.nextLine();
                    admin.createLecturer(lecturerUsername, lecturerPassword);
                    System.out.println("Lecturer created successfully!");
                    break;
                case 3:
                    System.out.print("Enter course code: ");
                    String courseCode = scanner.nextLine();
                    System.out.print("Enter lecturer username for the course: ");
                    String lecturerUsernameForCourse = scanner.nextLine();
                    Lecturer lecturerForCourse = admin.findLecturerByUsername(lecturerUsernameForCourse);
                    if (lecturerForCourse != null) {
                        List<Course> prerequisitesForCourse = admin.findCoursePrerequisites(courseCode);
                        System.out.print("Enter credits for the course: ");
                        int creditsForCourse = scanner.nextInt();
                        admin.createCourse(courseCode, lecturerForCourse, prerequisitesForCourse, creditsForCourse);
                        System.out.println("Course created successfully!");
                    } else {
                        System.out.println("Lecturer not found. Course creation failed.");
                    }
                    break;
                case 4:
                    System.out.print("Enter course code: ");
                    String courseCodeForAssignment = scanner.nextLine();
                    System.out.print("Enter lecturer username: ");
                    String lecturerUsernameForAssignment = scanner.nextLine();
                    Course courseForAssignment = admin.findCourseByCode(courseCodeForAssignment);
                    Lecturer lecturerForAssignment = admin.findLecturerByUsername(lecturerUsernameForAssignment);

                    if (courseForAssignment != null && lecturerForAssignment != null) {
                        admin.assignCourseToLecturer(courseForAssignment, lecturerForAssignment);
                        System.out.println("Lecturer assigned to the course successfully!");
                    } else {
                        System.out.println("Course or lecturer not found. Assignment failed.");
                    }
                    break;
                case 5:
                    admin.viewAllStudentsAndLecturers();
                    break;
                case 6:
                    System.out.print("Enter CSV file name to save data: ");
                    String fileName = scanner.nextLine();
                    admin.saveToCSV(fileName);
                    break;
                case 7:
                    System.out.println("Logging out Admin.");
                    return; // Return from the method to go back to the main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void studentMenu(Student student, Scanner scanner) {
        while (true) {
            System.out.println("\n===== Student Menu =====");
            System.out.println("1. Register for Course");
            System.out.println("2. View Registered Courses");
            System.out.println("3. View Subjects");
            System.out.println("4. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    System.out.print("Enter course code to register: ");
                    String courseCodeToRegister = scanner.nextLine();
                    Course courseToRegister = student.findCourseByCode(courseCodeToRegister);
                    if (courseToRegister != null) {
                        student.registerForCourse(courseToRegister);
                        System.out.println("Registered for the course successfully!");
                    } else {
                        System.out.println("Course not found. Registration failed.");
                    }
                    break;
                case 2:
                    student.viewRegisteredCourses();
                    break;
                case 3:
                    student.viewSubjects();
                    break;
                case 4:
                    System.out.println("Logging out Student.");
                    return; // Return from the method to go back to the main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void lecturerMenu(Lecturer lecturer, Scanner scanner) {
        while (true) {
            System.out.println("\n===== Lecturer Menu =====");
            System.out.println("1. View Students in Assigned Courses");
            System.out.println("2. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    lecturer.viewStudentsInAssignedCourses();
                    break;
                case 2:
                    System.out.println("Logging out Lecturer.");
                    return; // Return from the method to go back to the main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
