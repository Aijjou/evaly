package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import model.Utilisateur;
import repository.UtilisateurRepository;
import service.UtilisateurService;

@Controller
@Scope("session")
public class ConnexionController {

	@Autowired
	private UtilisateurService utilisateurService;

	@RequestMapping(value = "/public/connexion", method = RequestMethod.GET)
	public String login(Model model, HttpServletRequest request, HttpServletResponse response) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	
		String titreString = "Decouvrez Evaly";

		model.addAttribute("titre", titreString);

		return "/public/connexion";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String loginSlash(Model model, HttpServletRequest request, HttpServletResponse response) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	
		String titreString = "Decouvrez Evaly";

		model.addAttribute("titre", titreString);

		return "/public/connexion";

	}

	@RequestMapping(value = "/protected/home", method = RequestMethod.GET)
	public String getHome(Model model) {

		String titreString = "Bienvenue sur Evaly";

		model.addAttribute("titre", titreString);

		return "/protected/home";

	}

	@RequestMapping(value = "/public/accessDenied", method = RequestMethod.GET)
	public String accessDenied(Model model) {

		return "/public/accessDenied";

	}

}
