package dao;

import model.FocusSession;
import service.DatabaseManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SessionDAO {
    public void insertSession(FocusSession session) throws SQLException {
        String sql = ""INSERT INTO session_logs (task_description, start_time, end_time, duration, energy_level, status) VALUES (?, ?, ?, ?, ?, ?)"";
        try (PreparedStatement stmt = DatabaseManager.getInstance().getConnection().prepareStatement(sql)) {
            stmt.setString(1, session.getTaskDescription());
            stmt.setString(2, session.getStartTime());
            stmt.setString(3, session.getEndTime());
            stmt.setInt(4, session.getDuration());
            stmt.setInt(5, session.getEnergyLevel());
            stmt.setString(6, session.getStatus());
            stmt.executeUpdate();
        }
    }

    public List<FocusSession> getAllSessions() throws SQLException {
        String sql = ""SELECT * FROM session_logs ORDER BY id DESC"";
        List<FocusSession> sessions = new ArrayList<>();
        
        try (Statement stmt = DatabaseManager.getInstance().getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                FocusSession session = new FocusSession(
                    rs.getInt(""id""),
                    rs.getString(""task_description""),
                    rs.getString(""start_time""),
                    rs.getString(""end_time""),
                    rs.getInt(""duration""),
                    rs.getInt(""energy_level""),
                    rs.getString(""status"")
                );
                sessions.add(session);
            }
        }
        return sessions;
    }
}
