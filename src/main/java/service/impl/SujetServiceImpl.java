package service.impl;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import model.Matiere;
import model.Sujet;
import model.Theme;
import repository.SujetRepository;
import service.SujetService;
import service.ThemeService;



@Service
@Transactional
public class SujetServiceImpl implements SujetService{

	@Resource
	SujetRepository sujetRepository;
	
	
	@Override
	public List<Sujet> sujets() {
		
		return (List<Sujet>) sujetRepository.findAll();
	}
	
	@Override
	public Optional<Sujet> findById(Integer id){
		
		return sujetRepository.findById(id);
	}

	@Override
	public void save(Sujet s) {
		sujetRepository.save(s);
		
	}

	@Override
	public List<Sujet> findByMatiere(Matiere matiere) {
		return sujetRepository.findByMatiere(matiere);
	}

}
