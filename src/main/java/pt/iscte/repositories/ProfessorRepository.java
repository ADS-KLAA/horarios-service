package pt.iscte.repositories;

import java.util.Set;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import pt.iscte.entities.Aula;
import pt.iscte.entities.Professor;

/**
 * ProfessorRepository
 */
@ApplicationScoped
public class ProfessorRepository implements PanacheRepository<Professor> {

  public Set<Aula> getProfessorAulas(String username) {
    return find("name", username).list().getFirst().aulas;
  }

  public boolean verifyUsernameAndPassword(String email, String hashedPassword) {
    return !find("email = ?1 and password = ?2", email, hashedPassword).list().isEmpty();
  }

}
