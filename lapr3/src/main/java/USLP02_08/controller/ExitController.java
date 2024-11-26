package USLP02_08.controller;

import USLP02_08.dataAccess.DatabaseConnection;
import java.sql.SQLException;

public class ExitController {

    public ExitController(){
    }

    public void exit() throws SQLException {
        DatabaseConnection.getInstance().closeConnection();
    }
}
