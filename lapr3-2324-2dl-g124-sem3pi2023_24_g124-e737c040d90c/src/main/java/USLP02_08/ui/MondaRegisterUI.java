package USLP02_08.ui;

import USLP02_08.controller.MondaRegisterController;
import USLP02_08.domain.Cultura;
import USLP02_08.domain.Parcela;
import USLP02_08.ui.utils.Utils;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MondaRegisterUI implements Runnable {
    private MondaRegisterController controller;
    public MondaRegisterUI() {
        controller = new MondaRegisterController();
    }

    public void run() {
        try {
            System.out.println("Registar uma Monda");

            List<Parcela> parcelas = controller.getParcelas();
            Collections.sort(parcelas);
            Parcela parcela = parcelas.get(Utils.showAndSelectIndex2(parcelas, "\nLista de Parcelas\n")-1);

            List<Cultura> culturas = controller.getCulturas(parcela.getIdParcela());
            Collections.sort(culturas);
            Cultura cultura = culturas.get(Utils.showAndSelectIndex2(culturas, "\nLista de Culturas\n")-1);

            System.out.println("\nInsira uma Data de Monda");
            Date dataOperacao = Utils.readValidDateWithLabel("");

            System.out.println("\nÁrea Mondada");
            double area = Utils.readValidDoubleInInterval(cultura.getArea());

            controller.MondaRegister(parcela.getIdParcela(), cultura.getIdCultura(), dataOperacao, area);

            System.out.println("\nOperação registada.");
        } catch (SQLException e) {
            System.out.println("\nOperação não registada!\n");
        }
    }
}
