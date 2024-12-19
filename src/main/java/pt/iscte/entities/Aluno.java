package pt.iscte.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Aluno entity, represents students
 */
@Entity
@Table(name = "t_student")
public class Aluno {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  public UUID id;

  @Column(name = "name", nullable = false)
  public String name;

  @Column(name = "email", nullable = false)
  public String email;

  @Column(name = "password", nullable = false)
  public String password;

  @Column(name = "turma", nullable = false)
  public String turma;

  public Aluno(String name, String email, String password, String turma) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.turma = turma;
  }

  public Aluno() {

  }

  public static class Builder {
    public String name;
    public String email;
    public String password;
    public String turma;

    public Builder name(String name){
      this.name = name;
      return this;
    }

    public Builder email(String email) {
      this.email = email;
      return this;
    }

    public Builder password(String password) {
      this.password = password;
      return this;
    }

    public Builder turma(String turma) {
      this.turma = turma;
      return this;
    }

    public Aluno build() {
      return new Aluno(name, email, password, turma);
    }
  }
}
