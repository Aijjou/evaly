package service;

import java.util.List;
import java.util.Optional;

import model.Matiere;
import model.Sujet;

public interface SujetService {
	

	public List<Sujet> sujets();

	void save(Sujet s);

	public Optional<Sujet> findById(Integer id);
	
	List<Sujet> findByMatiere(Matiere matiere);

}
