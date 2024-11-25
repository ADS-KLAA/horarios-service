package pt.iscte;

import java.util.Map;
import java.util.List;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.iscte.repositories.AulaRepository;
import pt.iscte.repositories.SalaRepository;
import pt.iscte.entities.Aula;
import pt.iscte.entities.Sala;
import pt.iscte.enums.DiaSemana;

@Path("/hello")
public class GreetingResource {

  @Inject
  SalaRepository salaRepository;

  @Inject
  AulaRepository aulaRepository;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response testGet() {
    List<Sala> salas = salaRepository.listAll();
    return Response.ok().entity(Map.of("salas", salas)).build();
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Transactional
  public Response testPost() {
    Sala sala = salaRepository.listAll().getFirst();
    Aula aula = new Aula.Builder()
        .curso("LEI")
        .uc("Calculo I")
        .turma("EI-A4")
        .inscritos(40)
        .diaSemana(DiaSemana.QUA)
        .inicio("08:00")
        .fim("09:30")
        .dia("12/10/2024")
        .sala(sala)
        .build();
    aulaRepository.persist(aula);
    return Response.ok().build();
  }
}
