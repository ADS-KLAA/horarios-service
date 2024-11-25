package pt.iscte.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import pt.iscte.entities.*;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Sala Repository 
 */
@ApplicationScoped
public class SalaRepository implements PanacheRepository<Sala>{

  
}
