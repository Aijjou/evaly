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
public class MatiereController {

	Boolean isConnectBoolean = false;
	Boolean isAdmin = false;
	Boolean isFormateur = false;
	Boolean isApprenant = false;

	@RequestMapping(value = "/protected/creation-matiere", method = RequestMethod.GET)
	public String creationMatiere(Model model) {


		isAdmin = false;
		isFormateur = false;
		isApprenant = false;
		
		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("apprenant", isApprenant);
		model.addAttribute("admin", isAdmin);
		model.addAttribute("formateur", isFormateur);
		
		return "/protected/creation-matiere";
	}
	
}
