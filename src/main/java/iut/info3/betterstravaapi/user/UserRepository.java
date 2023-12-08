package iut.info3.betterstravaapi.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    @Query(value = "SELECT * from utilisateurs WHERE email = ?1 AND mot_de_passe = ?2",nativeQuery = true)
    public UserEntity findByEmailAndPassword(String email, String password);
}
