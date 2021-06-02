package controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dto.ExamenDto;
import dto.QuestionDto;
import model.Examen;
import service.ExamenService;
import service.PromotionService;
import service.SujetService;

@Controller
@Scope("session")
public class ExamenController {

	Boolean isAdmin = false;
	Boolean isFormateur = false;
	Boolean isApprenant = false;
	Boolean isConnectBoolean = true;
	
	@Autowired
	ExamenService examenService;
	@Autowired
	SujetService sujetService;
	@Autowired
	PromotionService promotionService;


	@RequestMapping(value = "protected/liste-examen", method = RequestMethod.GET)
	public String afficheExamen(Model model) {

		
		isAdmin = false;
		isFormateur = false;
		isApprenant = false;
		isConnectBoolean = true;
		
		List<Examen> examens = examenService.getListExamen();

		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("examens", examens);
		model.addAttribute("apprenant", isApprenant);
		model.addAttribute("admin", isAdmin);
		model.addAttribute("formateur", isFormateur);

		return "protected/liste-examen";

	}

	@RequestMapping(value = "protected/creation-examen", method = RequestMethod.GET)
	public String creationExamen(ExamenDto examen,Model model) {
		ExamenDto edto = new ExamenDto();
		edto.setDateExamen(new Date());
		edto.setTitre("Titre");
		edto.setDureeExamen(60);
		
		model.addAttribute("examen", edto);
		model.addAttribute("sujets", sujetService.sujets());
		model.addAttribute("promotions", promotionService.promotions());

		return "protected/creation-examen";

	}
	
	@RequestMapping(value = "/protected/creation-examen-sub", method = RequestMethod.POST)
	public String creationExamenSubmit(@ModelAttribute ExamenDto examen, Model model) {
		if (examen.getDateExamen()==null || examen.getTitre()==null || examen.getSujet()==null || 
				examen.getDureeExamen()==null || examen.getPromotion() ==null) {
			model.addAttribute("examen", examen);
			return "protected/creation-examen";
		}
		Examen nve = new Examen();
		nve.setDateExamen(examen.getDateExamen());
		nve.setTitre(examen.getTitre());
		nve.setDureeExamen(examen.getDureeExamen());

		
		return "protected/liste-examen";
	}

}
