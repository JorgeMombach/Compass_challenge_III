package jorge.mombach.msraces.repository;

import jorge.mombach.msraces.entity.Race;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaceRepository extends MongoRepository<Race, String> {
}
