package pt.iscte.controllers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import pt.iscte.entities.Sala;
import pt.iscte.repositories.SalaRepository;

import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class SalaController {

    @Inject
    SalaRepository salaRepository;

    @Transactional
    public Set<Sala> getSalas() {
        return salaRepository.findAll().stream().collect(Collectors.toSet());
    }
}