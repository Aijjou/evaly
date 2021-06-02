package service;

import java.util.List;
import java.util.Optional;

import model.Reponse;
import model.Theme;

public interface ReponseService {
	

	public List<Reponse> reponses();

	void save(Reponse t);

	public Optional<Reponse> findById(Integer id);

}
