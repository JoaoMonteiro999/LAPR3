package USLP02_08.ui;

import USLP02_08.controller.SementeiraRegisterController;
import USLP02_08.domain.Parcela;
import USLP02_08.domain.Planta;
import USLP02_08.ui.utils.Utils;

import java.sql.SQLException;
import java.util.*;

public class SemeaduraRegisterUI implements Runnable {

    private SementeiraRegisterController controller;

    public SemeaduraRegisterUI() {
        controller = new SementeiraRegisterController();
    }

    public void run() {
        try {
            System.out.println("Registar uma semeadura");

            List<Parcela> parcelas = controller.getParcelas();

            Collections.sort(parcelas);

            Parcela parcela = parcelas.get(Utils.showAndSelectIndex2(parcelas, "Lista de Parcelas")-1);

            List<Planta> plantas = controller.getPlantas();

            Collections.sort(plantas);

            Planta planta = plantas.get(Utils.showAndSelectIndex2(plantas, "Lista de Plantas")-1);

            Date dataInicio = Utils.readValidDateWithLabel("Data da Operação / Inicio da nova cultura");

            Date dataFim = Utils.readValidDateWithLabel("Fim da nova cultura");


            System.out.println("Área Semeada");
            double area = Utils.readValidDoubleInInterval(parcela.getArea());

            double quantidade = Utils.readValidPositiveDoubleWithLabel("Quantidade Semeada");

            controller.SementeiraRegister(parcela.getIdParcela(), planta.getIdPlanta(), dataInicio, dataFim, area, quantidade);


            System.out.println("\nOperação registada.");
        } catch (SQLException e) {
            System.out.println("\nOperação não registada!\n");
        }
    }
}
