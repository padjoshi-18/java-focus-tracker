package cli;

import dao.SessionDAO;
import model.FocusSession;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class FocusApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final SessionDAO sessionDAO = new SessionDAO();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(""yyyy-MM-dd HH:mm:ss"");

    private static String nowStr() {
        return LocalDateTime.now().format(formatter);
    }

    public static void main(String[] args) {
        System.out.println(""=== Focus Session Logger ===\"");
        
        while (true) {
            System.out.println(""\n1. Start Focus Session"");
            System.out.println(""2. View Session History""); 
            System.out.println(""3. Exit"");
            System.out.print(""Choose option: "");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case ""1"":
                    startSession();
                    break;
                case ""2"":
                    viewHistory();
                    break;
                case ""3"":
                    System.out.println(""Goodbye!"");
                    return;
                default:
                    System.out.println(""Invalid option. Try again."");
            }
        }
    }

    private static void startSession() {
        try {
            System.out.print(""\nEnter task description: "");
            String task = scanner.nextLine();
            
            if (task.trim().isEmpty()) {
                System.out.println(""Task description cannot be empty."");
                return;
            }
            
            System.out.println(""Press ENTER to start session..."");
            scanner.nextLine();
            
            String startTime = nowStr();
            System.out.println(""Session started at: "" + startTime);
            System.out.println(""Press ENTER when session is complete..."");
            scanner.nextLine();
            
            String endTime = nowStr();
            LocalDateTime start = LocalDateTime.parse(startTime, formatter);
            LocalDateTime end = LocalDateTime.parse(endTime, formatter);
            int duration = (int) Duration.between(start, end).getSeconds();
            
            System.out.print(""Energy level (1-5, where 5 is most energized): "");
            int energyLevel = Integer.parseInt(scanner.nextLine());
            
            if (energyLevel < 1 || energyLevel > 5) {
                System.out.println(""Invalid energy level. Session not saved."");
                return;
            }
            
            FocusSession session = new FocusSession(0, task, startTime, endTime, duration, energyLevel, ""COMPLETED"");
            sessionDAO.insertSession(session);
            
            System.out.printf(""Session saved! Duration: %d seconds\n"", duration);
            
        } catch (NumberFormatException e) {
            System.out.println(""Invalid number format. Session not saved."");
        } catch (Exception e) {
            System.out.println(""Error: "" + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void viewHistory() {
        try {
            System.out.println(""\n=== Session History ===\"");
            var sessions = sessionDAO.getAllSessions();
            
            if (sessions.isEmpty()) {
                System.out.println(""No sessions found."");
                return;
            }
            
            for (FocusSession session : sessions) {
                System.out.printf(""ID: %d | Task: %s | Duration: %ds | Energy: %d/5\n"",
                    session.getId(), session.getTaskDescription(), session.getDuration(), session.getEnergyLevel());
                System.out.printf(""     Start: %s | End: %s\n"", session.getStartTime(), session.getEndTime());
                System.out.println(""     "" + ""-"".repeat(50));
            }
            
        } catch (Exception e) {
            System.out.println(""Error retrieving history: "" + e.getMessage());
        }
    }
}
