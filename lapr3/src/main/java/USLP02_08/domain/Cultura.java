package USLP02_08.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Cultura implements Comparable<Cultura> {
    private Integer idCultura;
    private Integer idPlanta;
    private Integer idParcela;
    private Date dataInicio;
    private Date dataFim;
    private Double area;
    private String nomePlanta;
    private String variedade;
    private Integer idUnidade;

    /**
     * Construtor para operações sem data de fim
     * @param idParcela
     * @param idCultura
     * @param dataInicio
     * @param area
     */
    public Cultura(Integer idParcela,Integer idCultura, Date dataInicio, Double area) {
        this.idParcela = idParcela;
        this.idCultura = idCultura;
        this.dataInicio = dataInicio;
        this.area = area;
    }

    public Cultura(Integer idCultura, Date dataInicio, Date dataFim, String nomePlanta, String variedade, double area){
        this.idCultura = idCultura;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.nomePlanta = nomePlanta;
        this.variedade = variedade;
        this.area = area;
    }
    public Integer getIdParcela() {
        return idParcela;
    }

    public Integer getIdCultura() {
        return idCultura;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public Double getArea() {
        return area;
    }

    @Override
    public int compareTo(Cultura o) {
        return this.idCultura - o.getIdCultura();
    }

    @Override
    public String toString() {
        if (dataFim == null)
            dataFim = new Date();

        String formattedDataInicio = dataInicio != null ? new SimpleDateFormat("dd/MM/yyyy").format(dataInicio) : "N/A";
        String formattedDataFim = new SimpleDateFormat("dd/MM/yyyy").format(dataFim);

        return "ID: " + idCultura + ", Data de Inicio: " + formattedDataInicio  +  ", Data de Fim: " + formattedDataFim +
                ", Planta: " + nomePlanta + ", Variedade: " + variedade;
    }
}
