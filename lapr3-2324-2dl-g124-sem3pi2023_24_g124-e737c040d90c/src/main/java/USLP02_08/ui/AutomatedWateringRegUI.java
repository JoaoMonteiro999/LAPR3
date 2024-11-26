package USLP02_08.ui;

import USLP02_08.controller.ControladorRega;

public class AutomatedWateringRegUI implements Runnable{
    @Override
    public void run() {
        ControladorRega.startAutomatedWatering();
    }
}
