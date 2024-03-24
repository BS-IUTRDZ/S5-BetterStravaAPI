package iut.info3.betterstravaapi.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * Repository associated to the user table in the MySQL database.
 */
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    /**
     * Query to search a user with a specific email.
     * @param email email to search in the database
     * @return the user found with this email
     */
    @Query(value = "SELECT * FROM utilisateurs where email = ?",
            nativeQuery = true)
    UserEntity findByEmail(String email);

    /**
     * Query to search a user with a specific id.
     * @param userId id of the user to search in the database
     * @return the token of the user found with this id
     */
    @Query(value = "SELECT jwt_token FROM utilisateurs where id = ?",
            nativeQuery = true)
    String findTokenById(Integer userId);

    /**
     * Query to search a user with a specific email and password.
     * @param email email of the user
     * @param password password of the user
     * @return list of users found with this email and password
     */
    @Query(value = "SELECT * from utilisateurs WHERE email = ?1"
           + " AND mot_de_passe = ?2", nativeQuery = true)
    List<UserEntity> findByEmailAndPassword(String email,
                                                   String password);

    /**
     * Query to search a user with a specific token.
     * @param token token to search in the database
     * @return the user found with this token
     */
    @Query (value = "SELECT * from utilisateurs WHERE jwt_token = ?1",
            nativeQuery = true)
    UserEntity findByToken(String token);

}
