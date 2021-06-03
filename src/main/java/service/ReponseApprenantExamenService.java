package service;

import java.util.List;
import java.util.Optional;

import model.Apprenant;
import model.ReponseApprenantExamen;

public interface ReponseApprenantExamenService {
	

	public List<ReponseApprenantExamen> reponseApprenantExamens();
	
	public Optional<ReponseApprenantExamen> findById(Integer id);
	
	void save(ReponseApprenantExamen t);

}
