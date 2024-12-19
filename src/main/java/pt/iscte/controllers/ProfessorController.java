package pt.iscte.controllers;

import java.util.Set;

import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pt.iscte.entities.Aula;
import pt.iscte.repositories.ProfessorRepository;

/**
 * ProfessorController
 */
@ApplicationScoped
public class ProfessorController {

  @Inject
  JsonWebToken jwt;

  @Inject
  ProfessorRepository professorRepository;

  public Set<Aula> getProfessorAulas() {
    String username = jwt.getSubject();
    return professorRepository.getProfessorAulas(username);
  }

  public boolean verifyUsernameAndPassword(String email, String hashedPassword) {
    return professorRepository.verifyUsernameAndPassword(email, hashedPassword);
  }

}
