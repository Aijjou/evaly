package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.Apprenant;
import model.Formateur;
import service.ApprenantService;
import service.FormateurService;

@Controller
public class FormateurController {

	Boolean isConnectBoolean = false;
	Boolean isAdmin = false;
	
	
	@Autowired
	FormateurService formateurService;

	@RequestMapping(value = "/admin/liste-formateur", method = RequestMethod.GET)
	public String afficheFormateur(Model model) {

		
		
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
		model.addAttribute("admin", isAdmin);

		model.addAttribute("formateurs", formateurs);
		model.addAttribute("matieres", nomMatiere);

		return "/admin/liste-formateur";

	}

}
