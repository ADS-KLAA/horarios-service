package pt.iscte.enums;

import java.util.Arrays;
import java.util.List;

public enum Roles {
    PROFESSOR("Professor"), ALUNO("Aluno");

    private final String name;

    Roles(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public List<Roles> getValues() {
        return Arrays.asList(Roles.values());
    }
}

