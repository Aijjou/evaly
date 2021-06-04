package service;

import java.util.List;
import java.util.Optional;

import model.Apprenant;
import model.Promotion;

public interface ApprenantService {
	

	public List<Apprenant> apprenants();
	
	public List<Apprenant> ApprenantsByPromotion(Promotion promotion);
	
	public Optional<Apprenant> findById(Integer id);
	

	void save(Apprenant t);

}
