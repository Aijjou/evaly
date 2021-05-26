package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import model.Apprenant;


public interface ApprenantRepository extends CrudRepository<Apprenant, Long>{
	
	

}
