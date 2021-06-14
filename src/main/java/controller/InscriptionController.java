package controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
import model.PromotionFormateur;
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

	private static Logger logger = Logger.getLogger(InscriptionController.class);

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

	Boolean isAdmin = false;
	Boolean isFormateur = false;
	Boolean isApprenant = false;
	Boolean isConnectBoolean = true;
	Integer idUtilisateur = null;

	private void verificationRolesAndSetIdUtilisateur() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.err.println(" --- --- --- verificationRoles  --- --- --- ");
		auth.getAuthorities().stream().forEach(role -> {
			if (role.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				isFormateur = false;
				isApprenant = false;
				System.out.println("ROLE_ADMIN");
			}
			if (role.getAuthority().equals("ROLE_APPRENANT")) {
				isApprenant = true;
				isFormateur = false;
				isAdmin = false;
				System.out.println("ROLE_APPRENANT");
			}
			if (role.getAuthority().equals("ROLE_FORMATEUR")) {
				isFormateur = true;
				isApprenant = false;
				isAdmin = false;
				System.out.println("ROLE_FORMATEUR");
			}
		});
		principal.UserPrincipal userPrincipal = (principal.UserPrincipal) auth.getPrincipal();
		idUtilisateur = userPrincipal.getId();
		System.err.println(" --- --- --- verificationRoles --- --- --- ");
	}

	private final String UPLOAD_DIR = "C:\\Users\\afpa\\Documents\\workspace-spring-tool-suite-4-4.9.0.RELEASE\\evaly\\src\\main\\resources\\static\\images\\";

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

		if (formateurService.findById(utilisateur.getIdUtilisateur()).isPresent()) {
			System.err.println("formateur part");
			Formateur formateur = utilisateurService.findById1(verifyutilisateur.getUtilisateur().getIdUtilisateur())
					.get();

			FormateurDtoFinal formateurDtoFinal = new FormateurDtoFinal(formateur.getIdUtilisateur(),
					formateur.getNom(), formateur.getPrenom(), formateur.getMail(), null,
					formateur.getDateInscription(), null, formateur.getActive(), null, false, formateur.getIsReferent(),
					null, null, null);

			if (utilisateurService.verifyCode(verifyCodeDto)) {
				formateurDtoFinal.setActive(true);
			}

			model.addAttribute("formateurDtoFinal", formateurDtoFinal);
			model.addAttribute("promotions", promotions);

			return "/public/inscription-final";

		} else if (apprenantService.findById(utilisateur.getIdUtilisateur()).isPresent()) {
			System.err.println("apprenant part");
			Apprenant apprenant = utilisateurService.findById2(verifyutilisateur.getUtilisateur().getIdUtilisateur())
					.get();
			System.err.println("promotion id" + apprenant.getPromotion().getIdPromotion());
			ApprenantDtoFinal apprenantDtoFinal = new ApprenantDtoFinal(apprenant.getIdUtilisateur(),
					apprenant.getNom(), apprenant.getPrenom(), apprenant.getMail(), null,
					apprenant.getDateInscription(), null, apprenant.getActive(), false, null, null,
					apprenant.getPromotion().getIdPromotion(), null);

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

	@RequestMapping(value = "/admin/edit-admin-admin/{idAdmin}", method = RequestMethod.GET)
	public String editAdminAdmin(Model model, @PathVariable("idAdmin") Integer idAdmin) {

		boolean isModification = true;

		Utilisateur utilisateur = utilisateurService.findById(idAdmin).get();

		Organisation organisation = organisationService.findOrganisation(utilisateur.getIdOrganisation()).get();

		logger.info("utilisateur " + utilisateur);
		logger.info("organisation " + organisation);

		AdminEtablissementDto adminEtablissementDto = new AdminEtablissementDto(utilisateur.getIdUtilisateur(),
				organisation.getName(), organisation.getNumero(), organisation.getRue(), organisation.getVille(),
				organisation.getCode(), null, utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getMail(),
				null, utilisateur.getQuestionSecrete(), utilisateur.getReponseSecrete(), utilisateur.getIsAdmin());

		System.err.println(adminEtablissementDto);

		model.addAttribute("isModification", isModification);
		model.addAttribute("adminEtablissementDto", adminEtablissementDto);

		return "/public/inscription-admin";
	}

	@RequestMapping(value = "/public/inscription-final", method = RequestMethod.POST)
	public String inscriptionFinal(Model model,
			@Valid @ModelAttribute("formateurDtoFinal") FormateurDtoFinal formateurDtoFinal, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		String fileName = org.springframework.util.StringUtils
				.cleanPath(formateurDtoFinal.getPhoto().getOriginalFilename());
		Path path = Paths.get(UPLOAD_DIR + fileName);
		Files.copy(formateurDtoFinal.getPhoto().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

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

		String fileName = org.springframework.util.StringUtils
				.cleanPath(apprenantDtoFinal.getPhoto().getOriginalFilename());
		Path path = Paths.get(UPLOAD_DIR + fileName);
		Files.copy(apprenantDtoFinal.getPhoto().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

		System.err.println("dateNaissance + " + apprenantDtoFinal.getDateNaissance());

		Apprenant apprenant = utilisateurService.createApprenantFinal(apprenantDtoFinal);

		if (apprenant.getMail() != null && apprenant.getPassword() != null) {
			return "redirect:/logout";
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
			BindingResult result, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) throws Exception {

		Formateur formateur = utilisateurService.createFormateurParAdmin(formateurDto);

		if (formateur != null) {
			redirectAttributes.addFlashAttribute("message", "L'opération a reussi");
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");
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

		System.out.println(" adminDto   " + adminEtablissementDto.getIdAdminEtablissementDtoInteger());

		UtilisateurDto utilisateurDto;

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (adminEtablissementDto.getIsFormateur() != false) {
			System.out.println("isFormateur");

			if (adminEtablissementDto.getIdAdminEtablissementDtoInteger() != null) {

				utilisateurDto = new UtilisateurDto(adminEtablissementDto.getIdAdminEtablissementDtoInteger(),
						adminEtablissementDto.getNomReferent(), adminEtablissementDto.getPrenomReferent(),
						adminEtablissementDto.getMail(), adminEtablissementDto.getPassword(), null,
						adminEtablissementDto.getQuestion(), adminEtablissementDto.getReponse(), true);

			} else {
				utilisateurDto = new UtilisateurDto(adminEtablissementDto.getNomReferent(),
						adminEtablissementDto.getPrenomReferent(), adminEtablissementDto.getMail(),
						adminEtablissementDto.getPassword(), null, adminEtablissementDto.getQuestion(),
						adminEtablissementDto.getReponse(), true);
			}

			Utilisateur utilisateur = utilisateurService.createFormateur(utilisateurDto);

			System.out.println(" admin " + utilisateur);

		} else {
			System.out.println("isAdmin");

			if (adminEtablissementDto.getIdAdminEtablissementDtoInteger() != null) {
				System.out.println("isAdmin + id not null");
				utilisateurDto = new UtilisateurDto(adminEtablissementDto.getIdAdminEtablissementDtoInteger(),
						adminEtablissementDto.getNomReferent(), adminEtablissementDto.getPrenomReferent(),
						adminEtablissementDto.getMail(), adminEtablissementDto.getPassword(), null,
						adminEtablissementDto.getQuestion(), adminEtablissementDto.getReponse(), true);

			} else {
				System.out.println("isAdmin + id null");
				utilisateurDto = new UtilisateurDto(adminEtablissementDto.getNomReferent(),
						adminEtablissementDto.getPrenomReferent(), adminEtablissementDto.getMail(),
						adminEtablissementDto.getPassword(), null, adminEtablissementDto.getQuestion(),
						adminEtablissementDto.getReponse(), true);
			}
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

		List<Promotion> promotions2 = new ArrayList<>();

		verificationRolesAndSetIdUtilisateur();

		if (isFormateur == true) {

			Formateur formateur = formateurService.findById(idUtilisateur).get();
			List<PromotionFormateur> promotions = promotionService.findByFormateur(formateur);

			promotions2 = promotions.stream().map(promo -> promo.getPromotion()).collect(Collectors.toList());

		}
		if (isAdmin == true) {
			promotions2 = promotionService.promotions();
		}
		System.err.println(" >>>>> " + promotions2);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			return "redirect:/public/connexion";
		}

		model.addAttribute("promotions", promotions2);
		model.addAttribute("apprenantDto", new ApprenantDtoFinal());

		return "/protected/inscription-apprenant-formateur";
	}

	@RequestMapping(value = "/protected/inscription-apprenant-formateur", method = RequestMethod.POST)
	public String inscriptionApprenantFormateur(Model model,
			@Valid @ModelAttribute("apprenantDto") ApprenantDto apprenantDto, BindingResult result,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth == null) {
			return "redirect:/public/connexion";
		}

		Apprenant apprenant = apprenantService.createApprenantParFormateur(apprenantDto);

		if (apprenant != null) {
			redirectAttributes.addFlashAttribute("message", "L'opération a reussi");
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");
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
		return "redirect:/public/connexion";
	}

}
