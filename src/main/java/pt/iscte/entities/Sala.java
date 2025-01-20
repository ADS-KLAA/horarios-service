package pt.iscte.entities;

import java.util.Arrays;
import java.util.UUID;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Sala entity, represents a classroom
 */
@Entity
@Table(name = "t_classroom")
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    public UUID id;

    @Column(name = "nome", nullable = false)
    public String nome;

    @Column(name = "capacidade", nullable = false)
    public int capacidade;

    @Column(name = "caracteristicas", nullable = false)
    public String caracteristicas;

    public Sala(String nome, int capacidade, String caracteristicas) {
        this.nome = nome;
        this.capacidade = capacidade;
        this.caracteristicas = caracteristicas;
    }

    public Sala() {

    }

    public List<String> getCaracteristicas() {
        return Arrays.asList(this.caracteristicas.split(","));
    }

    public static class Builder {
        private String nome;
        private int capacidade;
        private String caracteristicas;


        public Builder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder capacidade(int capacidade) {
            this.capacidade = capacidade;
            return this;
        }

        public Builder caracteristicas(String caracteristicas) {
            this.caracteristicas = caracteristicas;
            return this;
        }

        public Sala build() {
            return new Sala(nome, capacidade, caracteristicas);
        }
    }
}
