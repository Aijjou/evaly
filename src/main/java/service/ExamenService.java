package service;

import java.util.List;
import java.util.Optional;

import model.Examen;

public interface ExamenService  {
	
	List<Examen> examens();
	
	public void save (Examen e);
	
	public Optional<Examen> findById(Integer id);
	
	

}
