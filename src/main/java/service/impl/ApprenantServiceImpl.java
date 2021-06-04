package service.impl;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import model.Apprenant;
import model.Promotion;
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


	@Override
	public Optional<Apprenant> findById(Integer id) {
		return apprenantRepository.findById(id);
	}
	
	@Override
	public void save(Apprenant s) {
		apprenantRepository.save(s);
	}


	@Override
	public List<Apprenant> ApprenantsByPromotion(Promotion promotion) {
		return apprenantRepository.findByPromotion(promotion);
	}

}
