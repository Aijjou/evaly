package repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import model.Apprenant;
import model.Examen;
import model.ResultatExamen;


public interface ResultatExamenRepository extends CrudRepository<ResultatExamen, Integer>{
	
	public Optional<ResultatExamen>findByApprenantAndExamen(Apprenant a, Examen e);

	public List<ResultatExamen> findByApprenant(Apprenant a);

}
