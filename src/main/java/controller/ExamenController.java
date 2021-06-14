package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import model.Formateur;
import model.FormateurMatiere;
import model.Matiere;
import model.Promotion;
import model.PromotionFormateur;
import model.Question;
import model.Reponse;
import model.ReponseApprenantExamen;
import model.ResultatExamen;
import model.Sujet;
import model.SujetQuestion;
import model.Theme;
import service.ApprenantService;
import service.ExamenService;
import service.FormateurMatiereService;
import service.FormateurService;
import service.PromotionService;
import service.QuestionService;
import service.ReponseApprenantExamenService;
import service.ReponseService;
import service.ResultatExamenService;
import service.SujetService;

@Controller
@Scope("session")
public class ExamenController {

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
	@Autowired
	FormateurService formateurService;
	@Autowired
	FormateurMatiereService formateurMatiereService;

	Boolean isAdmin = false;
	Boolean isFormateur = false;
	Boolean isApprenant = false;
	Boolean isConnectBoolean = true;
	Integer idUtilisateur = null;
	
	String message=null;

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

	@RequestMapping(value = "/protected/liste-examen", method = RequestMethod.GET)
	public String afficheListeExamen(Model model) {

		verificationRolesAndSetIdUtilisateur();
		System.out.println("admin ? " + isAdmin + " apprenant ? " + isApprenant + " formateur ? " + isFormateur
				+ " id : " + idUtilisateur);

//		//TEST AS APPRENANT
//		isAdmin=false;
//		isApprenant=true;
//		idUtilisateur=3;

		if (isAdmin) {
			// FIND ALL
			List<Examen> examensAll = new ArrayList<Examen>();
			examensAll = examenService.examens();
			model.addAttribute("examens", examensAll);
		}

		if (isFormateur) {
			List<Examen> examensformateur = new ArrayList<Examen>();

			Optional<Formateur> fmto = formateurService.findById(idUtilisateur);
			Formateur fmt = fmto.get();

			Set<PromotionFormateur> promoFormateur = fmt.getPromotionFormateurs();

			for (PromotionFormateur m : promoFormateur) {
				Set<Examen> examenspromo = m.getPromotion().getExamens();
				examensformateur.addAll(examenspromo);
			}

			examensformateur = examenService.examens();
			model.addAttribute("examens", examensformateur);
		}

		if (isApprenant) {

			Optional<Apprenant> apto = apprenantService.findById(idUtilisateur);
			Apprenant apt = apto.get();

			Promotion promo = apt.getPromotion();

			Set<Examen> examensapprenantset = promo.getExamens();
			List<Examen> examensapt = new ArrayList<Examen>();
			List<Examen> examenalreadydone = new ArrayList<Examen>();
			examensapt.addAll(examensapprenantset);

			for (Examen e : examensapt) {
				List<ReponseApprenantExamen> le = reponseApprenantExamenService.findByApprenantAndExamen(apt, e);
				if (le.size() > 0)
					examenalreadydone.add(e);
			}

			examensapt.removeAll(examenalreadydone);

			model.addAttribute("examens", examensapt);
		}
		
		if(message!=null) {
			model.addAttribute("message", message);
			message=null;
		}
		else model.addAttribute("message","");
		

		return "/protected/liste-examen";

	}

	@RequestMapping(value = "/protected/creation-examen", method = RequestMethod.GET)
	public String creationExamen(ExamenDto examen, Model model) {
		ExamenDto edto = new ExamenDto();
		// edto.setDateExamen(new Date());
		edto.setTitre("Titre");
		edto.setDureeExamen(60);

		model.addAttribute("examen", edto);
		model.addAttribute("sujets", sujetService.sujets());
		model.addAttribute("promotions", promotionService.promotions());

		return "/protected/creation-examen";

	}

	@RequestMapping(value = "/protected/creation-examen-sub", method = RequestMethod.POST)
	public String creationExamenSubmit(@ModelAttribute ExamenDto examen, Model model) {

		verificationRolesAndSetIdUtilisateur();
		System.out.println("admin ? " + isAdmin + " apprenant ? " + isApprenant + " formateur ? " + isFormateur
				+ " id : " + idUtilisateur);

		System.out.println(examen.getDateExamen() + " " + examen.getTitre() + " " + examen.getSujet() + " "
				+ examen.getDureeExamen() + " " + examen.getPromotion());
		if (examen.getDateExamen() == null || examen.getTitre() == null || examen.getSujet() == null
				|| examen.getDureeExamen() == null || examen.getPromotion() == null) {
			model.addAttribute("examen", examen);
			model.addAttribute("sujets", sujetService.sujets());
			model.addAttribute("promotions", promotionService.promotions());
			return "protected/creation-examen";
		}

		if (isApprenant)
			return "redirect:/protected/home";

		if (isAdmin || isFormateur) {

			Examen nve = new Examen();

			// FORMATEUR PAR DEFAUT
			Optional<Formateur> f = formateurService.findById(1);
			nve.setFormateur(f.get());

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
				System.out.println("DATE GOING TO SAVE : " + out);
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String datetosave = sdf.format(out);
			nve.setDateExamenString(datetosave);
			nve.setDateExamen(out);
			Date fromstring = null;
			try {
				fromstring = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(datetosave);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int i = 0; i < 10; i++) {
				System.out.println("DATE GOING TO SAVE FROM STRING : " + fromstring);
			}
			examenService.save(nve);

			return "redirect:/protected/liste-examen";
		}
		return "redirect:/protected/liste-examen";
	}

