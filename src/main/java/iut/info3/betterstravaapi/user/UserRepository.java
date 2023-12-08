package iut.info3.betterstravaapi.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository de UserEntity.
 */
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    /**
     * Recherche d'un email spécifique dans la base de données.
     * @param email email à chercher en base
     * @return l'utilisateur associé à l'email s'il existe
     */
    @Query(value = "SELECT * FROM utilisateurs where email = ?",
            nativeQuery = true)
    UserEntity findByEmail(String email);

    @Query(value = "SELECT * from utilisateurs WHERE email = ?1 AND mot_de_passe = ?2",nativeQuery = true)
    public List<UserEntity> findByEmailAndPassword(String email, String password);
}
