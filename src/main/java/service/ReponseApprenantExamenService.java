package service;

import java.util.List;
import java.util.Optional;

import model.Apprenant;
import model.Examen;
import model.ReponseApprenantExamen;

public interface ReponseApprenantExamenService {
	

	public List<ReponseApprenantExamen> reponseApprenantExamens();
	
	public Optional<ReponseApprenantExamen> findById(Integer id);
	
	public List<ReponseApprenantExamen> findByApprenantAndExamen(Apprenant a, Examen e);
	
	void save(ReponseApprenantExamen t);

}
