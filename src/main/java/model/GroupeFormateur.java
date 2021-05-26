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
 * GroupeFormateur generated by hbm2java
 */
@Entity
@Table(name = "groupe_formateur", catalog = "evaly")
public class GroupeFormateur implements java.io.Serializable {

	private Integer idGroupeFormateur;
	private Organisation organisation;
	private String nom;
	private Set<FormateurGroupeFormateur> formateurGroupeFormateurs = new HashSet<FormateurGroupeFormateur>(0);
	private Set<Matiere> matieres = new HashSet<Matiere>(0);

	public GroupeFormateur() {
	}

	public GroupeFormateur(Organisation organisation, String nom,
			Set<FormateurGroupeFormateur> formateurGroupeFormateurs, Set<Matiere> matieres) {
		this.organisation = organisation;
		this.nom = nom;
		this.formateurGroupeFormateurs = formateurGroupeFormateurs;
		this.matieres = matieres;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id_groupe_formateur", unique = true, nullable = false)
	public Integer getIdGroupeFormateur() {
		return this.idGroupeFormateur;
	}

	public void setIdGroupeFormateur(Integer idGroupeFormateur) {
		this.idGroupeFormateur = idGroupeFormateur;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_organisation")
	public Organisation getOrganisation() {
		return this.organisation;
	}

	public void setOrganisation(Organisation organisation) {
		this.organisation = organisation;
	}

	@Column(name = "nom", length = 100)
	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "groupeFormateur")
	public Set<FormateurGroupeFormateur> getFormateurGroupeFormateurs() {
		return this.formateurGroupeFormateurs;
	}

	public void setFormateurGroupeFormateurs(Set<FormateurGroupeFormateur> formateurGroupeFormateurs) {
		this.formateurGroupeFormateurs = formateurGroupeFormateurs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "groupeFormateur")
	public Set<Matiere> getMatieres() {
		return this.matieres;
	}

	public void setMatieres(Set<Matiere> matieres) {
		this.matieres = matieres;
	}

}