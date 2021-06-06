package repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import model.Formateur;

@Repository
public interface FormateurRepository extends CrudRepository<Formateur, Integer>{
	
	

}
