package service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import model.GroupeFormateur;
import repository.GroupeRepository;
import service.GroupeService;


@Service
@Transactional
public class GroupeServiceImpl implements GroupeService{

	@Resource
	GroupeRepository groupeRepository;
	
	
	@Override
	public List<GroupeFormateur> getListGroupeFormateur() {
		
		return (List<GroupeFormateur>) groupeRepository.findAll();
	
	}

}
