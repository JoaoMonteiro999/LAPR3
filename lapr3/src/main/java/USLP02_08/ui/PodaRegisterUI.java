package USLP02_08.ui;

import USLP02_08.controller.PodaRegisterController;
import USLP02_08.domain.Cultura;
import USLP02_08.domain.Parcela;
import USLP02_08.ui.utils.Utils;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class PodaRegisterUI implements Runnable {
    private PodaRegisterController controller;
    public PodaRegisterUI() {
        controller = new PodaRegisterController();
    }

    public void run() {
        try {
            System.out.println("\nRegistar uma Poda");

            List<Parcela> parcelas = controller.getParcelas();
            Collections.sort(parcelas);
            Parcela parcela = parcelas.get(Utils.showAndSelectIndex2(parcelas, "\nLista de Parcelas\n")-1);

            List<Cultura> culturas = controller.getCulturasPermanantes(parcela.getIdParcela());
            Collections.sort(culturas);
            Cultura cultura = culturas.get(Utils.showAndSelectIndex2(culturas, "\nLista de Culturas Permanentes\n")-1);

            System.out.println("\nInsira uma Data de Poda");
            Date dataOperacao = Utils.readValidDateWithLabel("");

            double quantidade = Utils.readValidPositiveDoubleWithLabel("\nQuantidade Podada (un)");

            controller.PodaRegister(parcela.getIdParcela(), cultura.getIdCultura(), dataOperacao, quantidade);

            System.out.println("\nOperação registada.");
        } catch (SQLException e) {
            System.out.println("\nOperação não registada!\n");
        }
    }
}
