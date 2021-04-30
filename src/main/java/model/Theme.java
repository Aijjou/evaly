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
 * Theme generated by hbm2java
 */
@Entity
@Table(name = "theme", catalog = "evaly")
public class Theme implements java.io.Serializable {

	private Integer idTheme;
	private Matiere matiere;
	private String nom;
	private Set<Question> questions = new HashSet<Question>(0);

	public Theme() {
	}

	public Theme(Matiere matiere, String nom, Set<Question> questions) {
		this.matiere = matiere;
		this.nom = nom;
		this.questions = questions;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id_theme", unique = true, nullable = false)
	public Integer getIdTheme() {
		return this.idTheme;
	}

	public void setIdTheme(Integer idTheme) {
		this.idTheme = idTheme;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_matiere")
	public Matiere getMatiere() {
		return this.matiere;
	}

	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}

	@Column(name = "nom", length = 50)
	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "theme")
	public Set<Question> getQuestions() {
		return this.questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

}
