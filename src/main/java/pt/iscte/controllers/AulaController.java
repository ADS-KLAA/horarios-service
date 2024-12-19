package pt.iscte.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
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

  public Set<String> getDisciplinasFromCurso(String curso) {
    return aulaRepository.find("curso like ?1", "%" + curso + "%")
        .list()
        .stream()
        .map(a -> a.uc)
        .collect(Collectors.toSet());
  }

  public Set<String> getCursos() {
    System.err.println("cursos called");
    return aulaRepository.listAll()
        .parallelStream()
        .map(a -> Arrays.asList(a.curso.split(", ")))
        .flatMap(List::stream)
        .collect(Collectors.toSet());
  }
}
