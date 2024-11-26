package USLP02_08.domain;

import java.util.Objects;

public class ModoDeAplicacaoFator implements Comparable<ModoDeAplicacaoFator> {

    private Integer idModo;

    private String modo;


    public ModoDeAplicacaoFator(Integer idModo, String modo) {
        this.idModo = idModo;
        this.modo = modo;
    }

    public Integer getIdModo() {
        return idModo;
    }

    public void setIdModo(Integer idModo) {
        this.idModo = idModo;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModoDeAplicacaoFator that = (ModoDeAplicacaoFator) o;

        return Objects.equals(modo, that.modo);
    }

    @Override
    public String toString() {
        return "MODO DE APLICAÇÂO: " + modo ;
    }


    @Override
    public int compareTo(ModoDeAplicacaoFator o) {
        return this.idModo - o.getIdModo();
    }
}
