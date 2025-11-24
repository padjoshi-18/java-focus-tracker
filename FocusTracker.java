import java.sql.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

public class FocusTracker {
    private static Connection connection;
    private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public static void main(String[] args) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:focus_sessions.db");
            initializeDatabase();
            runApp();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void initializeDatabase() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS session_logs (id INTEGER PRIMARY KEY AUTOINCREMENT, task_description TEXT NOT NULL, start_time TEXT NOT NULL, end_time TEXT NOT NULL, duration INTEGER NOT NULL, energy_level INTEGER NOT NULL CHECK(energy_level >= 1 AND energy_level <= 5), status TEXT NOT NULL DEFAULT 'COMPLETED')";
        connection.createStatement().execute(sql);
    }
    
    private static void runApp() {
        System.out.println("=== Focus Session Logger ===");
        while (true) {
            System.out.println("\n1. Start Focus Session\n2. View History\n3. Exit");
            System.out.print("Choose: ");
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1": startSession(); break;
                case "2": viewHistory(); break;
                case "3": return;
                default: System.out.println("Invalid choice");
            }
        }
    }
    
    private static void startSession() {
        try {
            System.out.print("Enter task: ");
            String task = scanner.nextLine();
            
            System.out.println("Press ENTER to start...");
            scanner.nextLine();
            String start = LocalDateTime.now().format(formatter);
            
            System.out.println("Press ENTER to end...");
            scanner.nextLine();
            String end = LocalDateTime.now().format(formatter);
            
            int duration = (int) Duration.between(LocalDateTime.parse(start, formatter), LocalDateTime.parse(end, formatter)).getSeconds();
            
            System.out.print("Energy (1-5): ");
            int energy = Integer.parseInt(scanner.nextLine());
            
            if (energy < 1 || energy > 5) {
                System.out.println("Invalid energy level");
                return;
            }
            
            String sql = "INSERT INTO session_logs (task_description, start_time, end_time, duration, energy_level) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, task);
            stmt.setString(2, start);
            stmt.setString(3, end);
            stmt.setInt(4, duration);
            stmt.setInt(5, energy);
            stmt.executeUpdate();
            
            System.out.println("Session saved! Duration: " + duration + "s");
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private static void viewHistory() {
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM session_logs ORDER BY id DESC");
            System.out.println("\n=== Session History ===");
            boolean hasData = false;
            
            while (rs.next()) {
                hasData = true;
                System.out.printf("ID: %d | Task: %s | Duration: %ds | Energy: %d/5\n", rs.getInt("id"), rs.getString("task_description"), rs.getInt("duration"), rs.getInt("energy_level"));
                System.out.printf("     Start: %s | End: %s\n", rs.getString("start_time"), rs.getString("end_time"));
                System.out.println("     " + "-".repeat(50));
            }
            
            if (!hasData) System.out.println("No sessions found");
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
