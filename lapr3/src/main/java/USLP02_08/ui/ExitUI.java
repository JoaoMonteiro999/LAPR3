package USLP02_08.ui;

import USLP02_08.controller.ExitController;

import java.sql.SQLException;

public class ExitUI implements Runnable {

    private ExitController controller;

    public ExitUI() {
        controller = new ExitController();
    }

    public void run() {
        try {
            controller.exit();
            System.out.println("Successful termination.");
            System.exit(0);
        } catch (SQLException e) {
            System.out.println("Unsuccessful termination!");
            System.exit(-1);
        }
    }

}
