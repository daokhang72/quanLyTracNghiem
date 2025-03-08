package DAL;

import DTO.LogDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogDAO {
    public List<LogDTO> getAllLogs() throws SQLException {
        List<LogDTO> logs = new ArrayList<>();
        String query = "SELECT * FROM logs";
        try (Connection con = DatabaseHelper.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                LogDTO log = new LogDTO(
                        rs.getInt("logID"),
                        rs.getString("logContent"),
                        rs.getInt("logUserID"),
                        rs.getInt("logExID"),
                        rs.getTimestamp("logDate")
                );
                logs.add(log);
            }
        }
        return logs;
    }
}