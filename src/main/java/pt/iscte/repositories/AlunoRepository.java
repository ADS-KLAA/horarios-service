package pt.iscte.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import pt.iscte.entities.*;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * AulaRepository 
 */
@ApplicationScoped
public class AlunoRepository implements PanacheRepository<Aluno>{

	public boolean verifyUsernameAndPassword(String email, String hashedPassword) {
    return !find("email = ?1 and password = ?2", email, hashedPassword).list().isEmpty();
	}

  
}
