package service.impl;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import model.Examen;
import model.Matiere;
import model.Promotion;
import model.Sujet;
import repository.ExamenRepository;
import service.ExamenService;


@Service
@Transactional
public class ExamenServiceImpl implements ExamenService{

	@Resource
	private ExamenRepository examenRepository;
	
	@Override
	public List<Examen> examens() {
		
		return (List<Examen>) examenRepository.findAll();
	}

	@Override
	public void save(Examen e) {
		examenRepository.save(e);
		
	}

	@Override
	public Optional<Examen> findById(Integer id) {
		return examenRepository.findById(id);
	}

	@Override
	public List<Examen> examenByPromotion(Promotion promotion) {
		return examenRepository.findByPromotion(promotion);
	}

	@Override
	public List<Examen> findBySujet(Sujet sujet) {
		return examenRepository.findBySujet(sujet);
	}

	@Override
	public List<Examen> findByMatiere(Matiere matiere) {
		return examenRepository.findByMatiere(matiere);
	}
	
	
	
	
	

}
