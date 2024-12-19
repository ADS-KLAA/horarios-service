package pt.iscte.controllers;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pt.iscte.repositories.AlunoRepository;

/**
 * AlunoController
 */
@ApplicationScoped
public class AlunoController {

  @Inject
  AlunoRepository alunoRepository;

  public boolean verifyUsernameAndPassword(String email, String hashedPassword) {
    return alunoRepository.verifyUsernameAndPassword(email, hashedPassword); 
  }
  
}
