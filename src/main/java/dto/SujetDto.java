package dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Examen;
import model.Formateur;
import model.Matiere;
import model.ReponseApprenant;
import model.ReponseApprenantExamen;
import model.SujetQuestion;
import model.Theme;

public class SujetDto {
	
	private Integer idSujet;
	private Formateur formateur;
	private Matiere matiere;
	private String nom;
	private Boolean isAutomaticGenerated;
	private String descriptionSujet;
	private Double noteMoyenne=0D;
	private Set<Examen> examens = new HashSet<Examen>(0);
	private Set<ReponseApprenantExamen> reponseApprenantExamens = new HashSet<ReponseApprenantExamen>(0);
	private Set<SujetQuestion> sujetQuestions = new HashSet<SujetQuestion>(0);
	private Set<ReponseApprenant> reponseApprenants = new HashSet<ReponseApprenant>(0);
	private List<Theme> theme = new ArrayList<Theme>();
	
	public List<Theme> getTheme() {
		return theme;
	}

	public void setTheme(List<Theme> theme) {
		this.theme = theme;
	}

	public SujetDto() {
		
	}

	public Integer getIdSujet() {
		return idSujet;
	}

	public void setIdSujet(Integer idSujet) {
		this.idSujet = idSujet;
	}

	public Formateur getFormateur() {
		return formateur;
	}

	public void setFormateur(Formateur formateur) {
		this.formateur = formateur;
	}

	public Matiere getMatiere() {
		return matiere;
	}

	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Boolean getIsAutomaticGenerated() {
		return isAutomaticGenerated;
	}

	public void setIsAutomaticGenerated(Boolean isAutomaticGenerated) {
		this.isAutomaticGenerated = isAutomaticGenerated;
	}

	public String getDescriptionSujet() {
		return descriptionSujet;
	}

	public void setDescriptionSujet(String descriptionSujet) {
		this.descriptionSujet = descriptionSujet;
	}

	public Double getNoteMoyenne() {
		return noteMoyenne;
	}

	public void setNoteMoyenne(Double noteMoyenne) {
		this.noteMoyenne = noteMoyenne;
	}

	public Set<Examen> getExamens() {
		return examens;
	}

	public void setExamens(Set<Examen> examens) {
		this.examens = examens;
	}

	public Set<ReponseApprenantExamen> getReponseApprenantExamens() {
		return reponseApprenantExamens;
	}

	public void setReponseApprenantExamens(Set<ReponseApprenantExamen> reponseApprenantExamens) {
		this.reponseApprenantExamens = reponseApprenantExamens;
	}

	public Set<SujetQuestion> getSujetQuestions() {
		return sujetQuestions;
	}

	public void setSujetQuestions(Set<SujetQuestion> sujetQuestions) {
		this.sujetQuestions = sujetQuestions;
	}

	public Set<ReponseApprenant> getReponseApprenants() {
		return reponseApprenants;
	}

	public void setReponseApprenants(Set<ReponseApprenant> reponseApprenants) {
		this.reponseApprenants = reponseApprenants;
	}

}
