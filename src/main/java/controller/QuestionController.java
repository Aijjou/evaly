package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dto.QuestionDto;
import model.Formateur;
import model.FormateurMatiere;
import model.Matiere;
import model.Question;
import model.Reponse;
import model.Theme;
import service.FormateurService;
import service.MatiereService;
import service.QuestionService;
import service.ReponseService;
import service.SujetService;
import service.ThemeService;

@Controller
public class QuestionController {

	@Autowired
	QuestionService questionService;

	@Autowired
	ThemeService themeService;

	@Autowired
	MatiereService matiereService;

	@Autowired
	SujetService sujetService;

	@Autowired
	ReponseService reponseService;

	@Autowired
	FormateurService formateurService;

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

	@RequestMapping(value = "protected/creation-question", method = RequestMethod.GET)
	public String creationQuestion(QuestionDto question, Model model) {

		verificationRolesAndSetIdUtilisateur();
		System.out.println("admin ? " + isAdmin + " apprenant ? " + isApprenant + " formateur ? " + isFormateur
				+ " id : " + idUtilisateur);
		
		if (idUtilisateur==null)return "redirect:/public/connexion";
		if (isApprenant)
			return "redirect:/public/accessDenied";

		if (isAdmin) {
			List<Theme> listth = themeService.themes();
			model.addAttribute("listtheme", listth);

			List<Matiere> listmatiere = matiereService.matieres();
			model.addAttribute("listmatiere", listmatiere);
		}

		if (isFormateur) {
			Optional<Formateur> fmto = formateurService.findById(idUtilisateur);

			Formateur fmt = fmto.get();

			List<Theme> listthfinal = new ArrayList<Theme>();
			List<Matiere> listmatiere = new ArrayList<Matiere>();

			Set<FormateurMatiere> formateurMatieres = fmt.getFormateurMatieres();

			for (FormateurMatiere m : formateurMatieres) {
				listmatiere.add(m.getMatiere());
				List<Theme> listthmat = themeService.findThemesByMat(m.getMatiere());
				listthfinal.addAll(listthmat);
			}
			model.addAttribute("listmatiere", listmatiere);
			model.addAttribute("listtheme", listthfinal);
		}

		QuestionDto qdto = new QuestionDto();
		qdto.setDescriptionQuestion("");
		qdto.setIsQcm(true);
		qdto.setRep1br("true");
		model.addAttribute("question", qdto);

		return "/protected/creation-question";
	}

	@RequestMapping(value = "/protected/edition-question/{idQuestion}", method = RequestMethod.GET)
	public String editionQuestion(QuestionDto question, Model model,
			@PathVariable(name = "idQuestion") String idQuestion) {

		verificationRolesAndSetIdUtilisateur();
		System.out.println("admin ? " + isAdmin + " apprenant ? " + isApprenant + " formateur ? " + isFormateur
				+ " id : " + idUtilisateur);
		if (isApprenant)
			return "redirect:/public/accessDenied";

		if (isAdmin) {
			List<Theme> listth = themeService.themes();
			model.addAttribute("listtheme", listth);

			List<Matiere> listmatiere = matiereService.matieres();
			model.addAttribute("listmatiere", listmatiere);
		}

		if (isFormateur) {
			Optional<Formateur> fmto = formateurService.findById(idUtilisateur);

			Formateur fmt = fmto.get();

			List<Theme> listthfinal = new ArrayList<Theme>();
			List<Matiere> listmatiere = new ArrayList<Matiere>();

			Set<FormateurMatiere> formateurMatieres = fmt.getFormateurMatieres();

			for (FormateurMatiere m : formateurMatieres) {
				listmatiere.add(m.getMatiere());
				List<Theme> listthmat = themeService.findThemesByMat(m.getMatiere());
				listthfinal.addAll(listthmat);
			}
			model.addAttribute("listmatiere", listmatiere);
			model.addAttribute("listtheme", listthfinal);
		}

		Integer idq = Integer.parseInt(idQuestion);

		Optional<Question> edited = questionService.findById(idq);
		Question edit = edited.get();

		QuestionDto qdto = new QuestionDto();

		qdto.setIdQuestion(edit.getIdQuestion());

		qdto.setDescriptionQuestion(edit.getDescriptionQuestion());

		qdto.setTheme(edit.getTheme().getIdTheme());

		qdto.setIsQcm(edit.getIsQcm());

		qdto.setNbnotes((edit.getNbnotes()));

		qdto.setTauxreussite((edit.getTauxreussite()));

		qdto.setToReset(false);

		qdto.setCoefficient(edit.getCoefficient());

		Set<Reponse> listrep = edit.getReponses();
		ArrayList<Reponse> arrayrep = new ArrayList<Reponse>();

		for (Reponse r : listrep) {
			arrayrep.add(r);
		}
		Collections.sort(arrayrep);

		for (Reponse r : arrayrep) {
			System.out.println(r.getIdReponse());
			System.out.println(r.getDescriptionReponse());
			System.out.println(r.getIsBonneReponse());
		}

		if (edit.getIsQcm()) {
			int size = listrep.size();
			if (size >= 1) {
				qdto.setRep1(arrayrep.get(0).getDescriptionReponse());
				if (arrayrep.get(0).getIsBonneReponse())
					qdto.setRep1br("true");
				else
					qdto.setRep1br("false");
			}
			if (size >= 2) {
				qdto.setRep2(arrayrep.get(1).getDescriptionReponse());
				if (arrayrep.get(1).getIsBonneReponse())
					qdto.setRep2br("true");
				else
					qdto.setRep2br("false");
			}
			if (size >= 3) {
				qdto.setRep3(arrayrep.get(2).getDescriptionReponse());
				if (arrayrep.get(2).getIsBonneReponse())
					qdto.setRep3br("true");
				else
					qdto.setRep3br("false");
			}
			if (size >= 4) {
				qdto.setRep4(arrayrep.get(3).getDescriptionReponse());
				if (arrayrep.get(3).getIsBonneReponse())
					qdto.setRep4br("true");
				else
					qdto.setRep4br("false");
			}
		}

		if (edit.getIsQcm() == false) {
			if (arrayrep.get(0).getDescriptionReponse() == "Vrai" && arrayrep.get(0).getIsBonneReponse()) {
				qdto.setIsVrai(true);
			} else
				qdto.setIsVrai(false);
		}

		model.addAttribute("question", qdto);

		return "/protected/edition-question";
	}

