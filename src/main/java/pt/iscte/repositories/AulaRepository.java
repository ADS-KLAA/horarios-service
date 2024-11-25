package pt.iscte.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import pt.iscte.entities.*;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * AulaRepository 
 */
@ApplicationScoped
public class AulaRepository implements PanacheRepository<Aula>{

  
}
