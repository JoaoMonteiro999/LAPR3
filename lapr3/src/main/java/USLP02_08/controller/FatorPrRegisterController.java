package USLP02_08.controller;

import USLP02_08.dataAccess.*;
import USLP02_08.domain.Cultura;
import USLP02_08.domain.FatorProducao;
import USLP02_08.domain.ModoDeAplicacaoFator;
import USLP02_08.domain.Parcela;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class FatorPrRegisterController {



    private OperacoesRepository operacoesRepository;
    private ParcelaRepository parcelaRepository;
    private CulturaRepository culturaRepository;
    private FatorRepository fatorRepository;
    private ModosRepository modosRepository;

    public FatorPrRegisterController(){
        getParcelaRepository();
        getCulturaRepository();
        getOperacoesRepository();
        getFatorRepository();
        getModosRepository();
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

    private FatorRepository getFatorRepository() {
        if (Objects.isNull(fatorRepository)) {
            Repositories repositories = Repositories.getInstance();
            fatorRepository = repositories.getFatorRepository();
        }
        return fatorRepository;
    }

    private ModosRepository getModosRepository() {
        if (Objects.isNull(modosRepository)) {
            Repositories repositories = Repositories.getInstance();
            modosRepository = repositories.getModosRepository();
        }
        return modosRepository;
    }

    public List<Parcela> getParcelas() throws SQLException { return parcelaRepository.getParcelas(); }

    public List<Cultura> getCulturas(int parcelaId) throws SQLException { return culturaRepository.getCulturas(parcelaId); }

    public List<FatorProducao> getFatorProducao() throws SQLException{ return fatorRepository.getFatorProducao();  }

    public List<ModoDeAplicacaoFator> getModos() throws SQLException { return modosRepository.getModos(); }






    public void FatorPrRegister(int parcelaId, int idCultura, Date dataOperacao,String FatorProducao,String Modo, double area,double quantidade) throws SQLException {
        operacoesRepository.FatorPrRegister(parcelaId, dataOperacao,FatorProducao,Modo, area,quantidade,idCultura);
    }
}
