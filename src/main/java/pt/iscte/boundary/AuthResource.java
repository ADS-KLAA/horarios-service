package pt.iscte.boundary;

import java.util.Map;
import java.util.Set;

import org.eclipse.microprofile.jwt.JsonWebToken;

import io.quarkus.logging.Log;
import pt.iscte.controllers.AuthController;
import pt.iscte.enums.Roles;
import pt.iscte.helper.ErrorHelper;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
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

  @Inject
  JsonWebToken jwt;

  /**
   * This endpoint is used for alunos to register their account
   * 
   * @param credentials - The request body with {username, email, password, turma}
   * @return A success response with the access token
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/register/aluno")
  public Response registerAluno(Map<String, Object> credentials) {
    if (authController.verifyCredentials(credentials, Set.of("username, email, password", "turma"))) {
      Log.error("Some credential is missing");
      return Response.status(Status.BAD_REQUEST).entity(ErrorHelper.getErrorEntity("Something is missing")).build();
    }

    authController.registerAluno(credentials);

    String token = authController.generateNewToken((String) credentials.get("email"), Set.of(Roles.ALUNO.name()));

    return Response.ok()
        .entity(Map.of("token", token, "expiresIn", 3600))
        .build();
  }

  /**
   * This endpoint is used for professsores to register their account
   * 
   * @param credentials - The request body with {username, email, password}
   * @return A success response with the access token
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/register/professor")
  public Response registerProfessor(Map<String, Object> credentials) {
    Log.info("/auth/register/professor called");
    if (authController.verifyCredentials(credentials, Set.of("username, email, password"))) {
      Log.error("Some credential is missing");
      return Response.status(Status.BAD_REQUEST).entity(ErrorHelper.getErrorEntity("Something is missing")).build();
    }

    authController.registarProfessor(credentials);

    String token = authController.generateNewToken((String) credentials.get("email"), Set.of(Roles.PROFESSOR.name()));

    return Response.ok()
        .entity(Map.of("token", token, "expiresIn", 3600))
        .build();
  }

  /**
   * This endpoint is used for professsores to login in their account
   * 
   * @param credentials - The request body
   * @return A success response with the access token if login was successful
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/login/professor")
  public Response loginProfessor(Map<String, Object> credentials) {
    if (authController.verifyCredentials(credentials, Set.of("email, password"))) {
      return Response.status(Status.BAD_REQUEST).entity(ErrorHelper.getErrorEntity("Something is missing")).build();
    }

    if (!authController.verifyUsernameAndPassword(credentials, Roles.PROFESSOR.name())) {
      return Response.status(Status.FORBIDDEN).entity(ErrorHelper.getErrorEntity("Wrong username or password")).build();
    }

    String token = authController.generateNewToken((String) credentials.get("email"), Set.of(Roles.PROFESSOR.name()));

    return Response.ok()
        .entity(Map.of("token", token, "expiresIn", 3600))
        .build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/login/aluno")
  public Response loginAluno(Map<String, Object> credentials) {
    if (authController.verifyCredentials(credentials, Set.of("email, password"))) {
      return Response.status(Status.BAD_REQUEST).entity(ErrorHelper.getErrorEntity("Something is missing")).build();
    }

    if (!authController.verifyUsernameAndPassword(credentials, Roles.ALUNO.name())) {
      return Response.status(Status.FORBIDDEN).entity(ErrorHelper.getErrorEntity("Wrong username or password")).build();
    }

    String token = authController.generateNewToken((String) credentials.get("email"), Set.of(Roles.ALUNO.name()));

    return Response.ok()
        .entity(Map.of("token", token, "expiresIn", 3600))
        .build();
  }
}
