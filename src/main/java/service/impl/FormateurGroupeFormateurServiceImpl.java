package service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Formateur;
import model.FormateurGroupeFormateur;
import model.GroupeFormateur;
import repository.FormateurGroupeFormateurRepository;
import service.FormateurGroupeFormateurService;

@Service
@Transactional
public class FormateurGroupeFormateurServiceImpl implements FormateurGroupeFormateurService {

	@Autowired
	FormateurGroupeFormateurRepository formateurGroupeFormateurRepository;

	@Override
	public List<FormateurGroupeFormateur> findGroupeFormateurByFormateur(Formateur formateur) {

		return formateurGroupeFormateurRepository.findByFormateur(formateur);
	}

}
