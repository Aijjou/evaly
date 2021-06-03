package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import model.Matiere;
import model.Question;
import model.Sujet;
import model.SujetQuestion;
import model.Theme;
import service.MatiereService;
import service.QuestionService;
import service.SujetQuestionService;
import service.SujetService;
import service.ThemeService;

@Controller
public class SujetController {

	Boolean isConnectBoolean = false;
	Boolean isAdmin = false;
	Boolean isFormateur = false;
	Boolean isApprenant = false;
	Sujet sujet;
	
	@Autowired
	MatiereService matiereService;
	
	@Autowired
	ThemeService themeService;
	
	@Autowired
	QuestionService questionService;
	
	@Autowired
	SujetService sujetService;
	
	@Autowired
	SujetQuestionService sujetQuestionService;
	
	
	@RequestMapping(value = "/protected/creation-sujet", method = RequestMethod.GET)
	public String afficheFormateur(Model model) {
		
		isFormateur = true;
		isApprenant = false;
		isConnectBoolean = true;
		isAdmin = false;
		
		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("apprenant", isApprenant);
		model.addAttribute("admin", isAdmin);
		model.addAttribute("formateur", isFormateur);
		model.addAttribute("listmatieres", matiereService.matieres());
		
		return "/protected/creation-sujet";
	}
	
	@RequestMapping(value = "/protected/creation-sujet2", method = RequestMethod.POST)
	public String afficheFormateur2(Model model, @RequestParam Integer title) {
		
		isFormateur = true;
		isApprenant = false;
		isConnectBoolean = true;
		isAdmin = false;
		Matiere mat = new Matiere();
		mat.setIdMatiere(title);
		
		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("apprenant", isApprenant);
		model.addAttribute("admin", isAdmin);
		model.addAttribute("formateur", isFormateur);
		model.addAttribute("themes", themeService.findThemesByMat(mat));
		
		return "/protected/creation-sujet2";
	}
	
	@RequestMapping(value = "/protected/liste-question", method = RequestMethod.POST)
	public String afficheFListeQuestions(Model model, @RequestParam List<Integer> title) {
		
		isFormateur = true;
		isApprenant = false;
		isConnectBoolean = true;
		isAdmin = false;
		List<Question> questions = new ArrayList<Question>();
		
		System.err.println(title);
		
		//recuperation des ids
		List<Integer> questIds = title;
		//iteration dans la liste d'ids
		for (int i=0;i<questIds.size();i++) {
			//creation de theme et set l'id du theme
			Theme theme = new Theme();
			theme.setIdTheme(questIds.get(i));
			//creation de list avec toute les quest pour tel theme
			List <Question> Questionsbytheme = questionService.QuestionsByTheme(theme);
			//ajout des quest du theme a la liste de quest des themes selectionnés
			questions.addAll(Questionsbytheme);
		}
		
		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("apprenant", isApprenant);
		model.addAttribute("admin", isAdmin);
		model.addAttribute("formateur", isFormateur);
		model.addAttribute("questions", questions);
		
		return "/protected/liste-question";
	}
	
	@RequestMapping(value = "/protected/creation-sujet-gen", method = RequestMethod.GET)
	public String generateSubject(Model model) {
		
		isFormateur = true;
		isApprenant = false;
		isConnectBoolean = true;
		isAdmin = false;
		
		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("apprenant", isApprenant);
		model.addAttribute("admin", isAdmin);
		model.addAttribute("formateur", isFormateur);
		
		return "/protected/creation-sujet-gen";
	}
	
	@RequestMapping(value = "/protected/creation-sujet-manu", method = RequestMethod.GET)
	public String manualSubject(Model model) {
		
		isFormateur = true;
		isApprenant = false;
		isConnectBoolean = true;
		isAdmin = false;
		
		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("apprenant", isApprenant);
		model.addAttribute("admin", isAdmin);
		model.addAttribute("formateur", isFormateur);
		
		return "/protected/creation-sujet-manu";
	}

	@RequestMapping(value = "/protected/liste-sujet", method = RequestMethod.GET)
	public String listeSujet(Model model) {
		
		
		isFormateur = true;
		isApprenant = false;
		isConnectBoolean = true;
		isAdmin = false;
		
		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("apprenant", isApprenant);
		model.addAttribute("admin", isAdmin);
		model.addAttribute("formateur", isFormateur);
		
		
		return "/protected/liste-sujet";
	}
	
	@RequestMapping(value = "/protected/creation-sujet-manu", method = RequestMethod.POST)
	public String creationQuesionnaire(Model model, @RequestParam List<Integer> ok) {
		
		isFormateur = true;
		isApprenant = false;
		isConnectBoolean = true;
		isAdmin = false;
		List<Question> questions = new ArrayList<Question>();
		
		
		System.err.println(ok);
		
		//recuperation des ids
		List<Integer> questIds = ok;
		//iteration dans la liste d'ids
		for (int i=0;i<questIds.size();i++) {
			//creation de la question et set l'id question
			Optional<Question> questionOp = questionService.findById(questIds.get(i));
			Question question = questionOp.get();
			//ajout de la quest a la liste de quests selectionnées
			questions.add(question);
		}
		
		Collections.sort(questions);
		
		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("apprenant", isApprenant);
		model.addAttribute("admin", isAdmin);
		model.addAttribute("formateur", isFormateur);
		model.addAttribute("questions", questions);
		model.addAttribute("sujet", sujet);
		
		return "/protected/creation-sujet-manu";
	}
	
	@RequestMapping(value = "/protected/liste-sujet", method = RequestMethod.POST)
	public String validationQuesionnaire(Model model, @RequestParam List<Integer> list, String nom, String description) {
		
		isFormateur = true;
		isApprenant = false;
		isConnectBoolean = true;
		isAdmin = false;
		List<Question> questions = new ArrayList<Question>();
		HashSet<Theme> themes = new HashSet<Theme>();
		Matiere matiere = new Matiere();
		Sujet sujet = new Sujet();
		Question question = new Question();
		sujet.setNom(nom);
		sujet.setdescriptionSujet(description);
		
		
		System.err.println(list);
		
		//recuperation des ids
		List<Integer> questIds = list;
		//iteration dans la liste d'ids
		for (int i=0;i<questIds.size();i++) {
			//creation de la question et set l'id question
			Optional<Question> questionOp = questionService.findById(questIds.get(i));
			question = questionOp.get();
			//ajout question et sujet a l'objet SujetQuestion puis save
			SujetQuestion sujetQuestion = new SujetQuestion();
			sujetQuestion.setSujet(sujet);
			sujetQuestion.setQuestion(question);
//			sujetQuestionService.save(sujetQuestion);
			//ajout de la quest a la liste de quests selectionnées
			questions.add(question);
		}
		
		for(Sujet s : sujetService.sujets()) {
			System.err.println(s.getdescriptionSujet());
		}
		System.err.println(sujet.getNom());
		System.err.println(question.getIdQuestion());
		
		for(int j=0;j<questions.size();j++){
			Theme theme = questions.get(j).getTheme();
			matiere = questions.get(j).getTheme().getMatiere();
			themes.add(theme);
		}
		
		
		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("apprenant", isApprenant);
		model.addAttribute("admin", isAdmin);
		model.addAttribute("formateur", isFormateur);
		model.addAttribute("sujets", sujetService.sujets());
		model.addAttribute("questions", questions);
		model.addAttribute("themes", themes);
		model.addAttribute("matiere", matiere);
		
		return "/protected/liste-sujet";
	}
}
