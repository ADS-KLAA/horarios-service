package pt.iscte.entities;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;
import pt.iscte.enums.DiaSemana;

/**
 * Aula entity, represents a class
 * Has the following fields:
 * - id
 * - curso
 * - uc
 * - turma
 * - inscritos
 * - confirmados
 * - diaSemana
 * - inicio
 * - fim
 * - dia
 * - {@link Sala}
 */
@Entity
@Table(name = "t_class")
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    public UUID id;

    @Column(name = "curso", nullable = false)
    public String curso;

    @Column(name = "uc", nullable = false)
    public String uc;

    @Column(name = "turma", nullable = false)
    public String turma;

    @Column(name = "inscritos", nullable = false)
    public int inscritos;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "t_student_class",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "class_id")
    )
    public Set<Aluno> confirmados;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana", nullable = false)
    public DiaSemana diaSemana;

    @Column(name = "inicio", nullable = false)
    public String inicio;

    @Column(name = "fim", nullable = false)
    public String fim;

    @Column(name = "dia", nullable = false)
    public String dia;

    @JoinColumn(name = "sala", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    public Sala sala;

    public Aula(String curso, String uc, String turma, int inscritos, DiaSemana diaSemana, String inicio, String fim, String dia, Sala sala) {
        this.curso = curso;
        this.uc = uc;
        this.turma = turma;
        this.inscritos = inscritos;
        this.diaSemana = diaSemana;
        this.inicio = inicio;
        this.fim = fim;
        this.dia = dia;
        this.sala = sala;
    }

    public Aula() {

    }

    public void addConfirmation(Aluno aluno) {
        this.confirmados.add(aluno);
    }

    public static class Builder {
        private String curso;
        private String uc;
        private String turma;
        private int inscritos;
        private DiaSemana diaSemana;
        private String inicio;
        private String fim;
        private String dia;
        private Sala sala;

        public Builder curso(String curso) {
            this.curso = curso;
            return this;
        }

        public Builder uc(String uc) {
            this.uc = uc;
            return this;
        }

        public Builder turma(String turma) {
            this.turma = turma;
            return this;
        }

        public Builder inscritos(int inscritos) {
            this.inscritos = inscritos;
            return this;
        }

        public Builder diaSemana(DiaSemana diaSemana) {
            this.diaSemana = diaSemana;
            return this;
        }

        public Builder inicio(String inicio) {
            this.inicio = inicio;
            return this;
        }

        public Builder fim(String fim) {
            this.fim = fim;
            return this;
        }

        public Builder dia(String dia) {
            this.dia = dia;
            return this;
        }

        public Builder sala(Sala sala) {
            this.sala = sala;
            return this;
        }

        public Aula build() {
            return new Aula(curso, uc, turma, inscritos, diaSemana, inicio, fim, dia, sala);
        }

    }

}
