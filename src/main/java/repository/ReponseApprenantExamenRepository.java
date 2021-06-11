package repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import model.Apprenant;
import model.Examen;
import model.ReponseApprenantExamen;


public interface ReponseApprenantExamenRepository extends CrudRepository<ReponseApprenantExamen, Integer>{

	List<ReponseApprenantExamen> findByApprenantAndExamen(Apprenant a, Examen e);
	
	

}
