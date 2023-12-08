package iut.info3.betterstravaapi.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service associé à la gestion des utilisateurs.
 */
@Service
public class UserService {

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
}
