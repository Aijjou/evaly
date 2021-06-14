package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import model.Apprenant;
import model.Formateur;
import model.FormateurMatiere;
import model.Matiere;
import model.Organisation;
import model.Promotion;
import model.PromotionFormateur;
import repository.PromotionFormateurRepository;
import service.ApprenantService;
import service.FormateurService;
import service.PromotionService;

@Controller
public class PromotionController {

	@Autowired
	PromotionService promotionService;

	@Autowired
	ApprenantService apprenantService;

	@Autowired
	FormateurService formateurService;

	@Autowired
	PromotionFormateurRepository promotionFormateurRepository;

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
				isFormateur = false;
				isApprenant = false;
				System.out.println("ROLE_ADMIN");
			}
			if (role.getAuthority().equals("ROLE_APPRENANT")) {
				isApprenant = true;
				isFormateur = false;
				isAdmin = false;
				System.out.println("ROLE_APPRENANT");
			}
			if (role.getAuthority().equals("ROLE_FORMATEUR")) {
				isFormateur = true;
				isApprenant = false;
				isAdmin = false;
				System.out.println("ROLE_FORMATEUR");
			}
		});
		principal.UserPrincipal userPrincipal = (principal.UserPrincipal) auth.getPrincipal();
		idUtilisateur = userPrincipal.getId();
		System.err.println(" --- --- --- verificationRoles --- --- --- ");
	}

	@RequestMapping(value = "/protected/liste-promotion", method = RequestMethod.GET)
	public String affichePromotion(Model model) {

		List<Promotion> promotions = new ArrayList<>();
		verificationRolesAndSetIdUtilisateur();

		if (isAdmin == true) {
			System.err.println("isAdmin");
			promotions = promotionService.promotions();
		}
		
		if (isFormateur == true) {

			List<Promotion> promotions2 = new ArrayList<>();

			List<PromotionFormateur> promotionFormateurs = promotionFormateurRepository
					.findByFormateur(formateurService.findById(idUtilisateur).get());

			promotionFormateurs.stream().forEach(promotionFormateur -> {
				promotions2.add(promotionFormateur.getPromotion());

			});
			promotions = promotions2;
		}
		if (isApprenant == true) {
			System.err.println("isApprenant");
			Apprenant apprenant = apprenantService.findById(idUtilisateur).get();
			Promotion promotion = promotionService.findById(apprenant.getPromotion().getIdPromotion()).get();
			promotions.add(promotion);
		}

		Organisation org = promotions.get(0).getOrganisation();
		String nomOrganisation = org.getName();

		System.err.println(nomOrganisation);

		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("promotions", promotions);
		model.addAttribute("organisation", nomOrganisation);

		return "/protected/liste-promotion";

	}

}
