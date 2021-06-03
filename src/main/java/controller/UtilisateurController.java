package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import model.Utilisateur;
import repository.UtilisateurRepository;
import service.UtilisateurService;

@RestController
public class UtilisateurController {

	@Autowired
	private Validator validator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	@Autowired
	private UtilisateurService utilisateurService;

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model, Pageable pageable) {
		model.addAttribute("page", utilisateurRepository.findAll(pageable));
		return "layout";
	}

	@RequestMapping(value = "/pagination", method = RequestMethod.GET)
	@ResponseBody
	public Page<Utilisateur> findAll(Pageable pageable) {
		return utilisateurRepository.findAll(pageable);
	}

	@RequestMapping(value = "/public/connexion", method = RequestMethod.GET)
	public String login(Model model, HttpServletRequest request) {
		System.out.println("connexion");

		return "/public/connexion";
	}

	@RequestMapping(value = "/public/connexion", method = RequestMethod.POST)
	public String connect(Model model, @ModelAttribute("compte") Utilisateur utilisateur, HttpSession session) {

//		Compte compte2 = compteService.findByEmailAndPassword(compte.getEmail(), compte.getPassword());
		Utilisateur utilisateur1 = utilisateurService.findByUsernameOrEmail(utilisateur.getNom(), utilisateur.getMail())
				.get();
		System.out.println(utilisateur1);

		if (utilisateur1 != null) {

			session.setAttribute("nom", utilisateur1.getNom());
			session.setAttribute("prenom", utilisateur1.getPrenom());

		} else {
			return "/public/connexion";
		}

		return "/protected/home";

	}

}
