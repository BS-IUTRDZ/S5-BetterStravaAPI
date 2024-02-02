package iut.info3.betterstravaapi.path;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PathRepository extends MongoRepository<PathEntity, ObjectId> {

    /**
     * appel a la base de donnees pour trouver
     * les parcours des 30 derniers jours.
     * @param id id de l'utilisateur
     * @param archive false pour recuperer les parcours non suprimé
     * @param date date correspondant a 30 jours avant
     * @return la liste des parcours trouver
     */
    List<PathEntity> findPathByIdUtilisateurAndArchiveAndDateAfter(
            int id, boolean archive, long date);

    /**
     * appel a la base de donnees pour trouver
     * les parcours d'un utilisateur.
     * @param id id de l'utilisateur
     * @param archive false pour recuperer les parcours non suprimé
     * @return la liste des parcours trouve
     */
    List<PathEntity> findPathByIdUtilisateurAndArchive(int id, boolean archive);

    /**
     * appel a la base de donnees pour trouver le dernier
     * parcours d'un utilisateur.
     * @param id id de l'utilisateur
     * @param archive false pour recuperer les parcours non suprimé
     * @return la liste des parcours trouver
     */
    PathEntity findTopByIdUtilisateurAndArchiveOrderByDateDesc(
            int id, boolean archive);

    @Query("{'nom': ?0, 'date': {$gte: ?1, $lte: ?2}}")
    List<PathEntity> findPathByDateAndName(String name,
                                           String dateInf,
                                           String dateSup);
    @Query("{'nom': ?0}")
    List<PathEntity> findPathByName(String name);
}
