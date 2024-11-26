package USLP02_08.controller;

import USLP02_08.dataAccess.DatabaseConnection;
import java.sql.SQLException;

public class DatabaseConnectionTestController {

    public DatabaseConnectionTestController() {
    }

    public int DatabaseConnectionTest() throws SQLException {
        int testResult = DatabaseConnection.getInstance().testConnection();
        return testResult;
    }
}
