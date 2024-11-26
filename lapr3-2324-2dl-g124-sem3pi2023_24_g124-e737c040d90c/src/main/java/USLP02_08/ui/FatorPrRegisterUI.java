package USLP02_08.ui;

import USLP02_08.controller.FatorPrRegisterController;
import USLP02_08.domain.Cultura;
import USLP02_08.domain.FatorProducao;
import USLP02_08.domain.ModoDeAplicacaoFator;
import USLP02_08.domain.Parcela;
import USLP02_08.ui.utils.Utils;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class FatorPrRegisterUI implements Runnable  {

    private FatorPrRegisterController controller;
    public FatorPrRegisterUI() {
        controller = new FatorPrRegisterController();
    }


    @Override
    public void run() {

        try {
            System.out.println("Registar uma Aplicação de fator de producao");

            List<Parcela> parcelas = controller.getParcelas();
            Collections.sort(parcelas);
            Parcela parcela = parcelas.get(Utils.showAndSelectIndex2(parcelas, "\nLista de Parcelas\n")-1);

            List<Cultura> culturas = controller.getCulturas(parcela.getIdParcela());
            Collections.sort(culturas);
            Cultura cultura = culturas.get(Utils.showAndSelectIndex2(culturas, "\nLista de Culturas\n")-1);

            Date dataOperacao = Utils.readValidDateWithLabel("\nData da fertilização");

            List<FatorProducao> fatoresProducao = controller.getFatorProducao();
            Collections.sort(fatoresProducao);
            FatorProducao fatorProducao = fatoresProducao.get(Utils.showAndSelectIndex2(fatoresProducao, "\nLista de Fatores de produção")-1);

            List<ModoDeAplicacaoFator> modos = controller.getModos();
            Collections.sort(modos);
            ModoDeAplicacaoFator modo = modos.get(Utils.showAndSelectIndex2(modos, "\nLista de Modos de aplicação de fator")-1);

            System.out.print("\nÁrea Fertilizada:");
            double area = Utils.readValidDoubleInInterval(cultura.getArea());

            double quantidade = Utils.readValidPositiveDoubleWithLabel("\nQuantidade aplicada (Kg)");

            controller.FatorPrRegister(parcela.getIdParcela(), cultura.getIdCultura(), dataOperacao,fatorProducao.getDesignacaoFatorPr(), modo.getModo(), area, quantidade);

            System.out.println("\nOperação registada.");
        } catch (SQLException e) {
            System.out.println("\nOperação não registada!\nData Inválida!\n");
        }

    }
}
