package iut.info3.betterstravaapi.path;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PathRepository extends MongoRepository <PathEntity, ObjectId> {

    List<PathEntity> findPathByIdUtilisateurAndArchiveAndDateAfter(int id, boolean archive, long date);

    List<PathEntity> findPathByIdUtilisateurAndArchive(int id, boolean archive);

    PathEntity findTopByIdUtilisateurAndArchiveOrderByDateDesc(int id,boolean archive);

}
