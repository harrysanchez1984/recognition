package co.com.mutant.recognition.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import co.com.mutant.recognition.entities.MutantEntity;

@Repository
public interface MutantRepository extends MongoRepository<MutantEntity, String> {

    Optional<Long> countByIsMutantValue(final Boolean isMutant);

}