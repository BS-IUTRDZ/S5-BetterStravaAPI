package iut.info3.betterstravaapi.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

}
