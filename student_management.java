//===== STUDENT MANAGEMENT SYSTEM =====

// 1. Add Student
// 2. View Students
// 3. Update Student Marks
// 4. Delete Student
// 5. Exit
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class student_management {

    //adding students
    public static void addstudent(Connection con, Scanner sc) {

        try {

            System.out.print("Enter Name: ");
            String names = sc.nextLine();

            System.out.print("Enter Age: ");
            int age = sc.nextInt();

            System.out.print("Enter Marks: ");
            double marks = sc.nextDouble();

            String query
                    = "INSERT INTO student(names, age, marks) VALUES(?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, names);
            ps.setInt(2, age);
            ps.setDouble(3, marks);

            int rows = ps.executeUpdate();

            System.out.println(rows + " student added successfully!");

            ps.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //to view the students data
    public static void viewstudent(Connection con) {
        try {
            String query = "SELECT * FROM student;";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("names");
                double marks = rs.getDouble("marks");
                int age = rs.getInt("age");

                System.out.println(id + "\t" + name + "\t" + marks + "\t" + age);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //to update data of the students
    public static void updatestudent(Connection con, Scanner sc) {
        System.out.println("enter id of student of which you want to update marks: ");
        int id = sc.nextInt();
        System.out.println("enter updated marks: ");
        double marks = sc.nextDouble();

        try {

            String query = "UPDATE student SET marks = ? WHERE id = ?;";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(2, id);
            ps.setDouble(1, marks);
            int rows = ps.executeUpdate();
            System.out.println(rows + "Rows updated");

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    //to delete data of student
    public static void deletestudent(Connection con, Scanner sc) {
        System.out.println("enter id of student to delete: ");
        int id = sc.nextInt();
        String query = "DELETE FROM student WHERE id = ?;";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            System.out.println(rows + "succesfully deleted");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    //main class
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Connection con = null;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/student_management",
                    "root",
                    "");

            while (true) {

                System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
                System.out.println("1. Add Student");
                System.out.println("2. View Students");
                System.out.println("3. Update Student");
                System.out.println("4. Delete Student");
                System.out.println("5. Exit");

                System.out.print("Enter Choice: ");
                int choice = sc.nextInt();

                switch (choice) {

                    case 1:
                        sc.nextLine(); // consume leftover Enter
                        addstudent(con, sc);
                        break;

                    case 2:
                        viewstudent(con);
                        break;

                    case 3:
                        updatestudent(con, sc);
                        break;

                    case 4:
                        deletestudent(con, sc);
                        break;

                    case 5:
                        System.out.println("Thank You!");
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid Choice!");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("error" + e);
        }

    }
}
