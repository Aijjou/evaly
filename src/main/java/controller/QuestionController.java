package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dto.QuestionDto;
import model.Matiere;
import model.Theme;
import service.MatiereService;
import service.QuestionService;
import service.ThemeService;

@Controller
public class QuestionController {

	Boolean isConnectBoolean = false;
	Boolean isAdmin = false;
	Boolean isFormateur = false;
	Boolean isApprenant = false;
	
	@Autowired
	QuestionService questionService;
	
	@Autowired
	ThemeService themeService;
	
	@Autowired
	MatiereService matiereService;
	
	@RequestMapping(value = "/protected/creation-question", method = RequestMethod.GET)
	public String creationQuestion(Model model) {

		isFormateur = true;
		isApprenant = false;
		isConnectBoolean = true;
		isAdmin = false;
		
		QuestionDto qdto = new QuestionDto();
		qdto.setDescriptionQuestion("dsc");
		qdto.setIsQcm(true);
		qdto.setRep1br("true");
		model.addAttribute("question", qdto);

		List<Theme> listth = themeService.themes();
		model.addAttribute("listtheme", listth);
		
		List<Matiere> listmatiere = matiereService.matieres();
		model.addAttribute("listmatiere", listmatiere);
		
		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("apprenant", isApprenant);
		model.addAttribute("admin", isAdmin);
		model.addAttribute("formateur", isFormateur);
		
		return "/protected/creation-question";
	}
	
	@RequestMapping(value = "/protected/liste-question", method = RequestMethod.GET)
	public String listeQuestion(Model model) {
		
		return "/protected/liste-question";
	}
}
