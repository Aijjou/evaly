package service.impl;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import model.Apprenant;
import repository.ApprenantRepository;
import service.ApprenantService;



@Service
@Transactional
public class ApprenantServiceImpl implements ApprenantService{

	@Resource
	ApprenantRepository apprenantRepository;
	
	
	@Override
	public List<Apprenant> apprenants() {
		
		
		
		return (List<Apprenant>) apprenantRepository.findAll();
	}

}
