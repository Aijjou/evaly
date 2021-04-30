package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.Apprenant;
import service.ApprenantService;

@Controller
public class ApprenantController {

	@Autowired
	ApprenantService apprenantService;

	@RequestMapping(value = "/protected/liste-eleve", method = RequestMethod.GET)
	public String afficheApprenant(Model model) {

		List<Apprenant> apprenants = apprenantService.apprenants();
		String nomFormation = " ";
		Boolean premierNom = true;

		for (Apprenant apprenant : apprenants) {

			if (apprenant.getPromotion() != null) {
				apprenant.setNomPromoString(apprenant.getPromotion().getNom());
				if (premierNom) {
					nomFormation = apprenant.getPromotion().getNom();
					premierNom = false;
				}
			}
		}

		model.addAttribute("apprenants", apprenants);
		model.addAttribute("formation", nomFormation);

		return "/protected/liste-eleve";

	}

	@RequestMapping(value = "/protected/liste-apprenant", method = RequestMethod.GET)
	public List<Apprenant> afficheListApprenant() {
		List<Apprenant> apprenants = apprenantService.apprenants();
		return apprenants;
	}

}
