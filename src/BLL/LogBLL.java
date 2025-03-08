package BLL;

import DAL.LogDAO;
import DTO.LogDTO;
import java.sql.SQLException;
import java.util.List;

public class LogBLL {
    private LogDAO logDAO = new LogDAO();

    public List<LogDTO> getAllLogs() throws SQLException {
        return logDAO.getAllLogs();
    }
}