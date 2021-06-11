package controller;

import java.nio.file.attribute.UserPrincipal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cloudinary.provisioning.Account.Role;

import dto.ApprenantDtoFinal;
import dto.FormateurDtoFinal;
import model.Apprenant;
import model.Examen;
import model.Formateur;
import model.FormateurMatiere;
import model.Matiere;
import model.Promotion;
import model.PromotionFormateur;
import model.Sujet;
import service.ExamenService;
import service.FormateurMatiereService;
import service.FormateurService;
import service.PromotionService;
import service.SujetService;

@Controller
public class FormateurController {

	Boolean isConnectBoolean = false;
	Boolean isAdmin = false;
	Boolean isFormateur = false;
	Boolean isApprenant = false;

	@Autowired
	FormateurService formateurService;

	@Autowired
	FormateurMatiereService formateurMatiereService;

	@Autowired
	SujetService sujetService;

	@Autowired
	PromotionService promotionService;

	@Autowired
	ExamenService examenService;

	@RequestMapping(value = "/admin/liste-formateur", method = RequestMethod.GET)
	public String afficheFormateur(Model model) {

		isAdmin = true;
		isFormateur = false;
		isApprenant = false;

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		System.err.println(" >>>>>> " + auth.getName());
		System.err.println(" >>>>>> " + auth.toString());
		System.err.println(" >>>>>> " + auth.getPrincipal());

		auth.getAuthorities().stream().forEach(role -> {

			System.err.println(role.getAuthority().equals("ROLE_ADMIN"));

		});

		principal.UserPrincipal userPrincipal = (principal.UserPrincipal) auth.getPrincipal();

		System.err.println(userPrincipal.getId());

		List<Formateur> formateurs = formateurService.formateurs();
		List<String> nomMatiere = new ArrayList<>();
		for (Formateur formateur : formateurs) {
			System.out.println("formateur" + formateur.getNom());
			formateur.getFormateurMatieres().stream().forEach(matiere -> {
				nomMatiere.add(matiere.getMatiere().getNom());

			});

		}

		isConnectBoolean = true;
		isAdmin = true;

		model.addAttribute("connexion", isConnectBoolean);
		model.addAttribute("apprenant", isApprenant);
		model.addAttribute("admin", isAdmin);
		model.addAttribute("formateur", isFormateur);
		model.addAttribute("formateurs", formateurs);
		model.addAttribute("matieres", nomMatiere);

		return "/admin/liste-formateur";

	}

	@RequestMapping(value = "/admin/delete-formateur/{id}", method = RequestMethod.GET)
	public String deleteApprenant(Model model, @PathVariable("id") Integer idFormateur) {
		System.err.println(idFormateur);
		if (formateurService.delete(idFormateur)) {
			return "redirect:/admin/liste-formateur";
		}
		return "redirect:/admin/liste-formateur";

	}

	@RequestMapping(value = "/protected/edit-formateur-formateur/{idFormateur}", method = RequestMethod.GET)
	public String editApprenantParApprenant(Model model, @PathVariable("idFormateur") Integer idFormateur) {

		List<Promotion> promotions = promotionService.promotions();

		boolean isModification = true;

		Formateur formateur = formateurService.findById(idFormateur).get();

		List<PromotionFormateur> promotionsFormateurs = promotionService.findByFormateur(formateur);

		List<Integer> listeIdPromotion = promotionsFormateurs.stream()
				.map(promotionsForma -> promotionsForma.getPromotion().getIdPromotion()).collect(Collectors.toList());

		System.err.println(" >>>>>>>  " + formateur);

		FormateurDtoFinal formateurDtoFinal = new FormateurDtoFinal(formateur.getIdUtilisateur(), formateur.getNom(),
				formateur.getPrenom(), formateur.getMail(), formateur.getPassword(), formateur.getDateInscription(),
				formateur.getDateNaissance(), formateur.getActive(), null, formateur.getIsAdmin(), null,
				formateur.getQuestionSecrete(), formateur.getReponseSecrete(), listeIdPromotion);

		model.addAttribute("formateurDtoFinal", formateurDtoFinal);
		model.addAttribute("promotions", promotions);
		model.addAttribute("isModification", isModification);

		return "/public/inscription-final";
	}

}
