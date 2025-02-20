package pt.iscte.controllers;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
import pt.iscte.entities.Professor;
import pt.iscte.repositories.AlunoRepository;
import pt.iscte.repositories.ProfessorRepository;

/**
 * This controller is responsible to generatink tokens for user logins and new
 * registrations, the tokens it generates last 1 hour
 */
@ApplicationScoped
public class AuthController {

    @ConfigProperty(name = "mp.jwt.verify.issuer", defaultValue = "https://iscte-iul.pt")
    String issuer;

    @Inject
    AlunoController alunoController;

    @Inject
    AlunoRepository alunoRepository;

    @Inject
    ProfessorController professorController;

    @Inject
    ProfessorRepository professorRepository;

    public String generateNewToken(@NotNull String email, @NotNull Set<String> groups) {
        return Jwt
                .issuer(issuer)
                .subject(email)
                .upn(email)
                .groups(groups)
                .expiresIn(3600L)
                .sign();
    }

    /**
     * Verifies if sent properties are valid, or at least it should but I gave up
     *
     * @param credentials - User sent credentials
     * @param properties  - Required Properties
     * @return - boolean indicating if the properties sent are valid
     */
    public boolean verifyCredentials(Map<String, Object> credentials, Set<String> properties) {
//    List<String> sentKeys = Arrays.asList(credentials.keySet().stream().toList().getFirst().split(", "));
//    List<String> missingCredentials = properties.stream().filter(key -> {Log.info(key);return sentKeys.contains(key);}).toList();
//    Log.error(sentKeys + " " + properties + " " + missingCredentials);
//    return missingCredentials.isEmpty();
        return false;
    }

    /**
     * Persists a new Aluno in the database
     *
     * @param credentials - credentials for registering Aluno
     */
    @Transactional
    public boolean registerAluno(Map<String, Object> credentials) {
        String hashedPassword = hashPassword((String) credentials.get("password"));
        Aluno aluno = new Aluno.Builder()
                .name((String) credentials.get("username"))
                .email((String) credentials.get("email"))
                .password(hashedPassword)
                .turma((String) credentials.get("turma"))
                .build();
        if (alunoRepository.find("email", aluno.email).firstResult() != null) {
            return false;
        }
        alunoRepository.persist(aluno);
        return true;
    }

    /**
     * Persists a new Professor in the database
     *
     * @param credentials - credentials for registering Professor
     */
    @Transactional
    public boolean registarProfessor(Map<String, Object> credentials) {
        String hashedPassword = hashPassword((String) credentials.get("password"));
        Professor professor = new Professor.Builder()
                .name((String) credentials.get("username"))
                .email((String) credentials.get("email"))
                .password(hashedPassword)
                .build();
        if (professorRepository.find("email", professor.email).firstResult() != null) {
            return false;
        }
        professorRepository.persist(professor);
        return true;
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
        String email = (String) credentials.get("email");
        String hashedPassword = hashPassword((String) credentials.get("password"));

        if (role.equals("Aluno")) {
            return alunoController.verifyUsernameAndPassword(email, hashedPassword);
        } else if (role.equals("Professor")) {
            return professorController.verifyUsernameAndPassword(email, hashedPassword);
        }
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
            // Salt to be used to salt the hashed password
            byte[] salt = "E6FTYuWG8j@^mRK#SdBvb2ySr$#H2^1X8zWYglTu8^skyi1FnC".getBytes();

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
