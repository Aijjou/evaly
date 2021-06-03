package service;

import java.util.List;
import java.util.Optional;

import model.Apprenant;

public interface ApprenantService {
	

	public List<Apprenant> apprenants();
	
	public Optional<Apprenant> findById(Integer id);
	

	void save(Apprenant t);

}
