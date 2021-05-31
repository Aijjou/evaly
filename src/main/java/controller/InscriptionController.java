package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class InscriptionController {

	Boolean isConnectBoolean = false;
	Boolean isAdmin = false;
	
	
	@RequestMapping(value = "/public/verification-code", method = RequestMethod.GET)
	public String verificationCode(Model model) {
		
		isConnectBoolean = false;
		isAdmin = false;
		
		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("admin", isAdmin);
		
		return "/public/verification-code";
	}
	
	@RequestMapping(value = "/public/inscription-final", method = RequestMethod.GET)
	public String inscriptionFinal(Model model) {
		isConnectBoolean = false;
		isAdmin = false;
		
		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("admin", isAdmin);
		
	
		
		return "/public/inscription-final";
	}
	
	
	
	@RequestMapping(value = "/admin/inscription-formateur-admin", method = RequestMethod.GET)
	public String inscriptionFormateurAdmin(Model model) {
		
		isConnectBoolean = true;
		isAdmin = true;
		
		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("admin", isAdmin);
		
		return "/admin/inscription-formateur-admin";
	}
	
	@RequestMapping(value = "/public/inscription-admin", method = RequestMethod.GET)
	public String inscriptionAdmin(Model model) {
		
		isConnectBoolean = false;
		isAdmin = false;
		
		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("admin", isAdmin);
		
		return "/public/inscription-admin";
	}
	
	
	@RequestMapping(value = "/protected/inscription-apprenant-formateur", method = RequestMethod.GET)
	public String inscriptionApprenantFormateur(Model model) {
		
		
		isConnectBoolean =true;
		isAdmin = true;
		
		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("admin", isAdmin);
		
		return "/protected/inscription-apprenant-formateur";
	}
	
	
	
	
	
	
	
	
}
