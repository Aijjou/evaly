package service;

import java.util.List;

import model.Formateur;
import model.FormateurGroupeFormateur;
import model.GroupeFormateur;

public interface FormateurGroupeFormateurService {

public  List<FormateurGroupeFormateur> findGroupeFormateurByFormateur(Formateur formateur);

}
