package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import model.Examen;
import service.ExamenService;

@Controller
@Scope("session")
public class ExamenController {

	@Autowired
	ExamenService examenService;

	@RequestMapping(value = "protected/liste-examen", method = RequestMethod.GET)
	public String afficheExamen(Model model) {

		List<Examen> examens = examenService.getListExamen();

		model.addAttribute("examens", examens);

		return "protected/liste-examen";

	}

	@RequestMapping(value = "protected/creation-examen", method = RequestMethod.GET)
	public String creationExamen(Model model) {

		return "protected/creation-examen";

	}

}
