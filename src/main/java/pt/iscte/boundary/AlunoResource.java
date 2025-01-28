package pt.iscte.boundary;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import pt.iscte.controllers.AlunoController;
import pt.iscte.entities.Aluno;
import pt.iscte.entities.Aula;

import java.util.Map;
import java.util.Set;

@Path("/aluno")
public class AlunoResource {

    @Inject
    AlunoController alunoController;

    @Inject
    JsonWebToken jwt;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("Aluno")
    @Path("/aulas")
    public Response getAlunoAulas() {
        Set<Aula> aulasAluno = alunoController.getAlunoAulas();
        if (aulasAluno == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(Map.of("error", "Aluno não encontrado")).build();
        }
        return Response.ok().entity(Map.of("aulas", aulasAluno)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("Aluno")
    @Path("/session/info")
    public Response getSession() {
        Aluno aluno = alunoController.getAlunoByEmail(jwt.getSubject());
        return Response.ok().entity(Map.of("email", aluno.getEmail(), "name", aluno.getName())).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("Aluno")
    @Path("/confirmar/{aulaId}")
    public Response confirmAula(@PathParam("aulaId") String aulaId) {
        boolean success = alunoController.confirmAula(aulaId);
        if (!success) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Aula doesn't exist").build();
        }
        return Response.ok().entity("Aula confirmed").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("Aluno")
    @Path("/attend/{aulaId}")
    public Response attendAula(@PathParam("aulaId") String aulaId) {
        boolean success = alunoController.attendAula(aulaId);
        if (!success) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Aula doesn't exist").build();
        }
        return Response.ok().entity("Aula marked as attended").build();
    }

}
