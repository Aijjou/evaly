package repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import model.Formateur;
import model.FormateurMatiere;

public interface FormateurMatiereRepository extends CrudRepository<FormateurMatiere, Integer>{
	
	List<FormateurMatiere> findByFormateur(Formateur formateur);

}
