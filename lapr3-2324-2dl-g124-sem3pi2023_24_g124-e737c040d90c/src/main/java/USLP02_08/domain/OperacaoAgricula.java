package USLP02_08.domain;

import java.util.Date;

public class OperacaoAgricula {

    private Integer idOperacao;
    private Date dataOperacao;
    private Integer idParcela;
    private Integer idCultura;
    private Integer idTipoOperacao;
    private Double quantidade;
    private Integer idUnidade;


    public OperacaoAgricula(Integer idOperacao, Date dataOperacao, Integer idParcela, Integer idCultura, Integer idTipoOperacao, Double quantidade, Integer idUnidade) {
        this.idOperacao = idOperacao;
        this.dataOperacao = dataOperacao;
        this.idParcela = idParcela;
        this.idCultura = idCultura;
        this.idTipoOperacao = idTipoOperacao;
        this.quantidade = quantidade;
        this.idUnidade = idUnidade;
    }

    public Integer getIdOperacao() {
        return idOperacao;
    }

    public Date getDataOperacao() {
        return dataOperacao;
    }

    public Integer getIdParcela() {
        return idParcela;
    }

    public Integer getIdCultura() {
        return idCultura;
    }

    public Integer getIdTipoOperacao() {
        return idTipoOperacao;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public Integer getIdUnidade() {
        return idUnidade;
    }
}
