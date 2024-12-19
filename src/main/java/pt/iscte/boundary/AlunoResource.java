package pt.iscte.boundary;

import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import pt.iscte.controllers.AlunoController;

@Path("/aluno")
public class AlunoResource {

  @Inject
  AlunoController alunoController;

}
