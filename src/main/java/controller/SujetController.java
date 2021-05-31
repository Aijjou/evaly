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
public class SujetController {


	@RequestMapping(value = "/protected/creation-sujet", method = RequestMethod.GET)
	public String afficheFormateur(Model model) {
		return "/protected/creation-sujet";
	}
	
	@RequestMapping(value = "/protected/creation-sujet-gen", method = RequestMethod.GET)
	public String generateSubject(Model model) {
		return "/protected/creation-sujet-gen";
	}
	
	@RequestMapping(value = "/protected/creation-sujet-manu", method = RequestMethod.GET)
	public String manualSubject(Model model) {
		return "/protected/creation-sujet-manu";
	}

	@RequestMapping(value = "/protected/liste-sujet", method = RequestMethod.GET)
	public String listeSujet(Model model) {
		return "/protected/liste-sujet";
	}
}
