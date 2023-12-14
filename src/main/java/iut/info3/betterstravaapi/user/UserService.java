package iut.info3.betterstravaapi.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
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

    public List<UserEntity> findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email,password);
    }

    public String generateToken(UserEntity user, Instant currentDate) {
        Algorithm algorithm = Algorithm.HMAC256(secretSentence);
        String jwt = JWT.create()
                .withClaim("id", user.getId() )
                .withClaim("email", user.getEmail())
                .withClaim("datetime-claim", currentDate)
                .withExpiresAt(currentDate.plus(tokenExpirationDuration, ChronoUnit.SECONDS))
                .sign(algorithm);
        return jwt;
    }
}