	@RequestMapping(value = "/protected/creation-question-sub", method = RequestMethod.POST)
	public String creationQuestionSubmit(@ModelAttribute QuestionDto question, Model model) {

		verificationRolesAndSetIdUtilisateur();
		System.out.println("admin ? " + isAdmin + " apprenant ? " + isApprenant + " formateur ? " + isFormateur
				+ " id : " + idUtilisateur);
		if (isApprenant)
			return "redirect:/protected/home";

		System.out.println();
		if (question.getIsQcm()) {
			System.out.println("------Test rep3-------");
			System.out.print("Test réponses 3 rep3null");
			System.out.println(question.getRep3() == null);
			System.out.print("Test réponses 3 rep3empty" + question.getRep3().isEmpty());
			System.out.println(question.getRep3().isEmpty());
			System.out.println("-------------");
		}
		for (int i = 0; i < 2; i++)
			System.out.println("Theme : " + question.getTheme());
		for (int i = 0; i < 2; i++)
			System.out.println("Nouveau Theme : " + question.getNvthemebool());
		for (int i = 0; i < 2; i++)
			System.out.println("Nouveau Theme Nom : " + question.getNvtheme());
		Question nvq = new Question();
		Reponse n1 = new Reponse();
		Reponse n2 = new Reponse();
		Reponse n3 = new Reponse();
		Reponse n4 = new Reponse();

		// Gestion Theme
		Theme theme = new Theme();
		// Creation
		System.out.println("Test entrée création thème :" + question.getNvthemebool() + " " + question.getIdMatiere());
		System.out.println("Test entrée création thème :" + question.getNvthemebool() + " " + question.getIdMatiere());

		if (question.getNvthemebool() != null && question.getIdMatiere() != null) {
			for (int i = 0; i < 2; i++)
				System.out.println("Nouveau Theme Matiere Id : " + question.getIdMatiere());
			for (int i = 0; i < 2; i++)
				System.out.println("Nouveau Theme Nom : " + question.getNvtheme());
			theme.setNom(question.getNvtheme());
			Optional<Matiere> recupmatiere = matiereService.findById(question.getIdMatiere());
			Matiere mat = recupmatiere.get();
			for (int i = 0; i < 2; i++)
				System.out.println("Nouveau Theme Matiere récupérée : " + mat);
			theme.setMatiere(mat);
			themeService.save(theme);
			for (int i = 0; i < 2; i++)
				System.out.println("Nouveau Theme sauvegardé : " + theme);
		}
		// Recup theme existant
		else if (question.getNvthemebool() == null) {
			Optional<Theme> themerec = themeService.findById(question.getTheme());
			theme = themerec.get();
		}

		for (int i = 0; i < 10; i++)
			System.out.println("Theme to apply  : " + theme);

		nvq.setCoefficient(question.getCoefficient());
		nvq.setTheme(theme);
		// Recup Description
		nvq.setDescriptionQuestion(question.getDescriptionQuestion());

		// Recup QCM / Not QCM
		if (question.getIsQcm()) {
			nvq.setIsQcm(true);
		} else if (question.getIsQcm() != true) {
			nvq.setIsQcm(false);
		}

		questionService.save(nvq);
		// Sauvegarde question + retour id
		System.out.println("-------------");
		System.out.println(question.getRep1() + " " + question.getRep1br());
		System.out.println(question.getRep2() + " " + question.getRep2br());
		System.out.println("-------------");
		if (question.getIsQcm()) {
		System.out.println("Test réponses 3 rep3null" + question.getRep3() == null + "test isempty"
				+ question.getRep3().isEmpty());
		}
		if (question.getIsQcm()) {
			if (!question.getRep1().isEmpty()) {
				n1.setDescriptionReponse(question.getRep1());
				if (question.getRep1br().equals("true"))
					n1.setIsBonneReponse(true);
				else
					n1.setIsBonneReponse(false);
				n1.setQuestion(nvq);
				reponseService.save(n1);
			}
			if (!question.getRep2().isEmpty()) {
				n2.setDescriptionReponse(question.getRep2());
				if (question.getRep2br() != null)
					n2.setIsBonneReponse(true);
				else
					n2.setIsBonneReponse(false);
				n2.setQuestion(nvq);
				reponseService.save(n2);
			}
			if (!question.getRep3().isEmpty()) {
				n3.setDescriptionReponse(question.getRep3());
				if (question.getRep3br() != null)
					n3.setIsBonneReponse(true);
				else
					n3.setIsBonneReponse(false);
				n3.setQuestion(nvq);
				reponseService.save(n3);
			}
			if (!question.getRep4().isEmpty()) {
				n4.setDescriptionReponse(question.getRep4());
				if (question.getRep4br() != null)
					n4.setIsBonneReponse(true);
				else
					n4.setIsBonneReponse(false);
				n4.setQuestion(nvq);
				reponseService.save(n4);
			}
		}
		// Traitement Vrai/Faux
		if (question.getIsQcm() == false) {
			n1.setDescriptionReponse("Vrai");
			if (question.getIsVrai())
				n1.setIsBonneReponse(true);
			else
				n1.setIsBonneReponse(false);
			n1.setQuestion(nvq);
			reponseService.save(n1);

			n2.setDescriptionReponse("Faux");
			if (question.getIsVrai() != false)
				n2.setIsBonneReponse(true);
			else
				n2.setIsBonneReponse(false);
			n2.setQuestion(nvq);
			reponseService.save(n2);
		}

		System.out.println(nvq);

		return "redirect:/protected/liste-question";
	}

