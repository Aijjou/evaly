package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import model.Examen;
import model.Formateur;
import model.FormateurMatiere;
import model.Matiere;
import model.Sujet;
import service.ExamenService;
import service.FormateurMatiereService;
import service.FormateurService;
import service.SujetService;

@Controller
public class FormateurController {

	Boolean isConnectBoolean = false;
	Boolean isAdmin = false;
	Boolean isFormateur = false;
	Boolean isApprenant = false;
	
	
	@Autowired
	FormateurService formateurService;
	
	@Autowired
	FormateurMatiereService formateurMatiereService;

	@Autowired
	SujetService sujetService;

	@Autowired
	ExamenService examenService;

	@RequestMapping(value = "/admin/liste-formateur", method = RequestMethod.GET)
	public String afficheFormateur(Model model) {

		isAdmin = true;
		isFormateur = false;
		isApprenant = false;
		
		List<Formateur> formateurs = formateurService.formateurs();
		List<String> nomMatiere = new ArrayList<>();
		for (Formateur formateur : formateurs) {
			System.out.println("formateur" + formateur.getNom());
			formateur.getFormateurMatieres().stream().forEach(matiere -> {
				nomMatiere.add(matiere.getMatiere().getNom());

			});

		}
		
		isConnectBoolean = true;
		isAdmin = true;
		
		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("apprenant", isApprenant);
		model.addAttribute("admin", isAdmin);
		model.addAttribute("formateur", isFormateur);
		model.addAttribute("formateurs", formateurs);
		model.addAttribute("matieres", nomMatiere);

		return "/admin/liste-formateur";

	}
}
