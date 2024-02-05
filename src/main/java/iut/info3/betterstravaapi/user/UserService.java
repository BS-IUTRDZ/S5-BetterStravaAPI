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
 * Service associé à la gestion des utilisateurs.
 */
@Service
public class UserService {

    /**
     * Service associé a la recuperation des variables d'environement.
      */
    @Autowired
    private EnvGetter envGetter;

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
    public UserEntity findByEmailAndPassword(final String email,
                                                   final String password) {
        List<UserEntity> result = userRepository.findByEmailAndPassword(
                email, password);
        return result.size() == 1 ? result.get(0) : null;
    }

    /**
     * generation dun token jwt.
     * @param user l'utilisateur concerne par le jwt.
     * @param currentDate la dte au moment de la creation du token.
     * @return une string correspondant au token.
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
     * recuperation du token d'un utilisateur.
     * @param idUser id de l'utilisateur dont on veut le token
     * @return le token de l'utilisateur s'il existe
     */
    public String getTokenBd(final Integer idUser) {
        return userRepository.findTokenById(idUser);
    }

    /**
     * appel de la methode de recherche par token.
     * @param token le tokenb rechercher
     * @return l'UserEntity correspondant au token
     */
    public UserEntity findUserByToken(final String token) {
        return userRepository.findByToken(token);
    }

    /**
     * fonction de verification que le token donnée n'est pas expirée.
     * @param jwtToken le token a tester
     * @return true si le token est encore valide, false sinon
     */
    public boolean verifierDateExpiration(final String jwtToken) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(envGetter.getSentence());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(jwtToken);

            // Récupérer les revendications du JWT
            Date dateExpiration = jwt.getExpiresAt();
            Date dateActuelle = new Date();

            return !dateExpiration.before(dateActuelle);

        } catch (JWTVerificationException e) {
            // Le JWT a expiré ou est invalide
            return false;
        }
    }

    /**
     * fonction d'arrangement des statistique des parcours donner dans un json.
     * @param parcours la liste des parcours a analyser
     * @return une HashMap correpondant au json des statistique
     */
    public HashMap<String, String> calculerPerformance(
            final List<PathEntity> parcours) {

        HashMap<String, String> map = new HashMap<>();

        float temps = 0;
        float distance = 0;
        for (PathEntity parcour : parcours) {
            temps += parcour.getTemps();
            distance += parcour.getDistance();
        }
        map.put("nombre_parcours", String.valueOf(parcours.size()));
        map.put("temps", String.valueOf(temps));
        map.put("distance", String.valueOf(distance));

        return map;

    }


}
