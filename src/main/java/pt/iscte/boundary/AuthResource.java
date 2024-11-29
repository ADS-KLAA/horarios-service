package pt.iscte.boundary;

import java.util.Map;
import java.util.Set;

import pt.iscte.controllers.AuthController;
import pt.iscte.helper.ErrorHelper;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/**
 * AuthResource
 * The purpose of this class is to handle any authentication related requests
 */
@Path("/auth")
public class AuthResource {

  @Inject
  AuthController authController;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/login/student")
  public Response loginAluno() {
    return Response.ok().build();
  }

  // TODO - Check if the user was created and send a server error if it hasn't
  /**
   * This endpoint is used for alunos to register their account
   * 
   * @param credentials - The request body
   * @return A success response stating the user was created
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/register/student")
  public Response registerAluno(Map<String, Object> credentials) {
    if (authController.verifyCredentials(credentials, Set.of("username, email, password", "turmas"))) {
      return Response.status(Status.BAD_REQUEST).entity(ErrorHelper.getErrorEntity("Something is missing")).build();
    }

    authController.registerAluno(credentials);

    return Response.ok().build();

  }

  /**
   * This endpoint is used for professsores to register their account
   * 
   * @param credentials - The request body
   * @return A success response stating the user was created
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/register/professor")
  public Response registerProfessor(Map<String, Object> credentials) {
    if (authController.verifyCredentials(credentials, Set.of("username, email, password"))) {
      return Response.status(Status.BAD_REQUEST).entity(ErrorHelper.getErrorEntity("Something is missing")).build();
    }

    authController.registarProfessor(credentials);

    return Response.ok().build();

  }

}
