package pt.iscte.repositories;

import java.util.List;
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

    public Set<Aula> getProfessorAulas(String email) {
        return find("email", email).list().getFirst().aulas;
    }

    public boolean verifyUsernameAndPassword(String email, String hashedPassword) {
        return !find("email = ?1 and password = ?2", email, hashedPassword).list().isEmpty();
    }

    public boolean registerAula(String email, Set<Aula> aulas) {
        List<Professor> professorList = find("email", email).list();
        if (professorList.isEmpty()) {
          return false;
        }
        Professor professor = professorList.getFirst();
        professor.addAula(aulas);
        return true;
    }

}
