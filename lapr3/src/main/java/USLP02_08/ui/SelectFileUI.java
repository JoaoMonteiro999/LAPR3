package USLP02_08.ui;

import USLP02_08.controller.ControladorRega;

public class SelectFileUI implements  Runnable{
    @Override
    public void run() {
        ControladorRega.selectFile();
    }
}
