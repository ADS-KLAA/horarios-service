package pt.iscte.controllers;

import java.util.Set;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pt.iscte.entities.Aula;
import pt.iscte.entities.Professor;
import pt.iscte.repositories.AulaRepository;
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

    @Inject
    AulaController aulaController;

    public Set<Aula> getProfessorAulas() {
        String email = jwt.getSubject();
        return professorRepository.getProfessorAulas(email);
    }

    @Transactional
    public boolean verifyUsernameAndPassword(String email, String hashedPassword) {
        return professorRepository.verifyUsernameAndPassword(email, hashedPassword);
    }

    @Transactional
    public Response registerAula(String curso, String uc, String turma) {
        String email = jwt.getSubject();
        Set<Aula> aulas = aulaController.getProfessorAulas(curso, uc, turma);
        boolean result = professorRepository.registerAula(email, aulas);
        return result ? Response.ok().entity("Aula registered").build() : Response.serverError().entity("I have no idea what happened xD").build();
    }

    @Transactional
    public Professor getProfessorByEmail(String email) {
       return professorRepository.find("email", email).firstResult();
    }
}
