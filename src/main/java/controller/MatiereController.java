package controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import model.GroupeFormateur;
import model.Matiere;
import service.GroupeService;
import service.MatiereService;

@Controller
public class MatiereController {
	
	@Autowired
	MatiereService matiereService;
	@Autowired
	GroupeService groupeService;

	Boolean isConnectBoolean = false;
	Boolean isAdmin = false;
	Boolean isFormateur = false;
	Boolean isApprenant = false;

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


		isAdmin = false;
		isFormateur = false;
		isApprenant = false;
		
		List<Matiere> lm = matiereService.matieres();
		
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
		Matiere mat= mmm.get();
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

		for (int i=0;i<10;i++) System.out.println(matiere.getIdMatiere());
		for (int i=0;i<10;i++) System.out.println(matiere.getGroupeFormateur().getIdGroupeFormateur());
		

		Matiere mat = new Matiere();
		Optional<GroupeFormateur> groupeFormateuropt = groupeService.findById(matiere.getGroupeFormateur().getIdGroupeFormateur());
		GroupeFormateur gf = groupeFormateuropt.get();
		mat.setGroupeFormateur(gf);
		
		mat.setNom(matiere.getNom());
		
		mat.setGroupeFormateur(matiere.getGroupeFormateur());
		mat.setNom(matiere.getNom());

		//matiereService.save(mat);

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

		for (int i=0;i<10;i++) System.out.println(matiere.getIdMatiere());
		for (int i=0;i<10;i++) System.out.println(matiere);
		
		
			
		
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
