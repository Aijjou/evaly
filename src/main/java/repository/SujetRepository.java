package repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import model.Matiere;
import model.Sujet;


public interface SujetRepository extends CrudRepository<Sujet, Integer>{
	
	List<Sujet> findByMatiere(Matiere matiere);

}
