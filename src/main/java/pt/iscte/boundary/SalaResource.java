package pt.iscte.boundary;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.iscte.controllers.SalaController;
import pt.iscte.entities.Sala;

import java.util.Map;
import java.util.Set;

/**
 * SalaResource
 * This Resourse is where the requests regarging salas end up
 */
@Path("/sala")
public class SalaResource {

    @Inject
    SalaController salaController;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSalas() {
        Set<Sala> salas = salaController.getSalas();
        return Response.ok().entity(Map.of("salas", salas)).build();
    }
}
