package USLP02_08.domain;

public class Parcela implements Comparable<Parcela> {

    private Integer idParcela;
    private String designacao;
    private Double area;

    public Parcela(Integer idParcela, String designacao, Double area) {
        this.idParcela = idParcela;
        this.designacao = designacao;
        this.area = area;
    }

    public Integer getIdParcela() {
        return idParcela;
    }

    public String getDesignacao() {
        return designacao;
    }

    public Double getArea() {
        return area;
    }

    @Override
    public int compareTo(Parcela o) {
        return this.idParcela - o.getIdParcela();
    }

    @Override
    public String toString() {
        return "ID: " + idParcela + ", DESIGNAÇÃO: " + designacao + '\'' + ", AREA: " + area;
    }
}
