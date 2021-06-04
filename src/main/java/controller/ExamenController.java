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
import org.springframework.web.bind.annotation.PathVariable;
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
public class ExamenController {

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
	PromotionService promotionService;
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

	@RequestMapping(value = "protected/liste-examen", method = RequestMethod.GET)
	public String afficheExamen(Model model) {

		isAdmin = false;
		isFormateur = false;
		isApprenant = false;
		isConnectBoolean = true;

		List<Examen> examens = examenService.examens();
		model.addAttribute("examens", examens);

		model.addAttribute("connexion", isConnectBoolean);

		model.addAttribute("apprenant", isApprenant);
		model.addAttribute("admin", isAdmin);
		model.addAttribute("formateur", isFormateur);

		return "protected/liste-examen";

	}

	@RequestMapping(value = "protected/creation-examen", method = RequestMethod.GET)
	public String creationExamen(ExamenDto examen, Model model) {
		ExamenDto edto = new ExamenDto();
		// edto.setDateExamen(new Date());
		edto.setTitre("Titre");
		edto.setDureeExamen(60);

		model.addAttribute("examen", edto);
		model.addAttribute("sujets", sujetService.sujets());
		model.addAttribute("promotions", promotionService.promotions());

		return "protected/creation-examen";

	}

	@RequestMapping(value = "/protected/creation-examen-sub", method = RequestMethod.POST)
	public String creationExamenSubmit(@ModelAttribute ExamenDto examen, Model model) {
		System.out.println(examen.getDateExamen() + " " + examen.getTitre() + " " + examen.getSujet() + " "
				+ examen.getDureeExamen() + " " + examen.getPromotion());
		if (examen.getDateExamen() == null || examen.getTitre() == null || examen.getSujet() == null
				|| examen.getDureeExamen() == null || examen.getPromotion() == null) {
			model.addAttribute("examen", examen);
			model.addAttribute("sujets", sujetService.sujets());
			model.addAttribute("promotions", promotionService.promotions());
			return "protected/creation-examen";
		}
		Examen nve = new Examen();

		System.out.println(examen.getDateExamen());
		nve.setTitre(examen.getTitre());
		nve.setDureeExamen(examen.getDureeExamen());
		Optional<Promotion> promo = promotionService.findById(examen.getPromotion());
		nve.setPromotion(promo.get());
		Optional<Sujet> sujet = sujetService.findById(examen.getSujet());
		nve.setSujet(sujet.get());

		for (int i = 0; i < 10; i++) {
			System.out.println(examen.getDateExamen());
		}
		LocalDateTime dateTime = LocalDateTime.parse(examen.getDateExamen());
		for (int i = 0; i < 10; i++) {
			System.out.println(dateTime);
		}
		Date out = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
		for (int i = 0; i < 10; i++) {
			System.out.println(out);
		}
		nve.setDateExamen(out);

		examenService.save(nve);


		return "redirect:/protected/liste-examen";
	}

	@RequestMapping(value = "protected/questionnaire/{idExamen}", method = RequestMethod.GET)
	public String passageExamen(Model model, @PathVariable(name = "idExamen") String idExamen) {

		// Vérification d'un résultat existant
		//
		// Find ResultatExamenByApprenantAndExamen
		// si trouvé redirect liste-examen // else affichage
		//
		
		
		Integer idex = Integer.parseInt(idExamen);
		Optional<Examen> examenopt = examenService.findById(idex);
		Examen examen = examenopt.get();

		QuestionnaireDto qdto = new QuestionnaireDto(examen);
		qdto.questionsExam(examen);
		ArrayList<QuestionDto> questions = qdto.getQuestions();
		model.addAttribute("qdto", qdto);
		model.addAttribute("questions", questions);
		System.out.println(qdto);
		System.out.println(qdto);
		System.out.println(qdto);

		return "protected/questionnaire";
	}

