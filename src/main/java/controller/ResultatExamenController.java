package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.Apprenant;
import model.Examen;
import model.Formateur;
import model.PromotionFormateur;
import model.ResultatExamen;
import service.ApprenantService;
import service.ExamenService;
import service.FormateurService;
import service.QuestionService;
import service.ReponseApprenantExamenService;
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
	FormateurService formateurService;
	@Autowired
	ReponseApprenantExamenService reponseApprenantExamenService;
	@Autowired
	ResultatExamenService resultatExamenService;

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

	@RequestMapping(value = "/protected/liste-resultat", method = RequestMethod.GET)
	public String afficheExamen(Model model) {

		verificationRolesAndSetIdUtilisateur();
		System.out.println("admin ? " + isAdmin + " apprenant ? " + isApprenant + " formateur ? " + isFormateur
				+ " id : " + idUtilisateur);

		if (isAdmin) {
			// FIND ALL
			List<ResultatExamen> resultatsexamens = resultatsexamenService.resultatsExamens();
			model.addAttribute("resultats", resultatsexamens);
		}

		if (isFormateur) {
			List<ResultatExamen> resultatsformateur = new ArrayList<ResultatExamen>();
			List<Apprenant> apprenants = new ArrayList<Apprenant>();
			Optional<Formateur> fmto = formateurService.findById(idUtilisateur);
			Formateur fmt = fmto.get();

			Set<PromotionFormateur> promoFormateur = fmt.getPromotionFormateurs();

			for (PromotionFormateur m : promoFormateur) {
				Set<Apprenant> apprenantspromo = m.getPromotion().getApprenants();
				apprenants.addAll(apprenantspromo);
			}

			for (Apprenant a : apprenants) {
				List<ResultatExamen> resultsappr = resultatExamenService.findByApprenant(a);
				if (resultsappr != null && !resultsappr.isEmpty()) {
					resultatsformateur.addAll(resultatExamenService.findByApprenant(a));
				}
			}
			model.addAttribute("resultats", resultatsformateur);
		}
		if (isApprenant) {
			Optional<Apprenant> apto = apprenantService.findById(idUtilisateur);
			Apprenant apt = apto.get();
			
			List<ResultatExamen> resultsappr = resultatExamenService.findByApprenant(apt);
			model.addAttribute("resultats", resultsappr);
		}
		return "/protected/liste-resultat";
	}
}
