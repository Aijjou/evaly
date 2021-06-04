package controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import model.Apprenant;
import model.Promotion;
import service.ApprenantService;
import service.PromotionService;

@Controller
public class ApprenantController {

	Boolean isConnectBoolean = false;
	Boolean isAdmin = false;
	Boolean isFormateur = false;
	Boolean isApprenant = false;

	@Autowired
	PromotionService promotionService;
	@Autowired
	ApprenantService apprenantService;

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
		
		List<Apprenant> apprenants = apprenantService.ApprenantsByPromotion(pro);
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
}
