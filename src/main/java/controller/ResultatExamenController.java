package controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import dto.ExamenDto;
import dto.QuestionDto;
import dto.QuestionnaireDto;
import model.Apprenant;
import model.Examen;
import model.Promotion;
import model.Question;
import model.Reponse;
import model.ReponseApprenantExamen;
import model.ResultatExamen;
import model.Sujet;
import model.SujetQuestion;
import service.ApprenantService;
import service.ExamenService;
import service.PromotionService;
import service.QuestionService;
import service.ReponseApprenantExamenService;
import service.ReponseService;
import service.ResultatExamenService;
import service.SujetService;

@Controller
@Scope("session")
public class ResultatExamenController {

	// yyyy-MM-dd"
//	@InitBinder
//	public void initBinder(WebDataBinder binder) {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		dateFormat.setLenient(false);
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
//	}

	Boolean isAdmin = false;
	Boolean isFormateur = false;
	Boolean isApprenant = false;
	Boolean isConnectBoolean = true;

	@Autowired
	ExamenService examenService;
	@Autowired
	SujetService sujetService;
	@Autowired
	ResultatExamenService resultatsexamenService;
	@Autowired
	QuestionService questionService;
	@Autowired
	ApprenantService apprenantService;
	@Autowired
	ReponseService reponseService;
	@Autowired
	ReponseApprenantExamenService reponseApprenantExamenService;
	@Autowired
	ResultatExamenService resultatExamenService;

	@RequestMapping(value = "protected/liste-resultat", method = RequestMethod.GET)
	public String afficheExamen(Model model) {

		isAdmin = true;
		isFormateur = false;
		isApprenant = false;
		isConnectBoolean = true;

		List<ResultatExamen> resultatsexamens = resultatsexamenService.resultatsExamens();
		model.addAttribute("resultats", resultatsexamens);

		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("apprenant", isApprenant);
		model.addAttribute("admin", isAdmin);
		model.addAttribute("formateur", isFormateur);

		return "protected/liste-resultat";

	}

	
}
