package service.impl;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import model.Formateur;
import model.FormateurMatiere;
import repository.FormateurMatiereRepository;
import service.FormateurMatiereService;

@Service
@Transactional
public class FormateurMatiereServiceImpl implements FormateurMatiereService {

	@Resource
	FormateurMatiereRepository formateurMatiereRepository;

	@Override
	public List<FormateurMatiere> findByFormateur(Formateur formateur) {
		return formateurMatiereRepository.findByFormateur(formateur);
	}
	
	

}
