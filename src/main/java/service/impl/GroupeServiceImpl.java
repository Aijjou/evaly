package service.impl;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import model.GroupeFormateur;
import repository.GroupeRepository;
import service.GroupeService;


@Service
@Transactional
public class GroupeServiceImpl implements GroupeService{

	@Autowired
	GroupeRepository groupeRepository;
	
	
	@Override
	public List<GroupeFormateur> getListGroupeFormateur() {
		
		return (List<GroupeFormateur>) groupeRepository.findAll();
	
	}
	
	@Override
	public Optional<GroupeFormateur> findById(Integer id) {
		return groupeRepository.findById(id);
	}
	
	@Override
	public void save(GroupeFormateur s) {
		groupeRepository.save(s);

	}

	@Override
	public List<Integer> findGroupeFormateurByidGroupeFormateur(Integer idFormateur) {
	
		return findGroupeFormateurByidGroupeFormateur(idFormateur);
	}

}
