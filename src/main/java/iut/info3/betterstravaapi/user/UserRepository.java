package iut.info3.betterstravaapi.user;

<<<<<<< HEAD
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
=======
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
>>>>>>> UC001

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query(value = "SELECT * FROM utilisateurs where email = ?", nativeQuery = true)
    public UserEntity findByEmail(String email);

    @Query(value = "SELECT * from utilisateurs WHERE email = ?1 AND mot_de_passe = ?2",nativeQuery = true)
    public UserEntity findByEmailAndPassword(String email, String password);
}
