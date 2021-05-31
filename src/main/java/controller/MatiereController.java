package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.Apprenant;
import model.Formateur;
import service.ApprenantService;
import service.FormateurService;

@Controller
public class MatiereController {


	@RequestMapping(value = "/protected/creation-matiere", method = RequestMethod.GET)
	public String creationMatiere(Model model) {

		return "/protected/creation-matiere";
	}
	
}
