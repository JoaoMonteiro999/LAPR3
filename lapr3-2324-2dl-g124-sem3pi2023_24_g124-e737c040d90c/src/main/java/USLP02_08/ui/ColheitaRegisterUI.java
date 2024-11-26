package USLP02_08.ui;

import USLP02_08.controller.ColheitaRegisterController;
import USLP02_08.domain.Cultura;
import USLP02_08.domain.Parcela;
import USLP02_08.ui.utils.Utils;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ColheitaRegisterUI implements Runnable {
    private ColheitaRegisterController controller;
    public ColheitaRegisterUI() {
        controller = new ColheitaRegisterController();
    }

    public void run() {
        try {
            System.out.println("\nRegistar uma Colheita");

            List<Parcela> parcelas = controller.getParcelas();
            Collections.sort(parcelas);
            Parcela parcela = parcelas.get(Utils.showAndSelectIndex2(parcelas, "\nLista de Parcelas\n")-1);

            List<Cultura> culturas = controller.getCulturas(parcela.getIdParcela());
            Collections.sort(culturas);
            Cultura cultura = culturas.get(Utils.showAndSelectIndex2(culturas, "\nLista de Culturas\n")-1);

            System.out.println("\nInsira uma Data de Colheita entre os Intervalos");
            Date dataOperacao = Utils.readValidDateWithLabel("");

            double quantidade = Utils.readValidPositiveDoubleWithLabel("\nQuantidade Colhida (Kg)");

            controller.ColheitaRegister(parcela.getIdParcela(), cultura.getIdCultura(), dataOperacao, quantidade);

            System.out.println("\nOperação registada.");
        } catch (SQLException e) {
            System.out.println("\nOperação não registada!\nData Inválida!\n");
        }
    }
}
