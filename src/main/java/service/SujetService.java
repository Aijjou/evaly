package service;

import java.util.List;
import java.util.Optional;

import model.Sujet;

public interface SujetService {
	

	public List<Sujet> sujets();

	void save(Sujet s);

	public Optional<Sujet> findById(Integer id);

}
