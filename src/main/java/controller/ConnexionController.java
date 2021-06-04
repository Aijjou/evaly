package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	public String login(Model model, HttpServletRequest request) {
		System.out.println("connexion");

		return "/public/connexion";
	}

	@RequestMapping(value = "/protected/home", method = RequestMethod.GET)
	public String getHome(Model model) {

		

		return "/protected/home";

	}

}
