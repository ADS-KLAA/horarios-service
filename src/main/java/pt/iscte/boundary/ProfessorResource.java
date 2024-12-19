package pt.iscte.boundary;

import java.util.Map;
import java.util.Set;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.iscte.controllers.ProfessorController;
import pt.iscte.entities.Aula;

/**
 * ProfessorResource
 */
@Path("professor")
public class ProfessorResource {

  @Inject
  ProfessorController professorController;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @RolesAllowed("Professor")
  @Path("/professor")
  public Response getProfessorAulas() {
    Set<Aula> aulasProfessor = professorController.getProfessorAulas();
    return Response.ok().entity(Map.of("aulas", aulasProfessor)).build();

  }

}
