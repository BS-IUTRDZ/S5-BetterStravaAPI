package iut.info3.betterstravaapi.path;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
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
     * @param pageable filtre permettant de limiter le nombre de parcours
     *                 renvoyer en fonction du nombre de parcours déjà charger
     *                 dans l'application.
     * @return les parcours respectant tout les filtres
     */
    @Query("{'date':  {$gte: ?0, $lte: ?1}, nom: {$regex: ?2}, "
            + "'idUtilisateur': ?3, 'archive': ?4}")
    List<PathEntity> findEntitiesByDateAndName(
            long dateInf, long dateSup, String nom, int id,
            boolean archive, Pageable pageable);

    /**
     *
     * @param dateInf date permettant de ne récupérer que les
     *                parcours avec une date inférieure
     * @param dateSup date permettant de ne récupérer que les
     *                parcours avec une date supérieure
     * @param nom chaine permettant de faire le filtre sur le champ nom
     * @param distanceMin distance minimale du parcours recherché
     * @param distanceMax distance maximale du parcours recherché
     * @param id id unique de l'utilisateur en base de données
     * @param archive filtre sur le champ archive permettant
     * @param pageable filtre permettant de limiter le nombre de parcours
     *                 renvoyer en fonction du nombre de parcours déjà charger
     *                 dans l'application.
     * @return les parcours respectant tout les filtres
     */
    @Query("{'date':  {$gte: ?0, $lte: ?1}, nom: {$regex: ?2}, "
            + "'statistiques.distance': {$gte:  ?3, $lte: ?4},"
            + "'idUtilisateur': ?5, 'archive': ?6}")
    List<PathEntity> findEntitiesByDateAndNameAndDistance(
            long dateInf, long dateSup, String nom,
            int distanceMin, int distanceMax, int id,
            boolean archive, Pageable pageable);



    /**
     * Recupère un parcours par son id.
     * @param idUtilisateur id unique de l'utilisateur en base de données
     * @param id id d'un parcours
     * @return le parcours correspondant à l'id
     */
    PathEntity findByIdAndArchiveFalseAndAndIdUtilisateur(ObjectId id,
                                                          int idUtilisateur);

}
