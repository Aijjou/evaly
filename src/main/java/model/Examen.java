package model;
// Generated 26 mars 2021 � 22:40:09 by Hibernate Tools 5.1.10.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Examen generated by hbm2java
 */
@Entity
@Table(name = "examen", catalog = "evaly")
public class Examen implements java.io.Serializable {

	private Integer idExamen;
	private Formateur formateur;
	private String titre;
	private Date dateExamen;
	private Integer dureeExamen;
	private Set<ReponseApprenantExamen> reponseApprenantExamens = new HashSet<ReponseApprenantExamen>(0);
	private Set<ResultatExamen> resultatExamens = new HashSet<ResultatExamen>(0);
	private Set<ReponseApprenant> reponseApprenants = new HashSet<ReponseApprenant>(0);
	private Promotion promotion;
	private Sujet sujet;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_promotion")
	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_sujet")
	public Sujet getSujet() {
		return sujet;
	}

	public void setSujet(Sujet sujet) {
		this.sujet = sujet;
	}

	public Examen() {
	}

	public Examen(Formateur formateur, String titre, Date dateExamen, Integer dureeExamen,
			Set<ReponseApprenantExamen> reponseApprenantExamens, Set<ResultatExamen> resultatExamens,
		 Set<ReponseApprenant> reponseApprenants) {
		this.formateur = formateur;
		this.titre = titre;
		this.dateExamen = dateExamen;
		this.dureeExamen = dureeExamen;
		this.reponseApprenantExamens = reponseApprenantExamens;
		this.resultatExamens = resultatExamens;
		this.reponseApprenants = reponseApprenants;
	}
	
	

	@Override
	public String toString() {
		return "Examen [idExamen=" + idExamen + ", titre=" + titre + ", dateExamen="
				+ dateExamen + ", dureeExamen=" + dureeExamen + ", promotion=" + promotion.getNom() + ", sujet=" + sujet.getNom() + "]";
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id_examen", unique = true, nullable = false)
	public Integer getIdExamen() {
		return this.idExamen;
	}

	public void setIdExamen(Integer idExamen) {
		this.idExamen = idExamen;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_formateur")
	public Formateur getFormateur() {
		return this.formateur;
	}

	public void setFormateur(Formateur formateur) {
		this.formateur = formateur;
	}

	@Column(name = "titre", length = 100)
	public String getTitre() {
		return this.titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date_examen", length = 10)
	public Date getDateExamen() {
		return this.dateExamen;
	}

	public void setDateExamen(Date dateExamen) {
		this.dateExamen = dateExamen;
	}

	@Column(name = "duree_examen")
	public Integer getDureeExamen() {
		return this.dureeExamen;
	}

	public void setDureeExamen(Integer dureeExamen) {
		this.dureeExamen = dureeExamen;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "examen")
	public Set<ReponseApprenantExamen> getReponseApprenantExamens() {
		return this.reponseApprenantExamens;
	}

	public void setReponseApprenantExamens(Set<ReponseApprenantExamen> reponseApprenantExamens) {
		this.reponseApprenantExamens = reponseApprenantExamens;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "examen")
	public Set<ResultatExamen> getResultatExamens() {
		return this.resultatExamens;
	}

	public void setResultatExamens(Set<ResultatExamen> resultatExamens) {
		this.resultatExamens = resultatExamens;
	}


	@OneToMany(fetch = FetchType.LAZY, mappedBy = "examen")
	public Set<ReponseApprenant> getReponseApprenants() {
		return this.reponseApprenants;
	}

	public void setReponseApprenants(Set<ReponseApprenant> reponseApprenants) {
		this.reponseApprenants = reponseApprenants;
	}

}