	@RequestMapping(value = "/protected/questionnaire/{idExamen}", method = RequestMethod.GET)
	public String passageExamen(Model model, @PathVariable(name = "idExamen") String idExamen) {

		verificationRolesAndSetIdUtilisateur();
		System.out.println("admin ? " + isAdmin + " apprenant ? " + isApprenant + " formateur ? " + isFormateur
				+ " id : " + idUtilisateur);

		///// Passage as Utilisateur 2
		idUtilisateur=5;

		Integer idex = Integer.parseInt(idExamen);
		Optional<Examen> examenopt = examenService.findById(idex);
		Examen examen = examenopt.get();

		Integer idapprenant = idUtilisateur;
		Optional<Apprenant> a = apprenantService.findById(idapprenant);
		Apprenant app = a.get();

		// Vérification d'un résultat existant
		//
		// Find ResultatExamenByApprenantAndExamen
		// si trouvé redirect liste-examen // else affichage
		
		Date now = new Date();
		String depart = examen.getDateExamenString();
		String fin = null;
		Date datedebutexamen = null;
		try {
			datedebutexamen = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(depart);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Long departmillisec = datedebutexamen.getTime();
		Integer duree = examen.getDureeExamen();
		Long dureemillisec = (long) (duree * 60 * 1000);
		Date datefinexamen = new Date(departmillisec + dureemillisec);
		
		for (int i = 0; i < 10; i++) {
			System.out.println("DEPART" + depart + " FIN " + datefinexamen);
		}
		
		// Vérif si date actuelle comprise entre début et fin examen
		if (now.compareTo(datedebutexamen)<0) {
			System.err.print("REDIRECT LISTE EXAMEN - EXAMEN NON COMMENCE");
			message="L'examen n'a pas encore débuté.";
			return "redirect:/protected/liste-examen";
		}
		
		if (now.compareTo(datefinexamen)>0) {
			System.err.print("REDIRECT LISTE EXAMEN - EXAMEN TERMINE");
			message="L'examen est terminé.";
			return "redirect:/protected/liste-examen";
		}
		
		// Vérif si l'examen a déjà été passé
		List<ReponseApprenantExamen> lrae = reponseApprenantExamenService.findByApprenantAndExamen(app, examen);
		if (lrae.size()>0) {
			System.err.print("REDIRECT LISTE EXAMEN - REPONSES DEJA PRESENTES");
			message="Vous avez déjà effectué cet examen.";
			return "redirect:/protected/liste-examen";
		}
		
		String pattern = "yyyy-MM-dd HH:mm";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String timeleftstring = simpleDateFormat.format(datefinexamen);
		System.out.println(timeleftstring);

		Long nowmilli = now.getTime();
		Long finmilli = datefinexamen.getTime();
		Long timeleftmilli = finmilli - nowmilli;
		
		try {
			model.addAttribute("endDate", new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(timeleftstring));
			model.addAttribute("timeleftmilli", timeleftmilli);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		QuestionnaireDto qdto = new QuestionnaireDto(examen);
		qdto.questionsExam(examen);
		ArrayList<QuestionDto> questions = qdto.getQuestions();
		model.addAttribute("qdto", qdto);
		model.addAttribute("questions", questions);

		return "/protected/questionnaire";
	}

	@RequestMapping(value = "/protected/questionnaire-sub", method = RequestMethod.POST)
	public String passageExamenSub(@ModelAttribute QuestionnaireDto qu, Model model, @RequestParam List<Integer> ok) {

		verificationRolesAndSetIdUtilisateur();
		System.out.println("admin ? " + isAdmin + " apprenant ? " + isApprenant + " formateur ? " + isFormateur
				+ " id : " + idUtilisateur);

		///// Passage as Utilisateur 2
		idUtilisateur=5;
		Integer idapprenant=idUtilisateur;
		
		Optional<Apprenant> a = apprenantService.findById(idapprenant);
		Apprenant app = a.get();
		Optional<Examen> exap = examenService.findById(qu.getIdExamen());
		Examen exa = exap.get();
		
		Date now = new Date();
		String depart = exa.getDateExamenString();
		String fin = null;
		Date datedebutexamen = null;
		try {
			datedebutexamen = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(depart);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Long departmillisec = datedebutexamen.getTime();
		Integer duree = exa.getDureeExamen();
		Long dureemillisec = (long) (duree * 60 * 1000);
		Date datefinexamen = new Date(departmillisec + dureemillisec);
		Long datefinexamenlong = datefinexamen.getTime();
		Date datefinexamenplusuneminute = new Date(datefinexamenlong + 60000);
		if (now.compareTo(datedebutexamen)<0) {
			System.err.print("REDIRECT LISTE EXAMEN - EXAMEN NON COMMENCE");
			message="L'examen n'a pas encore débuté.";
			return "redirect:/protected/liste-examen";
		}
		
		if (now.compareTo(datefinexamenplusuneminute)>0) {
			System.err.print("REDIRECT LISTE EXAMEN - FIN EXAMEN DEPASSEE");
			message="L'heure de fin d'examen a été dépassée de plus d'une minute, les réponses sont ignorées.";
			return "redirect:/protected/liste-examen";
		}

		List<ReponseApprenantExamen> lrae = reponseApprenantExamenService.findByApprenantAndExamen(app, exa);
		if (lrae.size()>0) {
			System.err.print("REDIRECT LISTE EXAMEN - REPONSES DEJA PRESENTES");
			message="Vous avez déjà effectué cet examen.";
			return "redirect:/protected/liste-examen";
		}
		
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
				System.out.println("FOR " + i);
				if (reponsesdelapprenant.contains(i) == false) {
					point = false;
					System.out.println("FOR " + i + " " + point);
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
