package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javassist.expr.NewArray;
import model.Apprenant;
import model.Formateur;
import model.FormateurMatiere;
import model.GroupeFormateur;
import model.Matiere;
import model.Promotion;
import model.PromotionFormateur;
import repository.FormateurMatiereRepository;
import service.ApprenantService;
import service.FormateurMatiereService;
import service.FormateurService;
import service.GroupeService;

import service.MatiereService;
import service.PromotionService;

@Controller
public class MatiereController {

	@Autowired
	MatiereService matiereService;
	@Autowired
	GroupeService groupeService;

	@Autowired
	FormateurMatiereService formateurMatiereService;

	@Autowired
	FormateurService formateurService;

	@Autowired
	ApprenantService apprenantService;

	@Autowired
	PromotionService promotionService;

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

	@RequestMapping(value = "/protected/creation-matiere", method = RequestMethod.GET)
	public String creationMatiere(Model model) {

		isAdmin = false;
		isFormateur = false;
		isApprenant = false;
		Matiere mat = new Matiere();

		model.addAttribute("matiere", mat);
		List<GroupeFormateur> listgf = groupeService.getListGroupeFormateur();
		model.addAttribute("listegroupes", listgf);

		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("apprenant", isApprenant);
		model.addAttribute("admin", isAdmin);
		model.addAttribute("formateur", isFormateur);

		return "/protected/creation-matiere";
	}

	@RequestMapping(value = "/protected/liste-matiere", method = RequestMethod.GET)
	public String listeMatiere(Model model) {

		List<Matiere> lm = new ArrayList<>();
		verificationRolesAndSetIdUtilisateur();

		if (isAdmin == true) {
			System.err.println("isAdmin");
			lm = matiereService.matieres();
		}
		if (isFormateur == true) {

			System.err.println("isFormateur");
			List<FormateurMatiere> formateurMatieres = formateurMatiereService
					.findByFormateur(formateurService.findById(idUtilisateur).get());
			List<Matiere> matireresList = new ArrayList<>();
			formateurMatieres.stream().forEach(formateurMatiere -> {

				matireresList.add(formateurMatiere.getMatiere());

			});

			lm = matireresList;

		}
		if (isApprenant == true) {
			System.err.println("isApprenant");
			List<Formateur> formateurs = new ArrayList<>();

			Apprenant apprenant = apprenantService.findById(idUtilisateur).get();

			List<PromotionFormateur> promotionFormateurs = promotionService.findByPromotion(apprenant.getPromotion());

			promotionFormateurs.stream().forEach(promotionFormateur -> {

				formateurs.add(promotionFormateur.getFormateur());

			});

			List<Matiere> matieres = new ArrayList<>();
			formateurs.stream().forEach(formateur -> {

				List<FormateurMatiere> formateurMatierers = formateurMatiereService.findByFormateur(formateur);

				formateurMatierers.stream().forEach(formateurMatiere -> {

					matieres.add(formateurMatiere.getMatiere());

				});

			});

			lm = matieres;

		}

		model.addAttribute("matieres", lm);

		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("apprenant", isApprenant);
		model.addAttribute("admin", isAdmin);
		model.addAttribute("formateur", isFormateur);

		return "/protected/liste-matiere";

	}

	@RequestMapping(value = "/protected/edition-matiere", method = RequestMethod.POST)
	public String selectMatiere(Model model, @RequestParam Integer matiereSelect) {

		Optional<Matiere> mmm = matiereService.findById(matiereSelect);
		Matiere mat = mmm.get();
		model.addAttribute("matiere", mat);
		List<GroupeFormateur> listgf = groupeService.getListGroupeFormateur();
		model.addAttribute("listegroupes", listgf);

		isAdmin = false;
		isFormateur = false;
		isApprenant = false;

		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("apprenant", isApprenant);
		model.addAttribute("admin", isAdmin);
		model.addAttribute("formateur", isFormateur);

		return "/protected/edition-matiere";
	}

	@RequestMapping(value = "/protected/crea-matiere-sub", method = RequestMethod.POST)
	public String submitcreaMatiere(Model model, @ModelAttribute Matiere matiere) {

		for (int i = 0; i < 10; i++)
			System.out.println(matiere.getIdMatiere());
		for (int i = 0; i < 10; i++)
			System.out.println(matiere.getGroupeFormateur().getIdGroupeFormateur());

		Matiere mat = new Matiere();
		Optional<GroupeFormateur> groupeFormateuropt = groupeService
				.findById(matiere.getGroupeFormateur().getIdGroupeFormateur());
		GroupeFormateur gf = groupeFormateuropt.get();
		mat.setGroupeFormateur(gf);

		mat.setNom(matiere.getNom());

		mat.setGroupeFormateur(matiere.getGroupeFormateur());
		mat.setNom(matiere.getNom());

		// matiereService.save(mat);

		isAdmin = false;
		isFormateur = false;
		isApprenant = false;

		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("apprenant", isApprenant);
		model.addAttribute("admin", isAdmin);
		model.addAttribute("formateur", isFormateur);

		return "redirect:/protected/liste-matiere";
	}

	@RequestMapping(value = "/protected/edit-matiere-sub", method = RequestMethod.POST)
	public String submitMatiere(Model model, @ModelAttribute Matiere matiere) {

		for (int i = 0; i < 10; i++)
			System.out.println(matiere.getIdMatiere());
		for (int i = 0; i < 10; i++)
			System.out.println(matiere);

		Optional<Matiere> newm = matiereService.findById(matiere.getIdMatiere());
		Matiere mat = newm.get();

		mat.setGroupeFormateur(matiere.getGroupeFormateur());
		mat.setNom(matiere.getNom());

		matiereService.save(mat);

		isAdmin = false;
		isFormateur = false;
		isApprenant = false;

		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("apprenant", isApprenant);
		model.addAttribute("admin", isAdmin);
		model.addAttribute("formateur", isFormateur);

		return "redirect:/protected/liste-matiere";
	}

}
