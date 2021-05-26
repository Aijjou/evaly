package model;
// Generated 26 mars 2021 � 22:40:09 by Hibernate Tools 5.1.10.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Apprenant generated by hbm2java
 */
@Entity
@Table(name = "apprenant", catalog = "evaly")
@PrimaryKeyJoinColumn(name = "id_utilisateur")
public class Apprenant extends Utilisateur{

	private static final long serialVersionUID = 613789007876810439L;
	private Promotion promotion;
	private String nomPromoString;
	private Utilisateur utilisateur;
	private Set<ReponseApprenantExamen> reponseApprenantExamens = new HashSet<ReponseApprenantExamen>(0);
	private Set<ResultatExamen> resultatExamens = new HashSet<ResultatExamen>(0);
	private Set<ReponseApprenant> reponseApprenants = new HashSet<ReponseApprenant>(0);

	public Apprenant() {
	}

	public Apprenant(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Apprenant(Promotion promotion, Utilisateur utilisateur,
			Set<ReponseApprenantExamen> reponseApprenantExamens, Set<ResultatExamen> resultatExamens,
			Set<ReponseApprenant> reponseApprenants) {

		this.promotion = promotion;
		this.utilisateur = utilisateur;
		this.reponseApprenantExamens = reponseApprenantExamens;
		this.resultatExamens = resultatExamens;
		this.reponseApprenants = reponseApprenants;
	}

//	@EmbeddedId
//
//	@AttributeOverrides({
//			@AttributeOverride(name = "idUtilisateur", column = @Column(name = "id_utilisateur", nullable = false)),
//			@AttributeOverride(name = "idPromotion", column = @Column(name = "id_promotion")) })
//	public ApprenantId getId() {
//		return this.id;
//	}
//
//	public void setId(ApprenantId id) {
//		this.id = id;
//	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_promotion", insertable = false, updatable = false)
	public Promotion getPromotion() {
		return this.promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}
	
	@Transient
	public String getNomPromoString() {
		return nomPromoString;
	}

	public void setNomPromoString(String nomPromoString) {
		this.nomPromoString = nomPromoString;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_utilisateur", nullable = false, insertable = false, updatable = false)
	public Utilisateur getUtilisateur() {
		return this.utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "apprenant")
	public Set<ReponseApprenantExamen> getReponseApprenantExamens() {
		return this.reponseApprenantExamens;
	}

	public void setReponseApprenantExamens(Set<ReponseApprenantExamen> reponseApprenantExamens) {
		this.reponseApprenantExamens = reponseApprenantExamens;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "apprenant")
	public Set<ResultatExamen> getResultatExamens() {
		return this.resultatExamens;
	}

	public void setResultatExamens(Set<ResultatExamen> resultatExamens) {
		this.resultatExamens = resultatExamens;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "apprenant")
	public Set<ReponseApprenant> getReponseApprenants() {
		return this.reponseApprenants;
	}

	public void setReponseApprenants(Set<ReponseApprenant> reponseApprenants) {
		this.reponseApprenants = reponseApprenants;
	}

}