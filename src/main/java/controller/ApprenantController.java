package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import dto.ApprenantDto;
import dto.ApprenantDtoFinal;
import model.Apprenant;
import model.Examen;
import model.Formateur;
import model.FormateurMatiere;
import model.Promotion;
import model.ResultatExamen;
import service.ApprenantService;
import service.ExamenService;
import service.FormateurService;
import service.PromotionService;

@Controller
public class ApprenantController {

	@Autowired
	PromotionService promotionService;
	@Autowired
	ApprenantService apprenantService;
	@Autowired
	ExamenService examenService;
	@Autowired
	FormateurService formateurService;

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
			} else isAdmin=false;
			
			if (role.getAuthority().equals("ROLE_APPRENANT")) {
				isApprenant = true;
				System.out.println("ROLE_APPRENANT");
			} else isApprenant=false;
			
			if (role.getAuthority().equals("ROLE_FORMATEUR")) {
				isFormateur = true;
				System.out.println("ROLE_FORMATEUR");
			} else isFormateur=false;
		});
		principal.UserPrincipal userPrincipal = (principal.UserPrincipal) auth.getPrincipal();
		idUtilisateur = userPrincipal.getId();
		System.err.println(" --- --- --- verificationRoles --- --- --- ");
	}
	
	@RequestMapping(value = "/protected/liste-apprenant", method = RequestMethod.GET)
	public String afficheApprenant(Model model) {

		List<Apprenant> apprenants = apprenantService.apprenants();
		String nomFormation = " ";
		Boolean premierNom = true;
		isAdmin = false;
		isFormateur = false;
		isApprenant = false;
		isConnectBoolean = true;
		model.addAttribute("promonom", "Liste de tout les apprenants");
		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("apprenants", apprenants);
		model.addAttribute("formation", nomFormation);
		model.addAttribute("apprenant", isApprenant);
		model.addAttribute("admin", isAdmin);
		model.addAttribute("formateur", isFormateur);

		return "/protected/liste-apprenant";

	}

	@RequestMapping(value = "/protected/liste-apprenant-promotion", method = RequestMethod.POST)
	public String afficheApprenantsPromo(Model model, @RequestParam Integer promoSelect) {

		Optional<Promotion> promo = promotionService.findById(promoSelect);
		Promotion pro = promo.get();

		List<Apprenant> apprenants = apprenantService.apprenantsByPromotion(pro);
		model.addAttribute("promonom", pro.getNom());
		Boolean premierNom = true;
		isAdmin = false;
		isFormateur = false;
		isApprenant = false;
		isConnectBoolean = true;

		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("apprenants", apprenants);
		model.addAttribute("apprenant", isApprenant);
		model.addAttribute("admin", isAdmin);
		model.addAttribute("formateur", isFormateur);

		return "/protected/liste-apprenant";

	}

	@RequestMapping(value = "protected/liste-examen-apprenant", method = RequestMethod.POST)
	public String afficheExamenApprenant(Model model, @RequestParam Integer idPromo) {

		isAdmin = false;
		isFormateur = false;
		isApprenant = false;
		isConnectBoolean = true;

		Optional<Promotion> promoOp = promotionService.findById(idPromo);
		Promotion promotion = promoOp.get();
		List<Examen> examensForPromo = examenService.examenByPromotion(promotion);

		model.addAttribute("examens", examensForPromo);
		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("apprenant", isApprenant);
		model.addAttribute("admin", isAdmin);
		model.addAttribute("formateur", isFormateur);

		return "protected/liste-examen";

	}

	@RequestMapping(value = "protected/delete-apprenant/{id}", method = RequestMethod.GET)
	public String deleteApprenant(Model model, @PathVariable("id") Integer idApprenant) {
		System.err.println(idApprenant);
		if (apprenantService.delete(idApprenant)) {
			return "redirect:/protected/liste-apprenant";
		}
		return "redirect:/protected/liste-apprenant";

	}

	@RequestMapping(value = "/protected/edit-apprenant/{id}", method = RequestMethod.GET)
	public String editApprenant(Model model, @PathVariable("id") Integer idApprenant) {

		boolean isModification = true;

		Apprenant apprenant = apprenantService.findById(idApprenant).get();

		System.err.println(" >>>>>>>  " + apprenant);

		ApprenantDto apprenantDto = new ApprenantDto(apprenant.getIdUtilisateur(), apprenant.getNom(),
				apprenant.getPrenom(), apprenant.getMail(), apprenant.getDateInscription(),
				apprenant.getPromotion().getIdPromotion());

		model.addAttribute("apprenantDto", apprenantDto);
		model.addAttribute("promotions", promotionService.promotions());
		model.addAttribute("isModification", isModification);

		return "/protected/inscription-apprenant-formateur";
	}

	@RequestMapping(value = "public/edit-apprenant-apprenant/{idApprenant}", method = RequestMethod.GET)
	public String editApprenantParApprenant(Model model, @PathVariable("idApprenant") Integer idApprenant) {

		boolean isModification = true;

		Apprenant apprenant = apprenantService.findById(idApprenant).get();

		System.err.println(" >>>>>>>  " + apprenant);

		ApprenantDtoFinal apprenantDtoFinal = new ApprenantDtoFinal(apprenant.getIdUtilisateur(), apprenant.getNom(),
				apprenant.getPrenom(), apprenant.getMail(), apprenant.getPassword(), apprenant.getDateInscription(),
				apprenant.getDateNaissance(), apprenant.getActive(), apprenant.getIsAdmin(),
				apprenant.getQuestionSecrete(), apprenant.getReponseSecrete(),
				apprenant.getPromotion().getIdPromotion(), null);
		

		model.addAttribute("apprenantDtoFinal", apprenantDtoFinal);
		model.addAttribute("isModification", isModification);

		return "/public/inscription-final-apprenant";
	}

	@RequestMapping(value = "/protected/info-utilisateur/{id}", method = RequestMethod.GET)
	public String afficheUnUtilisateur(Model model, @PathVariable("id") Integer idApprenant) {
		
		System.out.println("INFOS");
		isFormateur=true;
		idUtilisateur=1;
		
		Optional<Formateur> fo = null;
		Formateur forma = null;
		Set<FormateurMatiere> formatiere = null;
		List<Integer> indexmatiere = new ArrayList<Integer>();
		if (isFormateur) {
			fo = formateurService.findById(idUtilisateur);
			forma=fo.get();
			formatiere = forma.getFormateurMatieres();
			System.err.println("Resultats matiere du formateur");
			for (FormateurMatiere fm : formatiere) {
				indexmatiere.add(fm.getMatiere().getIdMatiere());
				System.err.println(fm.getMatiere());
			}
		}
		
		
		Apprenant apprenant = apprenantService.findById(idApprenant).get();
		Set<ResultatExamen> resultatExamens = apprenant.getResultatExamens();
		List<ResultatExamen> resultsfiltered = new ArrayList<ResultatExamen>();
		for (ResultatExamen re : resultatExamens) {
			if (indexmatiere.contains(re.getExamen().getMatiere().getIdMatiere())) {
				resultsfiltered.add(re);
			}
		}
		
		if (!resultsfiltered.isEmpty()) {
			model.addAttribute("results", resultsfiltered);
		}
		System.err.println("Resultats examens");
		System.err.println(resultsfiltered);
		Promotion promotion = apprenant.getPromotion();

		model.addAttribute("apprenant", apprenant);
		model.addAttribute("promotion", promotion);

		return "/protected/info-utilisateur";
	}

}
