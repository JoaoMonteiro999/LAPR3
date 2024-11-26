package USLP02_08.ui.menu;

import USLP02_08.ui.AutomatedWateringRegUI;
import USLP02_08.ui.CheckWaterUI;
import USLP02_08.ui.SelectFileUI;
import USLP02_08.ui.*;
import USLP02_08.ui.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainMenuUI implements Runnable {

    public MainMenuUI(){
    }

    @Override
    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        //options.add(new MenuItem("Database Connection Test", new DatabaseConnectionTestUI()));
        options.add(new MenuItem("Registar uma Semeadura", new SemeaduraRegisterUI()));
        options.add(new MenuItem("Registar uma Colheita", new ColheitaRegisterUI()));
        options.add(new MenuItem("Registar uma Monda", new MondaRegisterUI()));
        options.add(new MenuItem("Registar uma Fertilização", new FatorPrRegisterUI()));
        options.add(new MenuItem("Inserir Plano De Rega", new SelectFileUI()));
        options.add(new MenuItem("Verificar Rega", new CheckWaterUI()));
        options.add(new MenuItem("Registo de Rega Automática", new AutomatedWateringRegUI()));
        options.add(new MenuItem("Percursos diferentes entre um local e um hub", new USEI06_UI()));
        options.add(new MenuItem("Encontrar o caminho com mais hubs", new USEI07_UI()));
        options.add(new MenuItem("Encontrar circuito", new USEI08_UI()));
        options.add(new MenuItem("Criar Clusters", new USEI09_UI()));
        options.add(new MenuItem("Carregar ficheiro de Hubs", new USEI011_UI()));




        options.add(new MenuItem("Exit", new ExitUI()));
        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\nMain Menu");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}