	@RequestMapping(value = "/protected/questionnaire-sub", method = RequestMethod.POST)
	public String passageExamenSub(@ModelAttribute QuestionnaireDto qu, Model model, @RequestParam List<Integer> ok) {

		Integer idapprenant = 2;

		Optional<Apprenant> a = apprenantService.findById(idapprenant);
		Apprenant app = a.get();
		Optional<Examen> e = examenService.findById(qu.getIdExamen());
		Examen exa = e.get();
		List<ReponseApprenantExamen> listrae = new ArrayList<ReponseApprenantExamen>();

		for (Integer i : ok) {
			Optional<Reponse> re = reponseService.findById(i);
			Reponse rep = re.get();
			ReponseApprenantExamen rae = new ReponseApprenantExamen();
			rae.setApprenant(app);
			rae.setExamen(exa);
			rae.setReponse(rep);
			rae.setQuestion(rep.getQuestion());
			rae.setSujet(exa.getSujet());
			listrae.add(rae);

			reponseApprenantExamenService.save(rae);
		}

		// Calcul note

		Sujet sujet = exa.getSujet();
		Set<SujetQuestion> sq = sujet.getSujetQuestions();
		List<Question> lq = new ArrayList<Question>();
		for (SujetQuestion sqq : sq) {
			Question q = sqq.getQuestion();
			lq.add(q);
		}
		Integer totalcoeff = 0;
		Integer points = 0;
		for (Question q : lq) {
			for (int i = 0; i < 2; i++)
				System.out.println(q);
			totalcoeff += q.getCoefficient();
			for (int i = 0; i < 2; i++)
				System.out.println("ID" + q.getIdQuestion() + "TOTAL COEFF :" + totalcoeff);
			Set<Reponse> lr = q.getReponses();
			List<Integer> bonnesreponses = new ArrayList<Integer>();
			for (Reponse rep : lr) {
				if (rep.getIsBonneReponse() == true)
					bonnesreponses.add(rep.getIdReponse());
			}
			for (int i = 0; i < 2; i++)
				System.out.println("REPONSES BONNES :" + bonnesreponses);
			List<Integer> reponsesdelapprenant = new ArrayList<Integer>();
			for (ReponseApprenantExamen rep : listrae) {
				if (rep.getQuestion().getIdQuestion().equals(q.getIdQuestion())) {
					reponsesdelapprenant.add(rep.getReponse().getIdReponse());
				}
			}
			for (int i = 0; i < 2; i++)
				System.out.println("REPONSES APPRENANTS :" + reponsesdelapprenant);
			
			
			// Vérification bonnes réponses
			Boolean point = true;
			
				for (Integer i : bonnesreponses) {
					System.out.println("FOR "+i);
					if (reponsesdelapprenant.contains(i) == false) {
						point = false;
						System.out.println("FOR "+i+" "+point);
					}
				}
			
			for (int i = 0; i < 2; i++)
				System.out.println("COMPARE :" + reponsesdelapprenant.size() + " " + bonnesreponses.size());
			if (reponsesdelapprenant.size() != bonnesreponses.size()) {
				point = false;
			}

			for (int i = 0; i < 2; i++)
				System.out.println("CALCUL STATS :" + q.getIdQuestion() + " POINT ???" + point);

			// Statistiques
			q.setNbnotes(q.getNbnotes() + 1);
			if (point == true) {
				points += q.getCoefficient();
				q.setNbreussite(q.getNbreussite() + 1);
				
			}
			
			System.out.println("CALCUL " + 100 * (q.getNbreussite()) / q.getNbnotes());
			System.out.println("CALCUL +100*" + "(" + q.getNbreussite() + ")/" + q.getNbnotes());
			q.setTauxreussite(100 * (q.getNbreussite()) / q.getNbnotes());
			
			
			for (int i = 0; i < 2; i++)
				System.out.println(" Q :" + q.getIdQuestion() + " COEFF" + q.getCoefficient());
			for (int i = 0; i < 2; i++)
				System.out.println(" NOTES :" + q.getNbnotes() + " REUSSIES :" + q.getNbreussite() + " TAUX :"
						+ q.getTauxreussite());
			questionService.save(q);
			for (int i = 0; i < 2; i++)
				System.out.println(" POST SAVE :" + q.getIdQuestion());
		}

		Double note = (double) ((20 * points) / totalcoeff);

		// Mise à jour sujet

		Double moy = sujet.getNoteMoyenne();
		Integer nombrenotes = sujet.getNbnotes();
		Double total = moy * nombrenotes;
		sujet.setNbnotes(sujet.getNbnotes() + 1);
		nombrenotes += 1;
		Double nvmoy = (total + note) / nombrenotes;
		sujet.setNoteMoyenne(nvmoy);

		sujetService.save(sujet);

		// Ajout Resultat
		ResultatExamen re = new ResultatExamen();
		re.setApprenant(app);
		re.setExamen(exa);
		re.setNote(note);
		resultatExamenService.save(re);


		return "redirect:/protected/liste-resultat";
	}
}