	@RequestMapping(value = "/protected/edition-question-sub", method = RequestMethod.POST)
	public String editionQuestionSubmit(@ModelAttribute QuestionDto question, Model model) {

		verificationRolesAndSetIdUtilisateur();
		System.out.println("admin ? " + isAdmin + " apprenant ? " + isApprenant + " formateur ? " + isFormateur
				+ " id : " + idUtilisateur);
		if (isApprenant)
			return "redirect:/protected/home";

		System.out.println("------Test rep3 EDIT -------");
		System.out.print("Test réponses 3 rep3null");
		System.out.println(question.getRep3() == null);
		System.out.print("Test réponses 3 rep3empty" + question.getRep3().isEmpty());
		System.out.println(question.getRep3().isEmpty());
		System.out.println("-------------");
		Optional<Question> edited = questionService.findById(question.getIdQuestion());
		Question edit = edited.get();

		// Gestion Theme
		Theme theme = new Theme();
		// Creation
		System.out.println(question.getNvthemebool() + " MAT " + question.getIdMatiere());
		System.out.println("REP1BR " + question.getRep1br());
		System.out.println("REP2BR " + question.getRep2br());
		System.out.println("REP3BR " + question.getRep3br());
		System.out.println("REP4BR " + question.getRep4br());

		if (question.getNvthemebool() != null && question.getIdMatiere() != null) {

			for (int i = 0; i < 10; i++)
				System.out.println("ENTREE CREATION THEME");

			System.out.println(question.getNvtheme() + " MAT " + question.getIdMatiere());
			theme.setNom(question.getNvtheme());
			Optional<Matiere> recupmatiere = matiereService.findById(question.getIdMatiere());
			theme.setMatiere(recupmatiere.get());
			themeService.save(theme);
		}

		// Recup theme existant
		else if (question.getNvthemebool() == null) {
			Optional<Theme> themerec = themeService.findById(question.getTheme());
			theme = themerec.get();
			System.out.println(theme);
			System.out.println(theme);
			System.out.println(theme);
		}
		edit.setTheme(theme);

		// Recup Description
		edit.setDescriptionQuestion(question.getDescriptionQuestion());

		// Recup QCM / Not QCM
		if (question.getIsQcm()) {
			edit.setIsQcm(true);
		} else if (question.getIsQcm() != true) {
			edit.setIsQcm(false);
		}

		// Coefficient
		edit.setCoefficient(question.getCoefficient());

		// Traitement Reset

		if (question.getToReset()) {
			edit.setNbnotes(0);
			edit.setTauxreussite(100);
		}

		questionService.save(edit);
		// Sauvegarde question

		Set<Reponse> listrep = edit.getReponses();
		ArrayList<Reponse> arrayrep = new ArrayList<Reponse>();

		for (Reponse r : listrep) {
			System.out.println("Reponse edit :" + r);
			arrayrep.add(r);
		}
		Collections.sort(arrayrep);

		for (Reponse r : arrayrep) {
			r.setQuestion(null);
			System.out.println(r.getDescriptionReponse());
			System.out.println(r.getIsBonneReponse());
			reponseService.save(r);
		}

		// Combien de réponses avant ?
		// Si moins de réponses après, delete les réponses en trop
		// Si plus, créer davantage
		// Delete all / Create 2+ ?

		Reponse n1 = new Reponse();
		Reponse n2 = new Reponse();
		Reponse n3 = new Reponse();
		Reponse n4 = new Reponse();

		if (question.getIsQcm()) {

			if (!question.getRep1().isEmpty()) {
				n1.setDescriptionReponse(question.getRep1());
				if (question.getRep1br() != null)
					n1.setIsBonneReponse(true);
				else
					n1.setIsBonneReponse(false);

				n1.setQuestion(edit);
				reponseService.save(n1);
			}
			if (!question.getRep2().isEmpty()) {
				n2.setDescriptionReponse(question.getRep2());
				if (question.getRep2br() != null)
					n2.setIsBonneReponse(true);
				else
					n2.setIsBonneReponse(false);

				n2.setQuestion(edit);
				reponseService.save(n2);
			}
			if (!question.getRep3().isEmpty()) {
				n3.setDescriptionReponse(question.getRep3());
				if (question.getRep3br() != null)
					n3.setIsBonneReponse(true);
				else
					n3.setIsBonneReponse(false);

				n3.setQuestion(edit);
				reponseService.save(n3);
			}
			if (!question.getRep4().isEmpty()) {
				n4.setDescriptionReponse(question.getRep4());
				if (question.getRep4br() != null)
					n4.setIsBonneReponse(true);
				else
					n4.setIsBonneReponse(false);

				n4.setQuestion(edit);
				reponseService.save(n4);
			}
		}

		if (question.getIsQcm() == false) {

			n1.setDescriptionReponse("Vrai");
			if (question.getIsVrai())
				n1.setIsBonneReponse(true);
			else
				n1.setIsBonneReponse(false);

			n1.setQuestion(edit);
			reponseService.save(n1);

			n2.setDescriptionReponse("Faux");
			if (question.getIsVrai() != false)
				n2.setIsBonneReponse(true);
			else
				n2.setIsBonneReponse(false);

			n2.setQuestion(edit);
			reponseService.save(n2);
		}
		return "redirect:/protected/liste-question";
	}

	@RequestMapping(value = "/protected/liste-question", method = RequestMethod.GET)
	public String listeQuestion(Model model) {

		verificationRolesAndSetIdUtilisateur();
		System.out.println("admin ? " + isAdmin + " apprenant ? " + isApprenant + " formateur ? " + isFormateur
				+ " id : " + idUtilisateur);

		if (isApprenant)
			return "redirect:/public/accessDenied";

		if (isAdmin) {
			List<Question> questions = questionService.questions();
			model.addAttribute("questions", questions);
		}

		if (isFormateur) {
			Optional<Formateur> fmto = formateurService.findById(idUtilisateur);

			Formateur fmt = fmto.get();

			List<Question> questions = new ArrayList<Question>();
			List<Matiere> listmatiere = new ArrayList<Matiere>();
			Set<FormateurMatiere> formateurMatieres = fmt.getFormateurMatieres();

			for (FormateurMatiere m : formateurMatieres) {
				listmatiere.add(m.getMatiere());
				List<Question> questionsmat = questionService.QuestionsByMatiere(m.getMatiere());
				questions.addAll(questionsmat);
			}
			model.addAttribute("questions", questions);
		}

		return "/protected/liste-question";
	}
}
