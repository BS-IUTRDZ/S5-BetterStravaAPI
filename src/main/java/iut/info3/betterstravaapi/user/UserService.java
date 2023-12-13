package iut.info3.betterstravaapi.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean checkPresenceEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        return user != null;
    }

}
