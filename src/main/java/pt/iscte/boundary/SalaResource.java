package pt.iscte.boundary;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * SalaResource
 * This Resourse is where the requests regarging salas end up
 */
@Path("/sala")
public class SalaResource {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getSalas() {
    return Response.ok().build();
  }

}
