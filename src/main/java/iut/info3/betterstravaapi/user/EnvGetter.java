package iut.info3.betterstravaapi.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service de recuperation des variables d'environement.
 */

@Service
public class EnvGetter {

    /**
     * Phrase secrete pour codage jwt.
     */
    @Value("${SECRET_SENTENCE}")
    private String secretSentence;

    /**
     * Duree de validite du token.
     */
    @Value("${TOKEN_EXPIRATION_DURATION}")
    private long tokenExpirationDuration;

    /**
     * getter de la Secret Sentence.
     * @return la phrase secrete.
     */
    public String getSentence() {
        return secretSentence;
    }

    /**
     * getter de la duréée avant expiration du token.
     * @return la duréé avant expiration.
     */
    public long getExpiration() {
        return tokenExpirationDuration;
    }

}
