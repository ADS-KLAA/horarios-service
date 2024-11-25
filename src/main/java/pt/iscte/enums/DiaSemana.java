package pt.iscte.enums;

import java.util.Arrays;
import java.util.List;

public enum DiaSemana {
    SEG("SEG"),
    TER("TER"),
    QUA("QUA"),
    QUI("QUI"),
    SEX("SEX"),
    SAB("SAB"),
    DOM("DOM");

    private String name;

    DiaSemana(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
      return this.name;
    }
 
    public List<DiaSemana> getValues(){
      return Arrays.asList(DiaSemana.values()); 
    }
}

