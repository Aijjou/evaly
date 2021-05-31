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
	
	
	@Autowired
	GroupeService groupeService;

	@RequestMapping(value = "admin/groupe", method = RequestMethod.GET)
	public String afficheGroupe(Model model) {

		List<GroupeFormateur> groupesFormateurs = groupeService.getListGroupeFormateur();
		isConnectBoolean = true;
		isAdmin = true;
		
		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("admin", isAdmin);
		
		model.addAttribute("groupes", groupesFormateurs);

		return "admin/groupe";

	}

}
