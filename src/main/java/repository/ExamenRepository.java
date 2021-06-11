package repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import model.Examen;
import model.Matiere;
import model.Promotion;
import model.Sujet;

public interface ExamenRepository extends CrudRepository<Examen, Integer> {

	
	List<Examen> findByPromotion(Promotion promotion);
	
	List<Examen> findBySujet(Sujet sujet);

	List<Examen> findByMatiere(Matiere matiere);
	

}
