package iut.info3.betterstravaapi.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class UserService {

    @Value("${SECRET_SENTENCE}")
    private String secretSentence;

    @Value("${TOKEN_EXPIRATION_DURATION}")
    private long tokenExpirationDuration;

    @Autowired
    private UserRepository userRepository;

    public boolean checkPresenceEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        return user != null;
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public List<UserEntity> findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email,password);
    }

    public String encryptPassword(String password) {

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));

        BigInteger number = new BigInteger(1, hash);
        StringBuilder passwordHash = new StringBuilder(number.toString(16));

        while (passwordHash.length() < 64)
        {
            passwordHash.insert(0, '0');
        }

        return passwordHash.toString();
    }

    public String generateToken(UserEntity user) {
        Algorithm algorithm = Algorithm.HMAC256(secretSentence);
        String jwt = JWT.create()
                .withClaim("id", user.getId() )
                .withClaim("email", user.getEmail())
                .withClaim("datetime-claim", Instant.now())
                .withExpiresAt(Instant.now().plus(tokenExpirationDuration, ChronoUnit.SECONDS))
                .sign(algorithm);
        return jwt;
    }
}
