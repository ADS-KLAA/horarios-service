package pt.iscte.entities;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

/**
 * Professor
 */
@Entity
@Table(name = "t_teacher")
public class Professor {

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

  @ManyToMany
  @JoinTable(
    name = "t_teacher_class", 
    joinColumns = @JoinColumn(name = "teacher_id"), 
    inverseJoinColumns = @JoinColumn(name = "class_id")
  )
  public Set<Aula> aulas;

  public Professor(String name, String email, String password) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.aulas = Set.of();
  }

  public Professor() {

  }

  public static class Builder {
    public String name;
    public String email;
    public String password;

    public Builder name(String name) {
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

    public Professor build() {
      return new Professor(name, email, password);
    }
  }

}
