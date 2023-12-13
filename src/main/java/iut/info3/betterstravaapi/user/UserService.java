package iut.info3.betterstravaapi.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean checkPresenceEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        return user != null;
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
}
