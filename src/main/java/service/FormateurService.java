package service;

import java.util.List;
import java.util.Optional;

import model.Formateur;

public interface FormateurService {
	

	public List<Formateur>formateurs();
	
	public Optional<Formateur> findById(Integer id);
	
	public boolean delete(Integer isDelete);

}
