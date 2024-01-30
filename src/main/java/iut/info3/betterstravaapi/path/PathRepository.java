package iut.info3.betterstravaapi.path;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface PathRepository extends MongoRepository <PathEntity, ObjectId> {

    List<PathEntity> findPathByIdUtilisateurAndArchiveAndDateAfter(int id, boolean archive, LocalDate date);

    List<PathEntity> findPathByIdUtilisateurAndArchive(int id, boolean archive);

    PathEntity findTopByIdUtilisateurAndArchiveOrderByDateDesc(int id,boolean archive);

}
