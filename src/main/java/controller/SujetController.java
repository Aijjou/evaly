package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.QSort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import dto.QuestionDuSujetDto;
import dto.SujetDto;
import model.Matiere;
import model.Organisation;
import model.Promotion;
import model.Question;
import model.Sujet;
import model.SujetQuestion;
import model.Theme;
import service.MatiereService;
import service.PromotionService;
import service.QuestionService;
import service.SujetQuestionService;
import service.SujetService;
import service.ThemeService;

@Controller
public class SujetController {


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

	@Autowired
	PromotionService promotionService;

	List<Theme> themesCtrlMem = new ArrayList<Theme>();
	Boolean isAdmin = false;
	Boolean isFormateur = false;
	Boolean isApprenant = false;
	Boolean isConnectBoolean = true;
	Integer idUtilisateur = null;
	
	private void verificationRolesAndSetIdUtilisateur() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.err.println(" --- --- --- verificationRoles  --- --- --- ");
		auth.getAuthorities().stream().forEach(role -> {
			if (role.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				System.out.println("ROLE_ADMIN");
			} else
				isAdmin = false;

			if (role.getAuthority().equals("ROLE_APPRENANT")) {
				isApprenant = true;
				System.out.println("ROLE_APPRENANT");
			} else
				isApprenant = false;

			if (role.getAuthority().equals("ROLE_FORMATEUR")) {
				isFormateur = true;
				System.out.println("ROLE_FORMATEUR");
			} else
				isFormateur = false;
		});
		if (isAdmin || isFormateur || isApprenant) {
		principal.UserPrincipal userPrincipal = (principal.UserPrincipal) auth.getPrincipal();
		idUtilisateur = userPrincipal.getId();
		}
		System.err.println(" --- --- --- verificationRoles --- --- --- ");
	}

	@RequestMapping(value = "/protected/creation-sujet", method = RequestMethod.GET)
	public String afficheFormateur(Model model) {

		verificationRolesAndSetIdUtilisateur();
		System.out.println("admin ? " + isAdmin + " apprenant ? " + isApprenant + " formateur ? " + isFormateur
				+ " id : " + idUtilisateur);
		
		if (idUtilisateur==null)return "redirect:/public/connexion";
		if (isApprenant)
			return "redirect:/public/accessDenied";
		
		model.addAttribute("listmatieres", matiereService.matieres());

		return "/protected/creation-sujet";
	}

	@RequestMapping(value = "/protected/creation-sujet2", method = RequestMethod.POST)
	public String afficheFormateur2(Model model, @RequestParam Integer title) {

		verificationRolesAndSetIdUtilisateur();
		System.out.println("admin ? " + isAdmin + " apprenant ? " + isApprenant + " formateur ? " + isFormateur
				+ " id : " + idUtilisateur);
		
		if (idUtilisateur==null)return "redirect:/public/connexion";
		if (isApprenant)
			return "redirect:/public/accessDenied";
		
		Matiere mat = new Matiere();
		mat.setIdMatiere(title);

		model.addAttribute("themes", themeService.findThemesByMat(mat));

		return "/protected/creation-sujet2";
	}

	@RequestMapping(value = "/protected/liste-question-select", method = RequestMethod.POST)
	public String afficheFListeQuestions(Model model, @RequestParam List<Integer> title) {

		verificationRolesAndSetIdUtilisateur();
		System.out.println("admin ? " + isAdmin + " apprenant ? " + isApprenant + " formateur ? " + isFormateur
				+ " id : " + idUtilisateur);
		
		if (idUtilisateur==null)return "redirect:/public/connexion";
		if (isApprenant)
			return "redirect:/public/accessDenied";
		
		List<Question> questions = new ArrayList<Question>();

		System.err.println(title);

		// recuperation des ids
		List<Integer> questIds = title;
		// iteration dans la liste d'ids
		for (int i = 0; i < questIds.size(); i++) {
			// creation de theme et set l'id du theme
			Theme theme = new Theme();
			theme.setIdTheme(questIds.get(i));
			// creation de list avec toute les quest pour tel theme
			List<Question> Questionsbytheme = questionService.QuestionsByTheme(theme);
			// ajout des quest du theme a la liste de quest des themes selectionnés
			questions.addAll(Questionsbytheme);
		}

		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("apprenant", isApprenant);
		model.addAttribute("admin", isAdmin);
		model.addAttribute("formateur", isFormateur);
		model.addAttribute("questions", questions);

		return "/protected/liste-question-select";
	}

	@RequestMapping(value = "/protected/creation-sujet-gen", method = RequestMethod.GET)
	public String generateSubject(Model model) {

		verificationRolesAndSetIdUtilisateur();
		System.out.println("admin ? " + isAdmin + " apprenant ? " + isApprenant + " formateur ? " + isFormateur
				+ " id : " + idUtilisateur);
		
		if (idUtilisateur==null)return "redirect:/public/connexion";
		if (isApprenant)
			return "redirect:/public/accessDenied";

		return "/protected/creation-sujet-gen";
	}

	@RequestMapping(value = "/protected/creation-sujet-manu", method = RequestMethod.POST)
	public String creationQuesionnaire(Model model, @RequestParam List<Integer> ok) {

		verificationRolesAndSetIdUtilisateur();
		System.out.println("admin ? " + isAdmin + " apprenant ? " + isApprenant + " formateur ? " + isFormateur
				+ " id : " + idUtilisateur);
		
		if (idUtilisateur==null)return "redirect:/public/connexion";
		if (isApprenant)
			return "redirect:/public/accessDenied";
		
		List<Question> questions = new ArrayList<Question>();

		// recuperation des ids
		List<Integer> questIds = ok;
		// iteration dans la liste d'ids
		for (int i = 0; i < questIds.size(); i++) {
			// creation de la question et set l'id question
			Optional<Question> questionOp = questionService.findById(questIds.get(i));
			Question question = questionOp.get();
			// ajout de la quest a la liste de quests selectionnées
			questions.add(question);
		}

		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("apprenant", isApprenant);
		model.addAttribute("admin", isAdmin);
		model.addAttribute("formateur", isFormateur);
		model.addAttribute("questions", questions);
		model.addAttribute("sujet", sujet);

		return "/protected/creation-sujet-manu";
	}

	@RequestMapping(value = "/protected/creation-sujet-post", method = RequestMethod.POST)
	public String validationQuesionnaire(Model model, @RequestParam List<Integer> list, String nom,
			String description) {

		verificationRolesAndSetIdUtilisateur();
		System.out.println("admin ? " + isAdmin + " apprenant ? " + isApprenant + " formateur ? " + isFormateur
				+ " id : " + idUtilisateur);
		
		if (idUtilisateur==null)return "redirect:/public/connexion";
		if (isApprenant)
			return "redirect:/public/accessDenied";
		
		List<Question> questions = new ArrayList<Question>();
		HashSet<Theme> themes = new HashSet<Theme>();
		Matiere matiere = new Matiere();
		Sujet sujet = new Sujet();
		Question question = new Question();
		System.err.println(nom);
		sujet.setNom(nom);
		sujet.setdescriptionSujet(description);

		System.err.println(list);

		// recuperation des ids
		List<Integer> questIds = list;
		// iteration dans la liste d'ids
		for (int i = 0; i < questIds.size(); i++) {
			// creation de la question et set l'id question
			Optional<Question> questionOp = questionService.findById(questIds.get(i));
			question = questionOp.get();

			// ajout de la quest a la liste de quests selectionnÃ©es
			questions.add(question);
		}

		for (int j = 0; j < questions.size(); j++) {
			Theme theme = questions.get(j).getTheme();
			matiere = questions.get(j).getTheme().getMatiere();
			themes.add(theme);

			sujet.setMatiere(matiere);
			// ajout question et sujet a l'objet SujetQuestion puis save
			SujetQuestion sujetQuestion = new SujetQuestion();
			sujetQuestion.setSujet(sujet);
			sujetQuestion.setQuestion(questions.get(j));
			sujetQuestionService.save(sujetQuestion);
		}

		return "redirect:/protected/liste-sujet";
	}

	@RequestMapping(value = "/protected/liste-sujet", method = RequestMethod.GET)
	public String validationQuesionnaire2(Model model) {

		verificationRolesAndSetIdUtilisateur();
		System.out.println("admin ? " + isAdmin + " apprenant ? " + isApprenant + " formateur ? " + isFormateur
				+ " id : " + idUtilisateur);
		
		if (idUtilisateur==null)return "redirect:/public/connexion";
		if (isApprenant)
			return "redirect:/public/accessDenied";
		
		List<Sujet> sujets = sujetService.sujets();
		List<SujetDto> sujetsDto = new ArrayList<SujetDto>();
		HashSet<Theme> themesDuSujet = new HashSet<Theme>();
		
		//pour chaque sujet
		for (int j = 0; j < sujets.size(); j++) {
			//creation et insertion de donner dans le sujetdto puis ajout a la liste de dto
			SujetDto dto = new SujetDto();
			dto.setIdSujet(sujets.get(j).getIdSujet());
			dto.setNom(sujets.get(j).getNom());
			dto.setDescriptionSujet(sujets.get(j).getdescriptionSujet());
			dto.setMatiere(sujets.get(j).getMatiere());
			dto.setNbnotes(sujets.get(j).getNbnotes());
			dto.setNoteMoyenne(sujets.get(j).getNoteMoyenne());
			
			//recherche des questions pour le sujet par la table d'asso sujetquestion
			List<SujetQuestion> sujetQuestionsDuSujet = sujetQuestionService.findBySujet(sujets.get(j));
			
			//recuperation des themes des questions dans un hashset (donc pas de doublons)
			for (int i = 0; i < sujetQuestionsDuSujet.size(); i++) {
				Theme themeDuSujet = sujetQuestionsDuSujet.get(i).getQuestion().getTheme();
				themesDuSujet.add(themeDuSujet);
			}
			System.err.println(themesDuSujet);

			Set<Theme> themeMem = new HashSet<Theme>();
			//cast du hashset en list
			List<Theme> castHashsetThemes = new ArrayList<Theme>(themesDuSujet);
			for (int k = 0; k < castHashsetThemes.size(); k++) {
				themeMem.add(castHashsetThemes.get(k));
			}
			
			dto.setTheme(themeMem);
			
			//ajout de la liste des themes au sujet
			sujetsDto.add(dto);
			themesDuSujet.clear();
		}

		model.addAttribute("sujets", sujetsDto);

		return "/protected/liste-sujet";
	}

	@RequestMapping(value = "/protected/liste-promotion-select", method = RequestMethod.POST)
	public String affichePromotion(Model model, @RequestParam Integer sujetSelect) {

		verificationRolesAndSetIdUtilisateur();
		System.out.println("admin ? " + isAdmin + " apprenant ? " + isApprenant + " formateur ? " + isFormateur
				+ " id : " + idUtilisateur);
		
		if (idUtilisateur==null)return "redirect:/public/connexion";
		if (isApprenant)
			return "redirect:/public/accessDenied";
		
		List<Promotion> promotions = promotionService.promotions();
		Organisation org = promotions.get(0).getOrganisation();
		String nomOrganisation = org.getName();
		Optional<Sujet> sujetOp = sujetService.findById(sujetSelect);
		Sujet sujet = sujetOp.get();

		isAdmin = false;
		isFormateur = false;
		isApprenant = false;
		isConnectBoolean = true;

		System.err.println(nomOrganisation);
		System.err.println(sujet.getNom());

		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("apprenant", isApprenant);
		model.addAttribute("admin", isAdmin);
		model.addAttribute("formateur", isFormateur);
		model.addAttribute("promotions", promotions);
		model.addAttribute("organisation", nomOrganisation);
		model.addAttribute("sujet", sujet);

		return "/protected/liste-promotion-select";

	}

	@RequestMapping(value = "/protected/envoi-sujet", method = RequestMethod.POST)
	public String envoiSujet(Model model, @RequestParam List<Integer> promotions, Sujet sujetSelect) {

		verificationRolesAndSetIdUtilisateur();
		System.out.println("admin ? " + isAdmin + " apprenant ? " + isApprenant + " formateur ? " + isFormateur
				+ " id : " + idUtilisateur);
		
		if (idUtilisateur==null)return "redirect:/public/connexion";
		if (isApprenant)
			return "redirect:/public/accessDenied";

		System.err.println("test ctrleur envoi sujet");

		for (int i = 0; i < promotions.size(); i++) {
			System.err.println(promotions.get(i));
		}

		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("apprenant", isApprenant);
		model.addAttribute("admin", isAdmin);
		model.addAttribute("formateur", isFormateur);
//		model.addAttribute("sujet", sujetSelect);

		return "/protected/liste-promotion";

	}

	@RequestMapping(value = "/protected/modification-sujet/{idSujet}", method = RequestMethod.GET)
	public String modificationSujet(Model model, @PathVariable(name = "idSujet") String idSujet) {
		System.err.println(">>>>>>>> dto : " + themesCtrlMem);
		
		verificationRolesAndSetIdUtilisateur();
		System.out.println("admin ? " + isAdmin + " apprenant ? " + isApprenant + " formateur ? " + isFormateur
				+ " id : " + idUtilisateur);
		
		if (idUtilisateur==null)return "redirect:/public/connexion";
		if (isApprenant)
			return "redirect:/public/accessDenied";

		boolean tableisActive = true;
		// recuperation de l'id sujet
		Integer idSuj = Integer.parseInt(idSujet);

		// recuperation du sujet grace a l'id sujet
		Optional<Sujet> sujetOp = sujetService.findById(idSuj);
		Sujet sujet = sujetOp.get();

		List<SujetQuestion> sujetQuestions = sujetQuestionService.findBySujet(sujet);
		List<Question> questionsDuSujet = new ArrayList<Question>();
		Set<Theme> themesDuSujetSet = new HashSet<Theme>();

		// recuperation des questions du sujet, + recuperation des themes des questions
		for (int i = 0; i < sujetQuestions.size(); i++) {
			Question questMem = sujetQuestions.get(i).getQuestion();
			questionsDuSujet.add(questMem);
		}

		List<Theme> themes = new ArrayList<Theme>();
		// recuperation de la matiere et recuperation des themes de la matiere
		Matiere matiere = questionsDuSujet.get(0).getTheme().getMatiere();
		themes = themeService.findThemesByMat(matiere);

		if (themesCtrlMem.isEmpty()) {
			for (int i = 0; i < sujetQuestions.size(); i++) {
				Question questMem = sujetQuestions.get(i).getQuestion();
				themesDuSujetSet.add(questMem.getTheme());
				tableisActive = true;
			}
			System.err.println("(empty) contenu themesDuSujet : " + themesDuSujetSet);
		} else {
			for (int i = 0; i < themesCtrlMem.size(); i++) {
				Theme themeMem = themesCtrlMem.get(i);
				themesDuSujetSet.add(themeMem);
			}
			System.err.println("(not empty) contenu themesDuSujet : " + themesDuSujetSet);

			themesCtrlMem.clear();
			System.err.println(">>>>>> contenu themesCtrlMem : " + themesCtrlMem);
			tableisActive = false;
		}

		List<Theme> themesDuSujet = new ArrayList<Theme>(themesDuSujetSet);

		// recuperation des questions par themes et ajout dans une liste de l'ensemble
		// des questions des themes
		List<Question> questions = new ArrayList<Question>();
		for (int j = 0; j < themesDuSujet.size(); j++) {
			List<Question> questionsDuTheme = questionService.QuestionsByTheme(themesDuSujet.get(j));
			questions.addAll(questionsDuTheme);
		}

		Collections.sort(questionsDuSujet);
		Collections.sort(questions);
		System.err.println("verif list themesDuSujet : " + themesDuSujet);

		model.addAttribute("sujet", sujet);
		model.addAttribute("questionsDuSujet", questionsDuSujet);
		model.addAttribute("questions", questions);
		model.addAttribute("themesDuSujet", themesDuSujet);
		model.addAttribute("themes", themes);
		model.addAttribute("tableisActive", tableisActive);

		return "/protected/modification-sujet";
	}

	@RequestMapping(value = "/protected/validation-modification-sujet", method = RequestMethod.POST)
	public String validationModificationSujet(Model model, @ModelAttribute Sujet sujet,
			@RequestParam(value = "themeSelect") List<Integer> themeSelect,
			@RequestParam(value = "questionSelect") List<Integer> questionSelect, String action) {
		
		verificationRolesAndSetIdUtilisateur();
		System.out.println("admin ? " + isAdmin + " apprenant ? " + isApprenant + " formateur ? " + isFormateur
				+ " id : " + idUtilisateur);
		
		if (idUtilisateur==null)return "redirect:/public/connexion";
		if (isApprenant)
			return "redirect:/public/accessDenied";

		System.err.println(sujet.getdescriptionSujet());
		Optional<Sujet> sujetOp = sujetService.findById(sujet.getIdSujet());
		Sujet sujetObj = sujetOp.get();

		sujetObj.setNom(sujet.getNom());
		sujetObj.setdescriptionSujet(sujet.getdescriptionSujet());

		if (action.equals("update")) {
			System.err.println(">>>>> if update");

			// recuperation des questions
			List<Question> questions = new ArrayList<Question>();
			for (int j = 0; j < questionSelect.size(); j++) {
				Optional<Question> questionMemOp = questionService.findById(questionSelect.get(j));
				Question questionMem = questionMemOp.get();
				questions.add(questionMem);
			}

			List<SujetQuestion> lSQ = sujetQuestionService.findBySujet(sujetObj);
			for (SujetQuestion sq : lSQ) {
				sq.setSujet(null);
				sq.setQuestion(null);
				sujetQuestionService.save(sq);
				sujetQuestionService.delete(sq);
			}

			// creation du sujetQuestion puis ajout des questions pour le sujet
			for (int i = 0; i < questions.size(); i++) {
				SujetQuestion sujetQuestionMem = new SujetQuestion();
				sujetQuestionMem.setQuestion(questions.get(i));
				sujetQuestionMem.setSujet(sujetObj);
				sujetObj.getSujetQuestions().add(sujetQuestionMem);
				sujetQuestionService.save(sujetQuestionMem);
			}

			sujetService.save(sujetObj);

			// gestion de la suppression des questions du sujet qui n'ont pas été
			// selectionnées

			return "redirect:/protected/liste-sujet";
		} else {
			System.err.println(">>>>> else refresh");

			// recuperation des themes
			List<Theme> themes = new ArrayList<Theme>();
			for (int k = 0; k < themeSelect.size(); k++) {
				Optional<Theme> themeMemOp = themeService.findById(themeSelect.get(k));
				Theme themeMem = themeMemOp.get();
				themes.add(themeMem);
			}

			themesCtrlMem.addAll(themes);
			System.err.println(">>>>> dto : before : " + themesCtrlMem);

			return "redirect:/protected/modification-sujet/" + sujet.getIdSujet();
		}
	}

}
