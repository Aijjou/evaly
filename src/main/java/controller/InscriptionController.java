package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import dto.AdminEtablissementDto;
import dto.UtilisateurDto;
import model.Utilisateur;
import repository.UtilisateurRepository;
import service.UtilisateurService;

@Controller
public class InscriptionController {

	Boolean isConnectBoolean = false;
	Boolean isAdmin = false;
	
	@Autowired
	UtilisateurRepository utilisateurRepository;
	
	@Autowired
	private UtilisateurService utilisateurService;
	
	
	

	
	
	@RequestMapping(value = "/public/verification-code", method = RequestMethod.GET)
	public String verificationCode(Model model) {
		return "/public/verification-code";
	}

	@RequestMapping(value = "/public/inscription-final", method = RequestMethod.GET)
	public String afficheInscriptionFinal(Model model) {

		return "/public/inscription-final";
	}

	@RequestMapping(value = "/public/inscription-final", method = RequestMethod.POST)
	public String inscriptionFinal(Model model, @Valid @ModelAttribute("compteCreateDao") UtilisateurDto utilisateurDto,
			BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {

		return "/public/inscription-final";
	}

	@RequestMapping(value = "/admin/inscription-formateur-admin", method = RequestMethod.GET)
	public String inscriptionFormateurAdmin(UtilisateurDto utilisateurDto, Model model) {

		isConnectBoolean = true;
		isAdmin = true;

		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("admin", isAdmin);

		return "/admin/inscription-formateur-admin";
	}

	@RequestMapping(value = "/admin/inscription-formateur-admin", method = RequestMethod.POST)
	public String registration(Model model, @Valid @ModelAttribute("utilisateurDto") UtilisateurDto utilisateurDto,
			BindingResult result,HttpServletRequest request, HttpServletResponse response) throws Exception {


		return "/admin/inscription-formateur-admin";
	}

	@RequestMapping(value = "/public/inscription-admin", method = RequestMethod.GET)
	public String afficheInscriptionAdmin(Model model) {

		
		AdminEtablissementDto adminEtablissementDto = new AdminEtablissementDto();

		model.addAttribute("adminEtablissementDto", adminEtablissementDto);
		return "/public/inscription-admin";
	}

	@RequestMapping(value = "/public/inscription-admin", method = RequestMethod.POST)
	public String inscriptionAdmin(Model model, @Valid @ModelAttribute("adminEtablissementDto") AdminEtablissementDto adminEtablissementDto,
			BindingResult result,HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println(">>>> adminDto :" + adminEtablissementDto);

		return "/public/connexion";
	}

	@RequestMapping(value = "/protected/inscription-apprenant-formateur", method = RequestMethod.GET)
	public String inscriptionApprenantFormateur(Model model) {

		isConnectBoolean = true;
		isAdmin = true;

		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("admin", isAdmin);

		return "/protected/inscription-apprenant-formateur";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/connexion";
	}

}
