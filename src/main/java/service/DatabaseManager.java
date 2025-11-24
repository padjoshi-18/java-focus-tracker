package service;

import java.sql.*;

public class DatabaseManager {
    private static DatabaseManager instance;
    private Connection connection;

    private DatabaseManager() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:focus_sessions.db");
        initializeDatabase();
    }

    public static synchronized DatabaseManager getInstance() throws SQLException {
        if (instance == null) instance = new DatabaseManager();
        return instance;
    }

    public Connection getConnection() { return connection; }

    private void initializeDatabase() throws SQLException {
        String sql = ""CREATE TABLE IF NOT EXISTS session_logs (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                task_description TEXT NOT NULL,
                start_time TEXT NOT NULL,
                end_time TEXT NOT NULL,
                duration INTEGER NOT NULL,
                energy_level INTEGER NOT NULL CHECK(energy_level >= 1 AND energy_level <= 5),
                status TEXT NOT NULL DEFAULT 'COMPLETED' CHECK(status IN ('COMPLETED', 'CANCELLED'))
            )"";
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }
}
