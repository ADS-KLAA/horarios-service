package pt.iscte.boundary;

import java.util.Set;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.iscte.controllers.AulaController;

/**
 * AulaResource
 */
@Path("aula")
public class AulaResource {

    @Inject
    AulaController aulaController;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/cursos")
    public Response getCursos() {
        Set<String> cursos = aulaController.getCursos();
        return Response.ok().entity(Map.of("cursos", cursos)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/disciplinas")
    public Response getDisciplinasFromCurso(@QueryParam("curso") String curso) {
        Set<String> disciplinas = aulaController.getDisciplinasFromCurso(curso);
        return Response.ok().entity(Map.of("disciplinas", disciplinas)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/turmas")
    public Response getTurmas(@QueryParam("disciplina") String disciplina) {
        Set<String> turmas = aulaController.getTurmasFromDiscipilnas(disciplina);
        return Response.ok().entity(Map.of("turmas", turmas)).build();
    }

}
