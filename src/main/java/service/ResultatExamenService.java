package service;

import java.util.List;
import java.util.Optional;

import model.Apprenant;
import model.ResultatExamen;

public interface ResultatExamenService {
	

	public List<ResultatExamen> resultatsExamens();
	
	public Optional<ResultatExamen> findById(Integer id);
	

	void save(ResultatExamen t);

}
