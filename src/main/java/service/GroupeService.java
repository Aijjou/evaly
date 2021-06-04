package service;

import java.util.List;
import java.util.Optional;

import model.GroupeFormateur;

public interface GroupeService {
	
	
	public List<GroupeFormateur> getListGroupeFormateur();
	
	public Optional<GroupeFormateur> findById(Integer id);
	

	void save(GroupeFormateur t);

}
