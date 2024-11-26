package USLP02_08.controller;

import USLP02_08.dataAccess.CulturaRepository;
import USLP02_08.dataAccess.OperacoesRepository;
import USLP02_08.dataAccess.ParcelaRepository;
import USLP02_08.dataAccess.Repositories;
import USLP02_08.domain.Cultura;
import USLP02_08.domain.Parcela;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class PodaRegisterController {
    private OperacoesRepository operacoesRepository;
    private ParcelaRepository parcelaRepository;
    private CulturaRepository culturaRepository;

    public PodaRegisterController(){
        getParcelaRepository();
        getCulturaRepository();
        getOperacoesRepository();
    }

    private ParcelaRepository getParcelaRepository() {
        if (Objects.isNull(parcelaRepository)) {
            Repositories repositories = Repositories.getInstance();
            parcelaRepository = repositories.getParcelaRepository();
        }
        return parcelaRepository;
    }

    private CulturaRepository getCulturaRepository() {
        if (Objects.isNull(culturaRepository)) {
            Repositories repositories = Repositories.getInstance();
            culturaRepository = repositories.getCulturaRepository();
        }
        return culturaRepository;
    }

    private OperacoesRepository getOperacoesRepository() {
        if (Objects.isNull(operacoesRepository)) {
            Repositories repositories = Repositories.getInstance();
            operacoesRepository = repositories.getOperacoesRepository();
        }
        return operacoesRepository;
    }

    public List<Parcela> getParcelas() throws SQLException { return parcelaRepository.getParcelas(); }

    public List<Cultura> getCulturasPermanantes(int parcelaId) throws SQLException { return culturaRepository.getCulturasPermanantes(parcelaId); }

    public void PodaRegister(int parcelaId, int idCultura, Date dataOperacao, double quantidade) throws SQLException {
        operacoesRepository.PodaRegister(parcelaId, idCultura, dataOperacao, quantidade);
    }
}
