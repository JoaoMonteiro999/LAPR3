package USLP02_08.domain;

public class Planta implements Comparable<Planta> {

    private Integer idPlanta;
    private String nome;
    private String variedade;
    private String tipoCultura;

    public Planta(Integer idPlanta, String nome, String variedade, String tipoCultura) {
        this.idPlanta = idPlanta;
        this.nome = nome;
        this.variedade = variedade;
        this.tipoCultura = tipoCultura;
    }

    public Integer getIdPlanta() {
        return idPlanta;
    }

    public String getNome() {
        return nome;
    }

    public String getVariedade() {
        return variedade;
    }

    public String getTipoCultura() {
        return tipoCultura;
    }

    @Override
    public int compareTo(Planta o) {
        return this.idPlanta - o.idPlanta;
    }

    @Override
    public String toString() {
        return "ID: " + idPlanta + ", NOME: " + nome + " " + variedade + ", TIPO DE PLANTAÇÂO: " + printTipoDePlantacao(tipoCultura);
    }

    public String printTipoDePlantacao(String string){
        if (string.equals("1"))
            return "Temporária";
        return "Permanente";
    }
}


