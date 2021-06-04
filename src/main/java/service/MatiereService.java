package service;

import java.util.List;
import java.util.Optional;

import model.Matiere;

public interface MatiereService {
	
	public List<Matiere> matieres();
	
	public Optional<Matiere> findById(Integer id);
	
	public void save(Matiere m);

}
