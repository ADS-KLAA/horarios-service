package pt.iscte.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import pt.iscte.entities.*;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Set;

/**
 * AulaRepository 
 */
@ApplicationScoped
public class AlunoRepository implements PanacheRepository<Aluno>{

	public boolean verifyUsernameAndPassword(String email, String hashedPassword) {
    return !find("email = ?1 and password = ?2", email, hashedPassword).list().isEmpty();
	}

	public String getAlunoTurma(String username) {
        return find("email", username).firstResult().turma;
	}
  
}
