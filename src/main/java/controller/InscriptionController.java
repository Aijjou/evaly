package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dto.AdminEtablissementDto;
import dto.FormateurDto;
import dto.UtilisateurDto;
import dto.VerifyCodeDto;
import model.Organisation;
import model.Role;
import model.Utilisateur;
import repository.UtilisateurRepository;
import service.GroupeService;
import service.MatiereService;
import service.OrganisationService;
import service.RoleService;
import service.UtilisateurService;
import model.Formateur;
import model.GroupeFormateur;
import model.Matiere;

@Controller
public class InscriptionController {

	Boolean isConnectBoolean = false;
	Boolean isAdmin = false;

	@Autowired
	private GroupeService groupeService;

	@Autowired
	private MatiereService matiereService;

	@Autowired
	private OrganisationService organisationRepository;

	@Autowired
	private UtilisateurService utilisateurService;

	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "/public/verification-code", method = RequestMethod.GET)
	public String verificationCode(Model model) {
		
		return "/public/verification-code";
	}

	@PostMapping("/public/verification-code")
	public String verifyCodeAction(Model model, @Valid VerifyCodeDto verifyCodeDto, BindingResult result) {

		if (result.hasErrors()) {
			return "/public/verification-code";
		}

		utilisateurService.verifyCode(verifyCodeDto);

		return "redirect:/public/inscription-final";
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
	public String inscriptionFormateurAdmin(FormateurDto formateurDto, Model model) {

		isConnectBoolean = true;
		isAdmin = true;

		List<GroupeFormateur> groupesList = groupeService.getListGroupeFormateur();
		List<Matiere> matieres = matiereService.matieres();

		model.addAttribute("groupes", groupesList);
		model.addAttribute("matieres", matieres);

		return "/admin/inscription-formateur-admin";
	}

	@RequestMapping(value = "/admin/inscription-formateur-admin", method = RequestMethod.POST)
	public String registration(Model model, @Valid @ModelAttribute("formateurDto") FormateurDto formateurDto,
			BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (formateurDto != null) {

			Formateur formateur = utilisateurService.createFormateurParAdmin(formateurDto);

			if (formateur != null) {
				return "redirect:/public/verification-code";
			}

		}

		return "/admin/inscription-formateur-admin";
	}

	@RequestMapping(value = "/public/inscription-admin", method = RequestMethod.GET)
	public String afficheInscriptionAdmin(Model model) {

		Boolean admin = true;
		Boolean form = true;

		AdminEtablissementDto adminEtablissementDto = new AdminEtablissementDto();

		model.addAttribute("adminEtablissementDto", adminEtablissementDto);
		model.addAttribute("admin", admin);
		model.addAttribute("form", form);

		return "/public/inscription-admin";
	}

	@RequestMapping(value = "/public/inscription-admin", method = RequestMethod.POST)
	public String inscriptionAdmin(Model model,
			@Valid @ModelAttribute("adminEtablissementDto") AdminEtablissementDto adminEtablissementDto,
			BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println(" adminDto   " + adminEtablissementDto);

		utilisateurService.test("coucou");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (adminEtablissementDto.getIsFormateur() != null) {
			System.out.println("isFormateur");

			UtilisateurDto utilisateurDto = new UtilisateurDto(adminEtablissementDto.getNomReferent(),
					adminEtablissementDto.getPrenomReferent(), adminEtablissementDto.getMail(),
					adminEtablissementDto.getPassword(), null, adminEtablissementDto.getQuestion(),
					adminEtablissementDto.getReponse());

			Utilisateur utilisateur = utilisateurService.createFormateur(utilisateurDto);

			System.out.println(" admin " + utilisateur);

		} else {
			System.out.println("isAdmin");
			UtilisateurDto utilisateurDto = new UtilisateurDto(adminEtablissementDto.getNomReferent(),
					adminEtablissementDto.getPrenomReferent(), adminEtablissementDto.getMail(),
					adminEtablissementDto.getPassword(), null, adminEtablissementDto.getQuestion(),
					adminEtablissementDto.getReponse());

			Utilisateur utilisateur = utilisateurService.createAdmin(utilisateurDto);

			System.out.println(" formateur " + utilisateur);
		}

		Organisation organisation = new Organisation(adminEtablissementDto.getNom(), adminEtablissementDto.getNumero(),
				adminEtablissementDto.getRue(), adminEtablissementDto.getVille(), adminEtablissementDto.getCode(), null,
				null);

		organisationRepository.create(organisation);

		return "redirect:/public/connexion";
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
