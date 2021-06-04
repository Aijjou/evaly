package controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import model.Apprenant;
import model.Organisation;
import model.Promotion;
import service.ApprenantService;
import service.PromotionService;

@Controller
public class PromotionController {

	Boolean isConnectBoolean = false;
	Boolean isAdmin = false;
	Boolean isFormateur = false;
	Boolean isApprenant = false;

	@Autowired
	PromotionService promotionService;

	@Autowired
	ApprenantService apprenantService;

	@RequestMapping(value = "/protected/liste-promotion", method = RequestMethod.GET)
	public String affichePromotion(Model model) {

		List<Promotion> promotions = promotionService.promotions();
		Organisation org = promotions.get(0).getOrganisation();
		String nomOrganisation = org.getName();
		isAdmin = false;
		isFormateur = false;
		isApprenant = false;
		isConnectBoolean = true;
		
		System.err.println(nomOrganisation);

		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("promotions", promotions);
		model.addAttribute("organisation", nomOrganisation);
		model.addAttribute("apprenant", isApprenant);
		model.addAttribute("admin", isAdmin);
		model.addAttribute("formateur", isFormateur);

		return "/protected/liste-promotion";

	}
	
	@RequestMapping(value = "/protected/liste-eleve", method = RequestMethod.POST)
	public String afficheEleves(Model model, @RequestParam Integer promoSelect) {

		Optional<Promotion> promoOp = promotionService.findById(promoSelect);
		Promotion promotion = promoOp.get();
		String nomFormation = promotion.getNom();
		
		
		List<Apprenant> eleves = apprenantService.apprenantsByPromotion(promotion);
		
		for (Apprenant apprenant : eleves) {

			if (apprenant.getPromotion() != null) {
				apprenant.setNomPromoString(apprenant.getPromotion().getNom());
			}
		}
		
		
		isAdmin = false;
		isFormateur = false;
		isApprenant = false;
		isConnectBoolean = true;

		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("apprenant", isApprenant);
		model.addAttribute("admin", isAdmin);
		model.addAttribute("formateur", isFormateur);
		model.addAttribute("apprenants", eleves);
		model.addAttribute("formation", nomFormation);

		return "/protected/liste-eleve";

	}
}
