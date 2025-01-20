package pt.iscte.boundary;

import java.util.Map;
import java.util.Set;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import pt.iscte.controllers.ProfessorController;
import pt.iscte.entities.Aula;
import pt.iscte.entities.Professor;

/**
 * ProfessorResource
 */
@Path("professor")
public class ProfessorResource {

    @Inject
    ProfessorController professorController;

    @Inject
    JsonWebToken jwt;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("Professor")
    @Path("/aulas")
    public Response getProfessorAulas() {
        Set<Aula> aulasProfessor = professorController.getProfessorAulas();
        return Response.ok().entity(Map.of("aulas", aulasProfessor)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("Professor")
    @Path("/register/aulas")
    public Response registerAula(Map<String, Object> requestBody) {
        return professorController.registerAula(requestBody.get("curso").toString(), requestBody.get("uc").toString(),
                requestBody.get("turma").toString());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("Professor")
    @Path("/session/info")
    public Response getSession() {
        Professor professor = professorController.getProfessorByEmail(jwt.getSubject());
        return Response.ok().entity(Map.of("email", professor.getEmail(), "name", professor.getName(), "aulas", professor.getAulas())).build();
    }

}
