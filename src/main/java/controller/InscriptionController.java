package controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dto.AdminEtablissementDto;
import dto.ApprenantDto;
import dto.ApprenantDtoFinal;
import dto.FormateurDto;
import dto.FormateurDtoFinal;
import dto.UtilisateurDto;
import dto.VerifyCodeDto;
import model.Apprenant;
import model.Formateur;
import model.GroupeFormateur;
import model.Matiere;
import model.Organisation;
import model.Promotion;
import model.Utilisateur;
import model.VerifyUtilisateur;
import service.ApprenantService;
import service.FormateurService;
import service.GroupeService;
import service.MatiereService;
import service.OrganisationService;
import service.PromotionService;
import service.RoleService;
import service.UtilisateurService;
import service.VerifyUtilisateurService;

@Controller
public class InscriptionController {

	@Autowired
	private GroupeService groupeService;

	@Autowired
	private MatiereService matiereService;

	@Autowired
	private OrganisationService organisationService;

	@Autowired
	private ApprenantService apprenantService;

	@Autowired
	private UtilisateurService utilisateurService;

	@Autowired
	private FormateurService formateurService;

	@Autowired
	private VerifyUtilisateurService verifyUtilisateurService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private PromotionService promotionService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value = "/public/verification-code", method = RequestMethod.GET)
	public String verificationCode(Model model) {

		model.addAttribute("verifyCodeDto", new VerifyCodeDto());

		return "/public/verification-code";
	}

	@PostMapping("/public/verification-code")
	public String verifyCodeAction(Model model, @Valid VerifyCodeDto verifyCodeDto, BindingResult result) {

		List<Promotion> promotions = promotionService.promotions();
		System.err.println("verifiy code" + verifyCodeDto);

		if (result.hasErrors()) {
			return "/public/verification-code";
		}

		VerifyUtilisateur verifyutilisateur = verifyUtilisateurService.findByToken(verifyCodeDto.getToken()).get();

		Utilisateur utilisateur = utilisateurService.findById(verifyutilisateur.getUtilisateur().getIdUtilisateur())
				.get();

		System.err.println("utilisateur " + formateurService.findById(utilisateur.getIdUtilisateur()));

		if (!formateurService.findById(utilisateur.getIdUtilisateur()).isEmpty()) {
			System.err.println("formateur part");
			Formateur formateur = utilisateurService.findById1(verifyutilisateur.getUtilisateur().getIdUtilisateur())
					.get();

			FormateurDtoFinal formateurDtoFinal = new FormateurDtoFinal(formateur.getIdUtilisateur(),
					formateur.getNom(), formateur.getPrenom(), formateur.getMail(), null,
					formateur.getDateInscription(), null, formateur.getActive(), false, formateur.getIsReferent(), null,
					null, null);

			if (utilisateurService.verifyCode(verifyCodeDto)) {
				formateurDtoFinal.setActive(true);
			}

			model.addAttribute("formateurDtoFinal", formateurDtoFinal);
			model.addAttribute("promotions", promotions);

			return "/public/inscription-final";

		} else if (!apprenantService.findById(utilisateur.getIdUtilisateur()).isEmpty()) {
			System.err.println("apprenant part");
			Apprenant apprenant = utilisateurService.findById2(verifyutilisateur.getUtilisateur().getIdUtilisateur())
					.get();
			System.err.println("promotion id" + apprenant.getPromotion().getIdPromotion());
			ApprenantDtoFinal apprenantDtoFinal = new ApprenantDtoFinal(apprenant.getIdUtilisateur(),
					apprenant.getNom(), apprenant.getPrenom(), apprenant.getMail(), null,
					apprenant.getDateInscription(), null, apprenant.getActive(), false, null, null,
					apprenant.getPromotion().getIdPromotion());

			System.err.println(" >>>> " + apprenant.getPromotion().getIdPromotion());

			if (utilisateurService.verifyCode(verifyCodeDto)) {
				apprenantDtoFinal.setActive(true);
			}

			model.addAttribute("apprenantDtoFinal", apprenantDtoFinal);

			return "/public/inscription-final-apprenant";

		}
		return "/public/verification-code";
	}

	@RequestMapping(value = "/public/inscription-final", method = RequestMethod.GET)
	public String afficheInscriptionFinal(Model model) {

		return "/public/inscription-final";
	}

	@RequestMapping(value = "/public/inscription-final", method = RequestMethod.POST)
	public String inscriptionFinal(Model model,
			@Valid @ModelAttribute("formateurDtoFinal") FormateurDtoFinal formateurDtoFinal, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.err.println("formateur final " + formateurDtoFinal);

		Formateur formateur = utilisateurService.createFormateurFinal(formateurDtoFinal);

		if (formateur.getMail() != null && formateur.getPassword() != null) {
			return "/public/connexion";
		}
		return "/public/inscription-final";
	}

	@RequestMapping(value = "/public/inscription-final-apprenant", method = RequestMethod.GET)
	public String afficheInscriptionFinalApprenant(Model model) {

		return "/public/inscription-final-apprenant";
	}

	@RequestMapping(value = "/public/inscription-final-apprenant", method = RequestMethod.POST)
	public String inscriptionFinalApprenant(Model model,
			@Valid @ModelAttribute("apprenantFinalDto") ApprenantDtoFinal apprenantDtoFinal, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		Apprenant apprenant = utilisateurService.createApprenantFinal(apprenantDtoFinal);

		if (apprenant.getMail() != null && apprenant.getPassword() != null) {
			return "/public/connexion";
		}
		return "/public/inscription-final-apprenant";
	}

	@RequestMapping(value = "/admin/inscription-formateur-admin", method = RequestMethod.GET)
	public String inscriptionFormateurAdmin(FormateurDto formateurDto, Model model) {

		List<GroupeFormateur> groupesList = groupeService.getListGroupeFormateur();
		List<Matiere> matieres = matiereService.matieres();

		model.addAttribute("groupes", groupesList);
		model.addAttribute("matieres", matieres);

		return "/admin/inscription-formateur-admin";
	}

	@RequestMapping(value = "/admin/inscription-formateur-admin", method = RequestMethod.POST)
	public String registration(Model model, @Valid @ModelAttribute("formateurDto") FormateurDto formateurDto,
			BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Formateur formateur = utilisateurService.createFormateurParAdmin(formateurDto);

		if (formateur != null) {
			return "redirect:/protected/home";
		}

		return "/admin/inscription-formateur-admin";
	}

	@RequestMapping(value = "/public/inscription-admin", method = RequestMethod.GET)
	public String afficheInscriptionAdmin(Model model) {

		AdminEtablissementDto adminEtablissementDto = new AdminEtablissementDto();

		model.addAttribute("adminEtablissementDto", adminEtablissementDto);

		return "/public/inscription-admin";
	}

	@RequestMapping(value = "/public/inscription-admin", method = RequestMethod.POST)
	public String inscriptionAdmin(Model model,
			@Valid @ModelAttribute("adminEtablissementDto") AdminEtablissementDto adminEtablissementDto,
			BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println(" adminDto   " + adminEtablissementDto);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (adminEtablissementDto.getIsFormateur() != null) {
			System.out.println("isFormateur");

			UtilisateurDto utilisateurDto = new UtilisateurDto(adminEtablissementDto.getNomReferent(),
					adminEtablissementDto.getPrenomReferent(), adminEtablissementDto.getMail(),
					adminEtablissementDto.getPassword(), null, adminEtablissementDto.getQuestion(),
					adminEtablissementDto.getReponse(), true);

			Utilisateur utilisateur = utilisateurService.createFormateur(utilisateurDto);

			System.out.println(" admin " + utilisateur);

		} else {
			System.out.println("isAdmin");
			UtilisateurDto utilisateurDto = new UtilisateurDto(adminEtablissementDto.getNomReferent(),
					adminEtablissementDto.getPrenomReferent(), adminEtablissementDto.getMail(),
					adminEtablissementDto.getPassword(), null, adminEtablissementDto.getQuestion(),
					adminEtablissementDto.getReponse(), true);

			Utilisateur utilisateur = utilisateurService.createAdmin(utilisateurDto);

			System.out.println(" formateur " + utilisateur);
		}

		Organisation organisation = new Organisation(adminEtablissementDto.getNom(), adminEtablissementDto.getNumero(),
				adminEtablissementDto.getRue(), adminEtablissementDto.getVille(), adminEtablissementDto.getCode(), null,
				null);

		organisationService.create(organisation);

		return "redirect:/public/connexion";
	}

	@RequestMapping(value = "/protected/inscription-apprenant-formateur", method = RequestMethod.GET)
	public String afficheInscriptionApprenantFormateur(Model model) {

		List<Promotion> promotions = promotionService.promotions();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			return "redirect:/public/connexion";
		}

		model.addAttribute("promotions", promotions);
		model.addAttribute("apprenantDto", new ApprenantDto());

		return "/protected/inscription-apprenant-formateur";
	}

	@RequestMapping(value = "/protected/inscription-apprenant-formateur", method = RequestMethod.POST)
	public String inscriptionApprenantFormateur(Model model,
			@Valid @ModelAttribute("apprenantDto") ApprenantDto apprenantDto, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) {

		System.err.println(">>>>>>> apprenantDto  " + apprenantDto);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth == null) {
			return "redirect:/public/connexion";
		}

		Apprenant apprenant = apprenantService.createApprenantParFormateur(apprenantDto);

		if (apprenant != null) {
			return "redirect:/protected/home";
		}

		return "/protected/inscription-apprenant-formateur";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public static String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/connexion";
	}

}
