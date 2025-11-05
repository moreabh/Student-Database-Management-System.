import java.sql.*;
import java.util.Scanner;

public class StudentManagementSystem {

   static final String URL = "jdbc:mysql://localhost:3306/studentdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    static final String USER = "root"; // your MySQL username
    static final String PASSWORD = "@Bhi1834"; // your MySQL password

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            // Load MySQL JDBC Driver
            Class.forName("C:\\Users\\morea\\Downloads\\mysql-connector-j-9.5.0\\mysql-connector-j-9.5.0.jar");

            // Establish Connection
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to Database Successfully!");

            while (true) {
                System.out.println("\n---- Student Management System ----");
                System.out.println("1. Add Student");
                System.out.println("2. Update Student");
                System.out.println("3. Delete Student");
                System.out.println("4. View Students");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        addStudent(con, sc);
                        break;
                    case 2:
                        updateStudent(con, sc);
                        break;
                    case 3:
                        deleteStudent(con, sc);
                        break;
                    case 4:
                        viewStudents(con);
                        break;
                    case 5:
                        con.close();
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found. Please add the connector JAR file.");
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
    }

    private static void addStudent(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter name: ");
        String name = sc.next();
        System.out.print("Enter age: ");
        int age = sc.nextInt();
        System.out.print("Enter marks: ");
        double marks = sc.nextDouble();

        String query = "INSERT INTO students(name, age, marks) VALUES (?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, name);
        ps.setInt(2, age);
        ps.setDouble(3, marks);
        ps.executeUpdate();
        System.out.println("Student added successfully!");
    }

    private static void updateStudent(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter student ID to update: ");
        int id = sc.nextInt();
        System.out.print("Enter new marks: ");
        double marks = sc.nextDouble();

        String query = "UPDATE students SET marks = ? WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setDouble(1, marks);
        ps.setInt(2, id);
        int rows = ps.executeUpdate();
        if (rows > 0)
            System.out.println("Student updated successfully!");
        else
            System.out.println("Student not found!");
    }

    private static void deleteStudent(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter student ID to delete: ");
        int id = sc.nextInt();

        String query = "DELETE FROM students WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);
        int rows = ps.executeUpdate();
        if (rows > 0)
            System.out.println("Student deleted successfully!");
        else
            System.out.println("Student not found!");
    }

    private static void viewStudents(Connection con) throws SQLException {
        String query = "SELECT * FROM students";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);

        System.out.println("\nID\tName\tAge\tMarks");
        System.out.println("----------------------------------");
        while (rs.next()) {
            System.out.println(rs.getInt("id") + "\t" + rs.getString("name") + "\t" +
                               rs.getInt("age") + "\t" + rs.getDouble("marks"));
        }
    }
}
