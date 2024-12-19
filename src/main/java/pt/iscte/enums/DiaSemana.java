package pt.iscte.enums;

import java.util.Arrays;
import java.util.List;

public enum DiaSemana {
    Seg("Seg"),
    Ter("Ter"),
    Qua("Qua"),
    Qui("Qui"),
    Sex("Sex"),
    Sáb("Sáb"),
    Dom("Dom");

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

