package controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.Apprenant;
import model.Organisation;
import model.Promotion;
import service.PromotionService;

@Controller
public class PromotionController {

	Boolean isConnectBoolean = false;
	Boolean isAdmin = false;
	Boolean isFormateur = false;
	Boolean isApprenant = false;

	@Autowired
	PromotionService promotionService;

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
}
