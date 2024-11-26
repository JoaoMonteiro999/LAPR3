package USLP02_08.controller;

import USLP02_08.dataAccess.OperacoesRepository;
import USLP02_08.dataAccess.ParcelaRepository;
import USLP02_08.dataAccess.PlantaRepository;
import USLP02_08.dataAccess.Repositories;
import USLP02_08.domain.Parcela;
import USLP02_08.domain.Planta;
import USLP02_08.domain.Unidade;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class SementeiraRegisterController {

    private OperacoesRepository operacoesRepository;
    private PlantaRepository plantaRepository;
    private ParcelaRepository parcelaRepository;

    public SementeiraRegisterController(){
        getOperacoesRepository();
        getPlantaRepository();
        getParcelaRepository();
    }

    private OperacoesRepository getOperacoesRepository() {
        if (Objects.isNull(operacoesRepository)) {
            Repositories repositories = Repositories.getInstance();
            operacoesRepository = repositories.getOperacoesRepository();
        }
        return operacoesRepository;
    }

    private PlantaRepository getPlantaRepository() {
        if (Objects.isNull(plantaRepository)) {
            Repositories repositories = Repositories.getInstance();
            plantaRepository = repositories.getPlantaRepository();
        }
        return plantaRepository;
    }

    private ParcelaRepository getParcelaRepository() {
        if (Objects.isNull(parcelaRepository)) {
            Repositories repositories = Repositories.getInstance();
            parcelaRepository = repositories.getParcelaRepository();
        }
        return parcelaRepository;
    }

    public void SementeiraRegister(int parcelaId, int plantaId, Date dataOperacao, Date dataFim, double quantidade, double quantidadeOp) throws SQLException {
        operacoesRepository.sementeiraRegister(parcelaId,plantaId,dataOperacao, dataFim, quantidade, quantidadeOp);
    }

    public List<Planta> getPlantas() throws SQLException {
        return plantaRepository.getPlanta();
    }

    public List<Parcela> getParcelas() throws SQLException {
        return parcelaRepository.getParcelas();
    }

    public List<Unidade> getUnidades() throws SQLException {
        return operacoesRepository.getUnidades();
    }

    public String getTipoCultura(int plantaId) throws SQLException{
        return plantaRepository.getTipoCultura(plantaId);
    }
}
