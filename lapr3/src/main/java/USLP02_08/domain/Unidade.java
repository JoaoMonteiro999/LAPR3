package USLP02_08.domain;

public class Unidade implements Comparable<Unidade> {

    private Integer idUnidade;
    private String unidade;

    public Unidade(Integer idUnidade, String unidade) {
        this.idUnidade = idUnidade;
        this.unidade = unidade;
    }

    public Integer getIdUnidade() {
        return idUnidade;
    }

    public String getUnidade() {
        return unidade;
    }

    @Override
    public int compareTo(Unidade o) {
        return this.idUnidade - o.getIdUnidade();
    }

    @Override
    public String toString() {
        return "ID: " + idUnidade + ", UNIDADE: " + unidade;
    }
}
