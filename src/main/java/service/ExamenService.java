package service;

import java.util.List;
import java.util.Optional;

import model.Examen;
import model.Matiere;
import model.Promotion;
import model.Sujet;

public interface ExamenService  {
	
	List<Examen> examens();
	
	public void save (Examen e);
	
	public Optional<Examen> findById(Integer id);
	
	public List<Examen> examenByPromotion(Promotion promotion);
	
	List<Examen> findBySujet(Sujet sujet);

	List<Examen> findByMatiere(Matiere matiere);
}
