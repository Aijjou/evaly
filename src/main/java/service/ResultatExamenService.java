package service;

import java.util.List;
import java.util.Optional;

import model.Apprenant;
import model.Examen;
import model.ResultatExamen;

public interface ResultatExamenService {
	

	public List<ResultatExamen> resultatsExamens();
	
	public Optional<ResultatExamen> findById(Integer id);
	
	public Optional<ResultatExamen>findByApprenantAndExamen(Apprenant a, Examen e);
	
	public List<ResultatExamen>findByApprenant(Apprenant a);
	
	void save(ResultatExamen t);

}
