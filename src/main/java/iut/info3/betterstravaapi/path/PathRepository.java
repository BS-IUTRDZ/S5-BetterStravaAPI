package iut.info3.betterstravaapi.path;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PathRepository extends MongoRepository <PathEntity, ObjectId> {

}
