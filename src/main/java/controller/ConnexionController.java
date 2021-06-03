package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import model.Utilisateur;
import repository.UtilisateurRepository;

@Controller
public class ConnexionController {
	
	
	
	@Autowired
	UtilisateurRepository utilisateurRepository;
	
	
//	@RequestMapping(value = "/", method = RequestMethod.GET)
//	public String home(Model model, Pageable pageable) {
//		model.addAttribute("page", utilisateurRepository.findAll(pageable));
//		return "layout";
//	}
//
//	@RequestMapping(value = "/pagination", method = RequestMethod.GET)
//	@ResponseBody
//	public Page<Utilisateur> findAll(Pageable pageable) {
//		return utilisateurRepository.findAll(pageable);
//	}
	
	
//	@Autowired
//	private Validator validator;
//
//	@InitBinder
//	private void initBinder(WebDataBinder binder) {
//		binder.setValidator(validator);
//	}
//	
//	@Autowired
//	private UtilisateurService utilisateurService;
//	
//	
//	
//	Boolean isConnectBoolean = false;
//	Boolean isAdmin = false;
//	Boolean isFormateur = false;
//	Boolean isApprenant = false;
//	
//	
//	@RequestMapping(value = "/public/connexion", method = RequestMethod.GET)
//	public String connexion(Model model) {
//		
//		isConnectBoolean = false;
//		isAdmin = false;
//		
//		model.addAttribute("connexion", isConnectBoolean);
//		model.addAttribute("admin", isAdmin);
//		
//		
//		return "/public/connexion";
//	}
//	
//	
//
//	@RequestMapping(value = "/public/connexion", method = RequestMethod.GET)
//	public String login(Model model, HttpServletRequest request) {
//
//		return "/public/connexion";
//	}
//
//	@RequestMapping(value = "/public/connexion", method = RequestMethod.POST)
//	public String connect(Model model, @ModelAttribute("compte") Utilisateur utilisateur, HttpSession session) {
//
////		Compte compte2 = compteService.findByEmailAndPassword(compte.getEmail(), compte.getPassword());
//		Utilisateur utilisateur1 = utilisateurService.findByUsernameOrEmail(utilisateur.getNom(), utilisateur.getMail())
//				.get();
//		System.out.println(utilisateur1);
//
//		if (utilisateur1 != null) {
//
//			session.setAttribute("nom", utilisateur1.getNom());
//			session.setAttribute("prenom", utilisateur1.getPrenom());
//
//		} else {
//			return "/public/connexion";
//		}
//
//		return "/protected/home";
//
//	}
//
//	@RequestMapping(value = "/public/verification-code", method = RequestMethod.GET)
//	public String verifyCode(Model model, VerifyCodeDto verifyCodeDto) {
//
//		return "/public/verification-code";
//	}
//
//	@PostMapping("/public/verification-code")
//	public String verifyCodeAction(Model model, @Valid VerifyCodeDto verifyCodeDto, BindingResult result) {
//
//		if (result.hasErrors()) {
//			return "/public/verification-code";
//		}
//
//		utilisateurService.verifyCode(verifyCodeDto);
//
//		return "redirect:/inscription-final";
//	}
	
	

}
