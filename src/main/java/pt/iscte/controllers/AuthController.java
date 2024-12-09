package pt.iscte.controllers;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;
import java.util.Set;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.sun.istack.NotNull;

import io.quarkus.logging.Log;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import pt.iscte.entities.Aluno;
import pt.iscte.repositories.AlunoRepository;

/**
 * This controller is responsible to generatink tokens for user logins and new
 * registrations, the tokens it generates last 1 hour
 */
@ApplicationScoped
public class AuthController {

  @ConfigProperty(name = "mp.jwt.verify.issuer", defaultValue = "https://iscte-iul.pt")
  String issuer;

  @Inject
  AlunoRepository alunoRepository;

  public String generateNewToken(@NotNull String username, @NotNull Set<String> groups) {
    return Jwt
        .issuer(issuer)
        .subject(username)
        .upn(username)
        .groups(groups)
        .expiresIn(3600L)
        .sign();
  }

  /**
   * Verifies if sent properties are valid
   * 
   * @param credentials - User sent credentials
   * @param properties  - Required Properties
   * @return - boolean indicating if the properties sent are valid
   */
  public boolean verifyCredentials(Map<String, Object> credentials, Set<String> properties) {
    return properties.stream().filter(property -> !credentials.values().contains(property)).toList().isEmpty();
  }

  /**
   * Persists a new Aluno in the database
   * 
   * @param credentials - credentials for registering Aluno
   */
  @Transactional
  public void registerAluno(Map<String, Object> credentials) {
    String hashedPassword = hashPassword((String) credentials.get("password"));
    Aluno aluno = new Aluno.Builder()
        .name((String) credentials.get("username"))
        .email((String) credentials.get("email"))
        .password(hashedPassword)
        .turma((String) credentials.get("turmas"))
        .build();

    alunoRepository.persist(aluno);
  }

  /**
   * Persists a new Professor in the database
   * 
   * @param credentials - credentials for registering Professor
   */
  @Transactional
  public void registarProfessor(Map<String, Object> credentials) {
  }

  /**
   * Checks if the sent credentials exist in the database
   * 
   * @param credentials - credentials sent by the user
   * @param role        - role of the request to identify which table to look at
   * @return - boolean indicating if the user exists
   */
  @Transactional
  public boolean verifyUsernameAndPassword(Map<String, Object> credentials, String role) {
    return false;
  }

  /**
   * Generates a salted and hashed representation of the given password using
   * PBKDF2 with HMAC-SHA256.
   * The resulting hash includes the salt and is encoded as a Base64 string.
   * 
   * <p>
   * The method uses a random 16-byte salt and performs 65,536 iterations to
   * derive a secure hash.
   * </p>
   * 
   * @param password the plain-text password to be hashed. Must not be null or
   *                 empty.
   * @return a Base64-encoded string containing the salt and hashed password.
   * @throws RuntimeException if an error occurs during the hashing process.
   */
  public static String hashPassword(String password) {
    try {
      // Generate a random salt
      SecureRandom random = new SecureRandom();
      byte[] salt = new byte[16];
      random.nextBytes(salt);

      // Hash the password with PBKDF2
      PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
      SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
      byte[] hash = factory.generateSecret(spec).getEncoded();

      // Combine salt and hash
      byte[] hashAndSalt = new byte[salt.length + hash.length];
      System.arraycopy(salt, 0, hashAndSalt, 0, salt.length);
      System.arraycopy(hash, 0, hashAndSalt, salt.length, hash.length);

      // Encode the result as a Base64 string
      return Base64.getEncoder().encodeToString(hashAndSalt);
    } catch (Exception e) {
      // Handle exceptions internally and return a fallback value or throw a runtime
      // exception
      Log.error("Error while hashing password");
      throw new RuntimeException("Error while hashing password", e);
    }
  }
}
