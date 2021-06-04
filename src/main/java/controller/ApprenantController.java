package controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.Apprenant;
import service.ApprenantService;
import service.ExamenService;

@Controller
public class ApprenantController {

	Boolean isConnectBoolean = false;
	Boolean isAdmin = false;
	Boolean isFormateur = false;
	Boolean isApprenant = false;

	@Autowired
	ApprenantService apprenantService;
	@Autowired
	ExamenService examenService;

	@RequestMapping(value = "/protected/liste-eleve", method = RequestMethod.GET)
	public String afficheApprenant(Model model) {

		List<Apprenant> apprenants = apprenantService.apprenants();
		String nomFormation = " ";
		Boolean premierNom = true;
		isAdmin = false;
		isFormateur = false;
		isApprenant = false;
		isConnectBoolean = true;
		
		for (Apprenant apprenant : apprenants) {

			if (apprenant.getPromotion() != null) {
				apprenant.setNomPromoString(apprenant.getPromotion().getNom());
				if (premierNom) {
					nomFormation = apprenant.getPromotion().getNom();
					premierNom = false;
				}
			}
		}

		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("apprenants", apprenants);
		model.addAttribute("formation", nomFormation);
		model.addAttribute("apprenant", isApprenant);
		model.addAttribute("admin", isAdmin);
		model.addAttribute("formateur", isFormateur);

		return "/protected/liste-eleve";

	}
}
