package iut.info3.betterstravaapi.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import iut.info3.betterstravaapi.EnvGetter;
import iut.info3.betterstravaapi.path.PathEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Service related to users functionalities.
 */
@Service
public class UserService {

    /**
     * Service associated to the environment variables.
     */
    @Autowired
    private EnvGetter envGetter;

    /**
     * Repository associated to the user table in the MySQL database.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Method to check if an email is already present in the database.
     * @param email email to check
     * @return true if the email is already present, false otherwise
     */
    public boolean emailExists(final String email) {
        UserEntity user = userRepository.findByEmail(email);
        return user != null;
    }

    /**
     * Method to get a user by its email and password.
     * @param email email of the user.
     * @param password password of the user.
     * @return the user found with this email and password or null if not found
     */
    public UserEntity findByEmailAndPassword(final String email,
                                                   final String password) {
        List<UserEntity> result = userRepository.findByEmailAndPassword(
                email, password);
        return result.size() == 1 ? result.get(0) : null;
    }

    /**
     * Method to generate a token for a user.
     * @param user the user for which the token is generated.
     * @param currentDate the current date.
     * @return the token generated
     */
    public String generateToken(final UserEntity user,
                                final Instant currentDate) {
        Algorithm algorithm = Algorithm.HMAC256(envGetter.getSentence());
        String jwt = JWT.create()
                .withClaim("id", user.getId())
                .withClaim("email", user.getEmail())
                .withClaim("datetime-claim", currentDate)
                .withExpiresAt(currentDate.plus(envGetter.getExpiration(),
                                                ChronoUnit.SECONDS))
                .sign(algorithm);

        return jwt;
    }

    /**
     * Method to get the token of a user by its id.
     * @param userId the id of the user.
     * @return the token of the user found with this id
     */
    public String getTokenBd(final Integer userId) {
        return userRepository.findTokenById(userId);
    }

    /**
     * Method to find a user by its token.
     * @param token the token of the user.
     * @return the user found with this token
     */
    public UserEntity findUserByToken(final String token) {
        return userRepository.findByToken(token);
    }

    /**
     * Method to check if a token is still valid.
     * @param jwtToken the token to check
     * @return true if the token is still valid, false otherwise
     */
    public boolean isTokenNotExpired(final String jwtToken) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(envGetter.getSentence());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(jwtToken);

            // Get the expiration date of the token
            Date dateExpiration = jwt.getExpiresAt();
            Date dateActuelle = new Date();

            return !dateExpiration.before(dateActuelle);

        } catch (JWTVerificationException e) {
            // Invalid / Expired JWT
            return false;
        }
    }

    /**
     * Method to calculate the statistics displayed on the home page.
     * @param pathList list of paths of the user
     * @return the statistics calculated
     */
    public HashMap<String, String> calculerPerformance(
            final List<PathEntity> pathList) {

        HashMap<String, String> map = new HashMap<>();

        float temps = 0;
        double distance = 0;
        for (PathEntity parcours : pathList) {
            if (parcours.getStatistiques() != null) {
                temps += parcours.getStatistiques().getDuree();
                distance += parcours.getStatistiques().getDistance();
            }
        }
        map.put("nombre_parcours", String.valueOf(pathList.size()));
        map.put("temps", String.valueOf(temps));
        map.put("distance", String.valueOf(distance));

        return map;

    }


}
