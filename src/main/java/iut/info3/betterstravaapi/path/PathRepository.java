package iut.info3.betterstravaapi.path;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository des parcours.
 */
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

    /**
     *
     * @param dateInf date permettant de ne récupérer que les
     *                parcours avec une date inférieure
     * @param dateSup date permettant de ne récupérer que les
     *                parcours avec une date supérieure
     * @param nom chaine permettant de faire le filtre sur le champ nom
     * @param id id unique de l'utilisateur en base de données
     * @param archive filtre sur le champ archive permettant
     * @return les parcours respectant tout les filtres
     */
<<<<<<< HEAD
    @Query("{'date':  {$gte: ?0, $lte: ?1}, 'nom': ?2,"
           + "'idUtilisateur': ?3, 'archive': ?4}")
    List<PathEntity> findEntitiesByDateAndName(long dateInf, long dateSup,
                                               String nom, int id,
                                               boolean archive);

    /**
     * Recupère un parcours par son id.
     * @param id id d'un parcours
     * @return le parcours correspondant à l'id
     */
    PathEntity findByIdAndArchiveFalse(ObjectId id);

=======
    @Query("{'date':  {$gte: ?0, $lte: ?1}, $text: {$search: ?2}, 'idUtilisateur': ?3, 'archive': ?4}")
    List<PathEntity> findEntitiesByDateAndName
            (long dateInf, long dateSup, String nom, int id, boolean archive);
>>>>>>> 75a2fc4 (Tentative d'indexation du nom des parcours)


}
