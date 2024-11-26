package USLP02_08.domain;

public class Fertirrega {

    public String name;
    public String time;
    public String periodicity;
    public String mix;
    public String recurrence;


    public Fertirrega(String name, String time, String periodicity, String mix, String recurrence) {
        this.name = name;
        this.time = time;
        this.periodicity = periodicity;
        this.mix = mix;
        this.recurrence = recurrence;
    }
}
