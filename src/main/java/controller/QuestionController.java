package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import dto.QuestionDto;
import model.Matiere;
import model.Question;
import model.Reponse;
import model.Theme;
import service.MatiereService;
import service.QuestionService;
import service.ReponseService;
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
	ReponseService reponseService;
	
	Boolean isConnectBoolean = false;
	Boolean isAdmin = true;
	Boolean isFormateur = true;
	Boolean isApprenant = false;

	@RequestMapping(value = "/protected/creation-question", method = RequestMethod.GET)
	public String creationQuestion(QuestionDto question, Model model) {
		
		 isConnectBoolean = false;
		 isAdmin = true;
		 isFormateur = true;
		 isApprenant = false;
		 
			model.addAttribute("connexion", isConnectBoolean);
			model.addAttribute("apprenant", isApprenant);
			model.addAttribute("admin", isAdmin);
			model.addAttribute("formateur", isFormateur);
		
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
	
	@RequestMapping(value = "/protected/edition-question", method = RequestMethod.GET, params = { "idQuestion" })
	public String editionQuestion(QuestionDto question, Model model, @RequestParam(name = "idQuestion", required = true) String idQuestion) {
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
			if (size>=1) {
				qdto.setRep1(arrayrep.get(0).getDescriptionReponse());
				if (arrayrep.get(0).getIsBonneReponse()) qdto.setRep1br("true");
				else qdto.setRep1br("false");
			}
			if (size>=2) {
				qdto.setRep2(arrayrep.get(1).getDescriptionReponse());
				if (arrayrep.get(1).getIsBonneReponse()) qdto.setRep2br("true");
				else qdto.setRep2br("false");
			}
			if (size>=3) {
				qdto.setRep3(arrayrep.get(2).getDescriptionReponse());
				if (arrayrep.get(2).getIsBonneReponse()) qdto.setRep3br("true");
				else qdto.setRep3br("false");
			}
			if (size>=4) {
				qdto.setRep4(arrayrep.get(3).getDescriptionReponse());
				if (arrayrep.get(3).getIsBonneReponse()) qdto.setRep4br("true");
				else qdto.setRep4br("false");
			}
		}
		
		if (edit.getIsQcm()==false) {
			if (arrayrep.get(0).getDescriptionReponse()=="Vrai" && arrayrep.get(0).getIsBonneReponse()) {
				qdto.setIsVrai(true);
				}
				else qdto.setIsVrai(false);
			}	
		
		
		
		model.addAttribute("question", qdto);

		List<Theme> listth = themeService.themes();
		model.addAttribute("listtheme", listth);

		List<Matiere> listmatiere = matiereService.matieres();
		
		model.addAttribute("listmatiere", listmatiere);

		return "/protected/edition-question";
	}

	@RequestMapping(value = "/protected/creation-question-sub", method = RequestMethod.POST)
	public String creationQuestionSubmit(@ModelAttribute QuestionDto question, Model model) {

		Question nvq = new Question();
		Reponse n1 = new Reponse();
		Reponse n2 = new Reponse();
		Reponse n3 = new Reponse();
		Reponse n4 = new Reponse();

		//Gestion Theme
		Theme theme = new Theme();
		//Creation
		if (question.getNvthemebool() == "true" && question.getIdMatiere()!=null) {

			theme.setNom(question.getNvtheme());
			Optional<Matiere> recupmatiere = matiereService.findById(question.getIdMatiere());
			theme.setMatiere(recupmatiere.get());
			themeService.save(theme);
		}
		//Recup theme existant
		else if (question.getNvthemebool() == "false") {
			Optional<Theme> themerec = themeService.findById(question.getTheme());
			theme=themerec.get();
		}
		
		nvq.setTheme(theme);
		// Recup Description
		nvq.setDescriptionQuestion(question.getDescriptionQuestion());

		//Recup QCM / Not QCM
		if (question.getIsQcm()) {
			nvq.setIsQcm(true);
		} else if (question.getIsQcm() != true) {
			nvq.setIsQcm(false);
		}
		
		questionService.save(nvq);
		// Sauvegarde question + retour id
		System.out.println("-------------");
		System.out.println(question.getRep1()+" "+question.getRep2br());
		System.out.println("-------------");

		if (question.getIsQcm()) {

			if (question.getRep1() != null) {
				n1.setDescriptionReponse(question.getRep1());
				if (question.getRep1br().equals("true"))
					n1.setIsBonneReponse(true);
				else
					n1.setIsBonneReponse(false);
				
				n1.setQuestion(nvq);
				reponseService.save(n1);
			}
			if (question.getRep2() != null) {
				n2.setDescriptionReponse(question.getRep2());
				if (question.getRep2br()!= null)
					n2.setIsBonneReponse(true);
				else 
					n2.setIsBonneReponse(false);
				
				n2.setQuestion(nvq);
				reponseService.save(n2);
			}
			if (question.getRep3() != null) {
				n3.setDescriptionReponse(question.getRep3());
				if (question.getRep3br()!= null)
					n3.setIsBonneReponse(true);
				else
					n3.setIsBonneReponse(false);
				
				n3.setQuestion(nvq);
				reponseService.save(n3);
			}
			if (question.getRep4() != null) {
				n4.setDescriptionReponse(question.getRep4());
				if (question.getRep4br()!= null)
					n4.setIsBonneReponse(true);
				else
					n4.setIsBonneReponse(false);
				
				n4.setQuestion(nvq);
				reponseService.save(n4);
			}
		}
		
		if (question.getIsQcm()==false) {

				n1.setDescriptionReponse("Vrai");
				if (question.getIsVrai())
					n1.setIsBonneReponse(true);
				else
					n1.setIsBonneReponse(false);
				
				n1.setQuestion(nvq);
				reponseService.save(n1);
		
				n2.setDescriptionReponse("Faux");
				if (question.getIsVrai()!=false)
					n2.setIsBonneReponse(true);
				else
					n2.setIsBonneReponse(false);
				
				n2.setQuestion(nvq);
				reponseService.save(n2);
			}
		
		System.out.println(nvq);
		System.out.println(nvq);
		System.out.println(nvq);
		
		// Traitement Vrai/Faux

		return "/protected/liste-question";
	}
	
	@RequestMapping(value = "/protected/edition-question-sub", method = RequestMethod.POST)
	public String editionQuestionSubmit(@ModelAttribute QuestionDto question, Model model) {

		Optional<Question> edited = questionService.findById(question.getIdQuestion());
		Question edit = edited.get();
		
	
		//Gestion Theme
		Theme theme = new Theme();
		//Creation
		if (question.getNvthemebool().equals("true") && question.getIdMatiere()!=null) {

			theme.setNom(question.getNvtheme());
			Optional<Matiere> recupmatiere = matiereService.findById(question.getIdMatiere());
			theme.setMatiere(recupmatiere.get());
			themeService.save(theme);
		}
		

		//Recup theme existant
		else if (question.getNvthemebool() == null) {
			Optional<Theme> themerec = themeService.findById(question.getTheme());
			theme=themerec.get();
			System.out.println(theme);
			System.out.println(theme);
			System.out.println(theme);
		}
		edit.setTheme(theme);
		
		// Recup Description
		edit.setDescriptionQuestion(question.getDescriptionQuestion());

		//Recup QCM / Not QCM
		if (question.getIsQcm()) {
			edit.setIsQcm(true);
		} else if (question.getIsQcm() != true) {
			edit.setIsQcm(false);
		}
		
		//Coefficient
		edit.setCoefficient(question.getCoefficient());
		
		// Traitement Reset
		
		if (question.getToReset()) {
			edit.setNbnotes(0);
			edit.setTauxreussite(100);
		}
		
		questionService.save(edit);
		// Sauvegarde question 
		
//		Set<Reponse> listrep = edit.getReponses();
//		ArrayList<Reponse> arrayrep = new ArrayList<Reponse>();
//
//		for (Reponse r : listrep) {
//			arrayrep.add(r);
//		}
//		Collections.sort(arrayrep);
//		
//		for (Reponse r : arrayrep) {
//			System.out.println(r.getIdReponse());
//			System.out.println(r.getDescriptionReponse());
//			System.out.println(r.getIsBonneReponse());
//		}
//		
//		int nbrep = arrayrep.size();
		
		// Combien de réponses avant ? 
		// Si moins de réponses après, delete les réponses en trop
		// Si plus, créer davantage
		// Delete all / Create 2+ ?
		
		Reponse n1 = new Reponse();
		Reponse n2 = new Reponse();
		Reponse n3 = new Reponse();
		Reponse n4 = new Reponse();
		
		if (question.getIsQcm()) {

			if (question.getRep1() != null) {
				n1.setDescriptionReponse(question.getRep1());
				if (question.getRep1br().equals("true"))
					n1.setIsBonneReponse(true);
				else
					n1.setIsBonneReponse(false);
				
				n1.setQuestion(edit);
				reponseService.save(n1);
			}
			if (question.getRep2() != null) {
				n2.setDescriptionReponse(question.getRep2());
				if (question.getRep2br()!= null)
					n2.setIsBonneReponse(true);
				else 
					n2.setIsBonneReponse(false);
				
				n2.setQuestion(edit);
				reponseService.save(n2);
			}
			if (question.getRep3() != null) {
				n3.setDescriptionReponse(question.getRep3());
				if (question.getRep3br()!= null)
					n3.setIsBonneReponse(true);
				else
					n3.setIsBonneReponse(false);
				
				n3.setQuestion(edit);
				reponseService.save(n3);
			}
			if (question.getRep4() != null) {
				n4.setDescriptionReponse(question.getRep4());
				if (question.getRep4br()!= null)
					n4.setIsBonneReponse(true);
				else
					n4.setIsBonneReponse(false);
				
				n4.setQuestion(edit);
				reponseService.save(n4);
			}
		}
		
		if (question.getIsQcm()==false) {

				n1.setDescriptionReponse("Vrai");
				if (question.getIsVrai())
					n1.setIsBonneReponse(true);
				else
					n1.setIsBonneReponse(false);
				
				n1.setQuestion(edit);
				reponseService.save(n1);
		
				n2.setDescriptionReponse("Faux");
				if (question.getIsVrai()!=false)
					n2.setIsBonneReponse(true);
				else
					n2.setIsBonneReponse(false);
				
				n2.setQuestion(edit);
				reponseService.save(n2);
			}
		



		return "/protected/liste-question";
	}

	@RequestMapping(value = "/protected/liste-question", method = RequestMethod.GET)
	public String listeQuestion(Model model) {

		List<Question> questions = questionService.questions();
		model.addAttribute("questions", questions);
		
		return "/protected/liste-question";
	}
}
