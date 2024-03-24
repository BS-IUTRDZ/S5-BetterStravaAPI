package iut.info3.betterstravaapi.path;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository of the PathEntity.
 */
@Repository
public interface PathRepository extends MongoRepository<PathEntity, ObjectId> {

    /**
     * Query to find created path between a date and the current date
     * of a user.
     * @param id id of the user
     * @param archive false to get the non deleted paths
     * @param date date to compare with the date of the path
     * @return the list of paths found
     */
    List<PathEntity> findPathByIdUtilisateurAndArchiveAndDateAfter(
            int id, boolean archive, long date);

    /**
     * Query to find the paths of a user.
     * @param id id of the user
     * @param archive false to get the non deleted paths
     * @return the list of paths found
     */
    List<PathEntity> findPathByIdUtilisateurAndArchive(int id, boolean archive);


    /**
     * Query to find the last created path of a user.
     * @param id id of the user
     * @param archive false to get the non deleted paths
     * @return the last path found
     */
    PathEntity findTopByIdUtilisateurAndArchiveOrderByDateDesc(
            int id, boolean archive);

    /**
     * @param infDate inferior date to filter the paths
     * @param supDate superior date to filter the paths
     * @param name filter on the name of the path
     * @param id filter path with the user id
     * @param archive filter on the archive field
     * @param pageable filter to limit the number of paths returned
     *                 according to the number of paths already loaded
     *                 in the application.
     * @return the paths matching all the filters
     */
    @Query("{'date':  {$gte: ?0, $lte: ?1}, nom: {$regex: ?2}, "
            + "'idUtilisateur': ?3, 'archive': ?4}")
    List<PathEntity> findEntitiesByDateAndName(
            long infDate, long supDate, String name, int id,
            boolean archive, Pageable pageable);

    /**
     *
     * @param infDate inferior date to filter the paths
     * @param supDate superior date to filter the paths
     * @param name filter on the name of the path
     * @param minLength minimum length of the path
     * @param maxLength maximum length of the path
     * @param id filter path with the user id
     * @param archive filter on the archive field
     * @param pageable filter to limit the number of paths returned
     *                 according to the number of paths already loaded
     *                 in the application.
     * @return the paths matching all the filters
     */
    @Query("{'date':  {$gte: ?0, $lte: ?1}, nom: {$regex: ?2}, "
            + "'statistiques.distance': {$gte:  ?3, $lte: ?4},"
            + "'idUtilisateur': ?5, 'archive': ?6}")
    List<PathEntity> findEntitiesByDateAndNameAndDistance(
            long infDate, long supDate, String name,
            int minLength, int maxLength, int id,
            boolean archive, Pageable pageable);

    /**
     * Query to find the path of a user by its id.
     * @param userId id of the user
     * @param id id of the path
     * @return the path found
     */
    PathEntity findByIdAndArchiveFalseAndAndIdUtilisateur(ObjectId id,
                                                          int userId);

}
