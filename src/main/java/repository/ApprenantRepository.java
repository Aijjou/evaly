package repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import model.Apprenant;

@Repository
public interface ApprenantRepository extends CrudRepository<Apprenant, Long> {

}
