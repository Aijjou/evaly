package service.impl;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import model.Apprenant;
import model.Matiere;
import model.Question;
import model.Theme;
import repository.ApprenantRepository;
import repository.MatiereRepository;
import repository.QuestionRepository;
import repository.ThemeRepository;
import service.ApprenantService;
import service.MatiereService;
import service.QuestionService;
import service.ThemeService;



@Service
@Transactional
public class MatiereServiceImpl implements MatiereService{

	@Resource
	MatiereRepository matiereRepo;
	
	@Override
	public List<Matiere> findAllMatieres() {
		return (List<Matiere>) matiereRepo.findAll();
	}

}