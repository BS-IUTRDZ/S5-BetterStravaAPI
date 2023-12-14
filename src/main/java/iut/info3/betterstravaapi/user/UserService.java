package iut.info3.betterstravaapi.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Service associé à la gestion des utilisateurs.
 */
@Service
public class UserService {

    @Value("${SECRET_SENTENCE}")
    private String secretSentence;

    @Value("${TOKEN_EXPIRATION_DURATION}")
    private long tokenExpirationDuration;

    /**
     * Repository associé à la table utilisateur de la base MySQL.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Vérification que l'email passait en paramètre est présent en base.
     * @param email email à vérifier
     * @return true si l'email est présent, false sinon
     */
    public boolean checkPresenceEmail(final String email) {
        UserEntity user = userRepository.findByEmail(email);
        return user != null;
    }

    /**
     * recherche d'un utilisatur a partir se son mail et mdp.
     * @param email mail de l'utilisateur.
     * @param password mdp de l'utilisateur.
     * @return la list des utilisateur trouve.
     */
    public List<UserEntity> findByEmailAndPassword(final String email,
                                                   final String password) {
        return userRepository.findByEmailAndPassword(email,password);
    }

    /**
     * generation dun token jwt.
     * @param user l'utilisateur concerne par le jwt.
     * @param currentDate la dte au moment de la creation du token.
     * @return une string correspondant au token.
     */
    public String generateToken(final UserEntity user,
                                final Instant currentDate) {
        Algorithm algorithm = Algorithm.HMAC256(secretSentence);
        String jwt = JWT.create()
                .withClaim("id", user.getId())
                .withClaim("email", user.getEmail())
                .withClaim("datetime-claim", currentDate)
                .withExpiresAt(currentDate.plus(tokenExpirationDuration,
                                                ChronoUnit.SECONDS))
                .sign(algorithm);
        return jwt;
    }
}
