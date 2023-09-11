package jorge.mombach.mshistory.repository;

import jorge.mombach.mshistory.entity.RaceResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaceResultRepository extends MongoRepository<RaceResult, String> {
}
