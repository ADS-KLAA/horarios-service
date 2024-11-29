package pt.iscte.controllers;

import java.util.Map;
import java.util.Set;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.sun.istack.NotNull;

import io.quarkus.logging.Log;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

/**
 * This controller is responsible to generatink tokens for user logins and new
 * registrations
 */
@ApplicationScoped
public class AuthController {

  @ConfigProperty(name = "mp.jwt.verify.issuer", defaultValue = "https://iscte-iul.pt")
  String issuer;

  public String generateNewToken(@NotNull String username, @NotNull Set<String> groups) {
    return Jwt
        .issuer(issuer)
        .subject(username)
        .upn(username)
        .groups(groups)
        .sign();
  }

  public boolean verifyCredentials(Map<String, Object> credentials, Set<String> properties) {
    return properties.stream().filter(property -> !credentials.values().contains(property)).toList().isEmpty();
  }

  @Transactional
  public void registerAluno(Map<String, Object> credentials) {
  }

  @Transactional
  public void registarProfessor(Map<String, Object> credentials) {
  }

}
