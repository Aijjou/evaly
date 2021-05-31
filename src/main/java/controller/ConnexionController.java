package controller;

import org.aspectj.weaver.patterns.IScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ConnexionController {
	
	
	Boolean isConnectBoolean = false;
	Boolean isAdmin = false;
	Boolean isFormateur = false;
	Boolean isApprenant = false;
	
	
	@RequestMapping(value = "/public/connexion", method = RequestMethod.GET)
	public String connexion(Model model) {
		
		isConnectBoolean = false;
		isAdmin = false;
		
		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("admin", isAdmin);
		
		
		return "/public/connexion";
	}
	
	

}
