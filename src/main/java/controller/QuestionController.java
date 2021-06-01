package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dto.QuestionDto;
import model.Matiere;
import model.Question;
import model.Reponse;
import model.Theme;
import service.MatiereService;
import service.QuestionService;
import service.ThemeService;

@Controller
public class QuestionController {

	@Autowired
	QuestionService questionService;

	@Autowired
	ThemeService themeService;

	@Autowired
	MatiereService matiereService;

	@RequestMapping(value = "/protected/creation-question", method = RequestMethod.GET)
	public String creationQuestion(QuestionDto question, Model model) {
		QuestionDto qdto = new QuestionDto();
		qdto.setDescriptionQuestion("dsc");
		qdto.setIsQcm(true);
		qdto.setRep1br("true");
		model.addAttribute("question", qdto);

		List<Theme> listth = themeService.themes();
		model.addAttribute("listtheme", listth);

		List<Matiere> listmatiere = matiereService.matieres();
		model.addAttribute("listmatiere", listmatiere);

		return "/protected/creation-question";
	}

	@PostMapping(path = "/protected/creation-question-sub")
	public String creationQuestionSubmit(@ModelAttribute QuestionDto question, Model model) {

		Question nvq = new Question();
		Reponse n1 = new Reponse();
		Reponse n2 = new Reponse();
		Reponse n3 = new Reponse();
		Reponse n4 = new Reponse();

		Theme theme = new Theme();
		if (question.getNvthemebool() == "true") {

			theme.setNom(question.getNvtheme());
			Matiere recupmatiere = new Matiere();
			// Find Matiere
			theme.setMatiere(recupmatiere);
			// Sauvegarde Theme
		}

		nvq.setDescriptionQuestion(question.getDescriptionQuestion());

		if (question.getIsQcm()) {
			nvq.setIsQcm(true);
		} else if (question.getIsQcm() != true) {
			nvq.setIsQcm(false);
		}

		// Sauvegarde question + retour id

		// Retour objet question sauvegardÃ©

		if (question.getIsQcm()) {

			if (question.getRep1() != null) {
				n1.setDescriptionReponse(question.getRep1());
				if (question.getRep1br().equals("true"))
					n1.setIsBonneReponse(true);
				else
					n1.setIsBonneReponse(false);
				
				// Set question
			}
		}
		
		// Traitement Vrai/Faux

		return "/protected/liste-question";
	}

	@RequestMapping(value = "/protected/liste-question", method = RequestMethod.GET)
	public String listeQuestion(Model model) {

		return "/protected/liste-question";
	}
}
