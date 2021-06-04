package service;

import java.util.List;
import java.util.Optional;

import model.Formateur;
import model.FormateurMatiere;

public interface FormateurMatiereService {
	
	List<FormateurMatiere> findByFormateur(Formateur formateur);

}
