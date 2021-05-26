package model;
// Generated 26 mars 2021 � 22:40:09 by Hibernate Tools 5.1.10.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Question generated by hbm2java
 */
@Entity
@Table(name = "question", catalog = "evaly")
public class Question implements java.io.Serializable {

	private Integer idQuestion;
	private Theme theme;
	private String descriptionQuestion;
	private Integer coefficient;
	private Boolean isQcm;
	private Set<ReponseApprenantExamen> reponseApprenantExamens = new HashSet<ReponseApprenantExamen>(0);
	private Set<SujetQuestion> sujetQuestions = new HashSet<SujetQuestion>(0);
	private Set<Reponse> reponses = new HashSet<Reponse>(0);
	private Set<ReponseApprenant> reponseApprenants = new HashSet<ReponseApprenant>(0);

	public Question() {
	}

	public Question(Theme theme, String descriptionQuestion, Integer coefficient, Boolean isQcm,
			Set<ReponseApprenantExamen> reponseApprenantExamens, Set<SujetQuestion> sujetQuestions,
			Set<Reponse> reponses, Set<ReponseApprenant> reponseApprenants) {
		this.theme = theme;
		this.descriptionQuestion = descriptionQuestion;
		this.coefficient = coefficient;
		this.isQcm = isQcm;
		this.reponseApprenantExamens = reponseApprenantExamens;
		this.sujetQuestions = sujetQuestions;
		this.reponses = reponses;
		this.reponseApprenants = reponseApprenants;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id_question", unique = true, nullable = false)
	public Integer getIdQuestion() {
		return this.idQuestion;
	}

	public void setIdQuestion(Integer idQuestion) {
		this.idQuestion = idQuestion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_theme")
	public Theme getTheme() {
		return this.theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	@Column(name = "description_question", length = 500)
	public String getDescriptionQuestion() {
		return this.descriptionQuestion;
	}

	public void setDescriptionQuestion(String descriptionQuestion) {
		this.descriptionQuestion = descriptionQuestion;
	}

	@Column(name = "coefficient")
	public Integer getCoefficient() {
		return this.coefficient;
	}

	public void setCoefficient(Integer coefficient) {
		this.coefficient = coefficient;
	}

	@Column(name = "is_qcm")
	public Boolean getIsQcm() {
		return this.isQcm;
	}

	public void setIsQcm(Boolean isQcm) {
		this.isQcm = isQcm;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "question")
	public Set<ReponseApprenantExamen> getReponseApprenantExamens() {
		return this.reponseApprenantExamens;
	}

	public void setReponseApprenantExamens(Set<ReponseApprenantExamen> reponseApprenantExamens) {
		this.reponseApprenantExamens = reponseApprenantExamens;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "question")
	public Set<SujetQuestion> getSujetQuestions() {
		return this.sujetQuestions;
	}

	public void setSujetQuestions(Set<SujetQuestion> sujetQuestions) {
		this.sujetQuestions = sujetQuestions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "question")
	public Set<Reponse> getReponses() {
		return this.reponses;
	}

	public void setReponses(Set<Reponse> reponses) {
		this.reponses = reponses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "question")
	public Set<ReponseApprenant> getReponseApprenants() {
		return this.reponseApprenants;
	}

	public void setReponseApprenants(Set<ReponseApprenant> reponseApprenants) {
		this.reponseApprenants = reponseApprenants;
	}

}