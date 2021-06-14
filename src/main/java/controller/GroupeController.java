package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.GroupeFormateur;
import service.GroupeService;

@Controller
@Scope("session")
public class GroupeController {

	
	Boolean isConnectBoolean = false;
	Boolean isAdmin = false;
	Boolean isFormateur = false;
	Boolean isApprenant = false;
	
	
	@Autowired
	GroupeService groupeService;

	@RequestMapping(value = "/admin/groupe", method = RequestMethod.GET)
	public String afficheGroupe(Model model) {

	
		isFormateur = false;
		isApprenant = false;
		isConnectBoolean = true;
		isAdmin = true;
		
		List<GroupeFormateur> groupesFormateurs = groupeService.getListGroupeFormateur();
	
		
		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("apprenant", isApprenant);
		model.addAttribute("admin", isAdmin);
		model.addAttribute("formateur", isFormateur);
		
		model.addAttribute("groupes", groupesFormateurs);

		return "/admin/groupe";

	}

}
