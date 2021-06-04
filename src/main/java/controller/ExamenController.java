package controller;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dto.ExamenDto;
import dto.QuestionDto;
import model.Examen;
import model.Promotion;
import model.Sujet;
import service.ExamenService;
import service.PromotionService;
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


	@RequestMapping(value = "protected/liste-examen", method = RequestMethod.GET)
	public String afficheExamen(Model model) {

		
		isAdmin = false;
		isFormateur = false;
		isApprenant = false;
		isConnectBoolean = true;
		
		List<Examen> examens = examenService.examens();

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
		System.out.println(examen.getDateExamen()+" "+ examen.getTitre()+" "+ examen.getSujet()+" "+ 
				examen.getDureeExamen()+" "+ examen.getPromotion() );
		if (examen.getDateExamen()==null || examen.getTitre()==null || examen.getSujet()==null || 
				examen.getDureeExamen()==null || examen.getPromotion() ==null) {
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
		
		for (int i=0;i<10;i++) {
			System.out.println(examen.getDateExamen());
		}
		 LocalDateTime dateTime = LocalDateTime.parse(examen.getDateExamen());
		 for (int i=0;i<10;i++) {
				System.out.println(dateTime);
			}
		 Date out = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
		 for (int i=0;i<10;i++) {
				System.out.println(out);
			}
		 nve.setDateExamen(out);
		 
		examenService.save(nve);
		
		model.addAttribute("examens", examenService.examens());
		
		return "protected/liste-examen";
	}

}
