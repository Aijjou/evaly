package repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import model.Formateur;
import model.FormateurGroupeFormateur;

public interface FormateurGroupeFormateurRepository extends CrudRepository<FormateurGroupeFormateur, Integer> {

	
	List<FormateurGroupeFormateur> findByFormateur(Formateur formateur);
	
	
	
	
}
