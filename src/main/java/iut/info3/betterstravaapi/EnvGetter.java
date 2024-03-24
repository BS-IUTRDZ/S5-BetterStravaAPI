package iut.info3.betterstravaapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service to get environment variables.
 */

@Service
public class EnvGetter {

    /**
     * Secret sentence to encode the token.
     */
    @Value("${SECRET_SENTENCE}")
    private String secretSentence;

    /**
     * Duration before token expiration.
     */
    @Value("${TOKEN_EXPIRATION_DURATION}")
    private long tokenExpirationDuration;

    /**
     * Getter of the secret sentence.
     * @return the secret sentence.
     */
    public String getSentence() {
        return secretSentence;
    }

    /**
     * Getter of the token expiration duration.
     * @return the token expiration duration.
     */
    public long getExpiration() {
        return tokenExpirationDuration;
    }


}
