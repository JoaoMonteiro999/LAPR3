package USLP02_08.domain;

import java.util.Objects;

public class Setor {
    public String name;
    public String time;
    public String periodicity;

    public Setor(String name, String time, String periodicity) {
        this.name = name;
        this.time = time;
        this.periodicity = periodicity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Setor setor = (Setor) o;

        return Objects.equals(name, setor.name);
    }


}
