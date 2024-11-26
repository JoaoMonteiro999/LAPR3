package USLP02_08.domain;

import java.util.Objects;

public class FatorProducao implements Comparable<FatorProducao>{

    private Integer idFatorPr;
    private String designacaoFatorPr;

    public FatorProducao(Integer idFatorPr, String designacaoFatorPr) {
        this.idFatorPr = idFatorPr;
        this.designacaoFatorPr = designacaoFatorPr;
    }

    public Integer getIdFatorPr() {
        return idFatorPr;
    }

    public void setIdFatorPr(Integer idFatorPr) {
        this.idFatorPr = idFatorPr;
    }

    public String getDesignacaoFatorPr() {
        return designacaoFatorPr;
    }

    public void setDesignacaoFatorPr(String designacaoFatorPr) {
        this.designacaoFatorPr = designacaoFatorPr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FatorProducao that = (FatorProducao) o;

        if (!Objects.equals(idFatorPr, that.idFatorPr)) return false;
        return Objects.equals(designacaoFatorPr, that.designacaoFatorPr);
    }

    @Override
    public String toString() {
        return "ID: " + idFatorPr + ", DESIGNAÇÃO: " + designacaoFatorPr;
    }

    @Override
    public int compareTo(FatorProducao o) {
        return this.idFatorPr - o.getIdFatorPr();
    }
}
