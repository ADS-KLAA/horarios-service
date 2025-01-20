package pt.iscte.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import pt.iscte.entities.Aula;
import pt.iscte.repositories.AulaRepository;
import pt.iscte.repositories.ProfessorRepository;

/**
 * AulaController
 */
@ApplicationScoped
public class AulaController {

    @Inject
    AulaRepository aulaRepository;

    @Inject
    ProfessorRepository professorRepository;

    @Transactional
    public Set<String> getCursos() {
        System.err.println("cursos called");
        return aulaRepository.listAll()
                .parallelStream()
                .map(a -> Arrays.asList(a.curso.split(", ")))
                .flatMap(List::stream)
                .collect(Collectors.toSet());
    }

    @Transactional
    public Set<String> getDisciplinasFromCurso(String curso) {
        return aulaRepository.find("curso like ?1", "%" + curso + "%")
                .list()
                .stream()
                .map(a -> a.uc)
                .collect(Collectors.toSet());
    }

    @Transactional
    public Set<String> getTurmasFromDiscipilnas(String disciplina) {
        return aulaRepository.find("uc like ?1", "%" + disciplina + "%")
                .list()
                .stream()
                .map(a -> Arrays.asList(a.turma.split(", ")))
                .flatMap(List::stream)
                .collect(Collectors.toSet());
    }

    @Transactional
    public Set<Aula> getProfessorAulas(String curso, String uc, String turma) {
        return aulaRepository.find("curso like ?1 and uc like ?2 and turma like ?3", "%" + curso + "%", "%" + uc + "%", "%" + turma + "%")
                .list()
                .stream()
                .collect(Collectors.toSet());
    }

    @Transactional
    public Set<Aula> getAlunoAulas(String turma) {
       return aulaRepository.find("turma like ?1", "%" + turma + "%")
               .list()
               .stream()
               .collect(Collectors.toSet());
    }
}
