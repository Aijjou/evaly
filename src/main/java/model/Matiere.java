package model;
// Generated 26 mars 2021 � 22:40:09 by Hibernate Tools 5.1.10.Final

import static javax.persistence.GenerationType.IDENTITY;

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

/**
 * Matiere generated by hbm2java
 */
@Entity
@Table(name = "matiere", catalog = "evaly")
public class Matiere implements java.io.Serializable {

	private Integer idMatiere;
	private GroupeFormateur groupeFormateur;
	private String nom;
	private Set<FormateurMatiere> formateurMatieres = new HashSet<FormateurMatiere>(0);
	private Set<Theme> themes = new HashSet<Theme>(0);

	public Matiere() {
	}

	public Matiere(GroupeFormateur groupeFormateur, String nom, Set<FormateurMatiere> formateurMatieres,
			Set<Theme> themes) {
		this.groupeFormateur = groupeFormateur;
		this.nom = nom;
		this.formateurMatieres = formateurMatieres;
		this.themes = themes;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id_matiere", unique = true, nullable = false)
	public Integer getIdMatiere() {
		return this.idMatiere;
	}

	public void setIdMatiere(Integer idMatiere) {
		this.idMatiere = idMatiere;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_groupe_formateur")
	public GroupeFormateur getGroupeFormateur() {
		return this.groupeFormateur;
	}

	public void setGroupeFormateur(GroupeFormateur groupeFormateur) {
		this.groupeFormateur = groupeFormateur;
	}

	@Column(name = "nom", length = 50)
	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "matiere")
	public Set<FormateurMatiere> getFormateurMatieres() {
		return this.formateurMatieres;
	}

	public void setFormateurMatieres(Set<FormateurMatiere> formateurMatieres) {
		this.formateurMatieres = formateurMatieres;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "matiere")
	public Set<Theme> getThemes() {
		return this.themes;
	}

	public void setThemes(Set<Theme> themes) {
		this.themes = themes;
	}

	@Override
	public String toString() {
		return "Matiere [idMatiere=" + idMatiere + ", groupeFormateur=" + groupeFormateur.getNom() + ", nom=" + nom
				+"]";
	}

	
	
}