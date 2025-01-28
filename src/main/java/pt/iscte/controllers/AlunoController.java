package pt.iscte.controllers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.jwt.JsonWebToken;
import pt.iscte.entities.Aluno;
import pt.iscte.entities.Aula;
import pt.iscte.repositories.AlunoRepository;
import pt.iscte.repositories.AulaRepository;

import java.util.Set;
import java.util.UUID;

/**
 * AlunoController
 */
@ApplicationScoped
public class AlunoController {

    @Inject
    AlunoRepository alunoRepository;

    @Inject
    AulaController aulaController;

    @Inject
    AulaRepository aulaRepository;

    @Inject
    JsonWebToken jwt;

    @Transactional
    public boolean verifyUsernameAndPassword(
            String email,
            String hashedPassword
    ) {
        return alunoRepository.verifyUsernameAndPassword(email, hashedPassword);
    }

    @Transactional
    public Set<Aula> getAlunoAulas() {
        String username = jwt.getSubject();
        if (username.isEmpty()) {
            return null;
        }
        String turma = alunoRepository.getAlunoTurma(username);
        return aulaController.getAlunoAulas(turma);
    }

    @Transactional
    public Aluno getAlunoByEmail(String email) {
        return alunoRepository.find("email", email).firstResult();
    }

    @Transactional
    public boolean confirmAula(String aulaId) {
        String username = jwt.getSubject();
        Aluno aluno = alunoRepository.find("email", username).firstResult();
        Aula aula = aulaRepository.find("id", UUID.fromString(aulaId)).firstResult();
        if (aula == null) {
            return false;
        }
        aula.addConfirmation(aluno);
        return true;
    }

    @Transactional
    public boolean attendAula(String aulaId) {
        String username = jwt.getSubject();
        Aluno aluno = alunoRepository.find("email", username).firstResult();
        Aula aula = aulaRepository.find("id", UUID.fromString(aulaId)).firstResult();
        if (aula == null) {
            return false;
        }
        aula.addPresenca(aluno);
        return true;
    }
}
