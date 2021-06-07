package model;
// Generated 26 mars 2021 � 22:40:09 by Hibernate Tools 5.1.10.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Utilisateur generated by hbm2java
 */
@Entity
@Table(name = "utilisateur", catalog = "evaly")
@Inheritance(strategy = InheritanceType.JOINED)
public class Utilisateur implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Integer idUtilisateur;
	private String nom;
	private String prenom;
	private String mail;
	private String password;
	private String photo;
	private String questionSecrete;
	private String reponseSecrete;
	private Date dateNaissance;
	private Date dateInscription;
	private Boolean active;
	private Boolean isAdmin;
	private Set<Role> roles;
	private Set<VerifyUtilisateur> verifyUtilisateurs;
	private Set<Formateur> formateurs = new HashSet<Formateur>(0);
	private Set<Apprenant> apprenants = new HashSet<Apprenant>(0);

	public Utilisateur() {
	}

	public Utilisateur(String nom, String prenom, String mail, String password, String questionSecrete,
			String reponseSecrete, Date dateInscription, Set<Formateur> formateurs, Set<Apprenant> apprenants,
			String photo, Boolean active,Set<Role> roles, Boolean isAdmin) {
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
		this.photo = photo;
		this.password = password;
		this.questionSecrete = questionSecrete;
		this.reponseSecrete = reponseSecrete;
		this.dateInscription = dateInscription;
		this.formateurs = formateurs;
		this.apprenants = apprenants;
		this.active = active;
		this.roles = roles;
		this.isAdmin = isAdmin;
		
	}
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_utilisateur", unique = true, nullable = false)
	public Integer getIdUtilisateur() {
		return this.idUtilisateur;
	}

	public void setIdUtilisateur(Integer idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}


    @JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", catalog = "evaly", joinColumns = {
			@JoinColumn(name = "id_utilisateur", referencedColumnName = "id_utilisateur") }, inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id_role"))
	public Set<Role> getRoles() {
		return roles;
	}

    @Column(name = "is_active")
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}


	@Column(name = "nom", length = 100)
	public String getNom() {
		return this.nom;
	}
	
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "utilisateur", cascade = CascadeType.ALL)
	public Set<VerifyUtilisateur> getVerifyUtilisateurs() {
		return verifyUtilisateurs;
	}

	public void setVerifyUtilisateurs(Set<VerifyUtilisateur> verifyUtilisateurs) {
		this.verifyUtilisateurs = verifyUtilisateurs;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@Column(name = "photo", length = 100)
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Column(name = "prenom", length = 100)
	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	@Column(name = "mail", length = 100)
	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@Column(name = "password", length = 100)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "question_secrete", length = 200)
	public String getQuestionSecrete() {
		return this.questionSecrete;
	}

	public void setQuestionSecrete(String questionSecrete) {
		this.questionSecrete = questionSecrete;
	}

	@Column(name = "reponse_secrete", length = 200)
	public String getReponseSecrete() {
		return this.reponseSecrete;
	}

	public void setReponseSecrete(String reponseSecrete) {
		this.reponseSecrete = reponseSecrete;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date_inscription", length = 10)
	public Date getDateInscription() {
		return this.dateInscription;
	}

	public void setDateInscription(Date dateInscription) {
		this.dateInscription = dateInscription;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "utilisateur")
	public Set<Formateur> getFormateurs() {
		return this.formateurs;
	}

	public void setFormateurs(Set<Formateur> formateurs) {
		this.formateurs = formateurs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "utilisateur")
	public Set<Apprenant> getApprenants() {
		return this.apprenants;
	}

	public void setApprenants(Set<Apprenant> apprenants) {
		this.apprenants = apprenants;
	}

	
	@Column(name="is_admin")
	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	

	@Column(name = "date_naissance", length = 200)
	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	@Override
	public String toString() {
		return "Utilisateur [idUtilisateur=" + idUtilisateur + ", nom=" + nom + ", prenom=" + prenom + ", mail=" + mail
				+ ", photo=" + photo + ", questionSecrete=" + questionSecrete
				+ ", reponseSecrete=" + reponseSecrete + ", dateInscription=" + dateInscription + ", active=" + active
				+ ", isAdmin=" + isAdmin + ", roles=" + roles + ", verifyUtilisateurs=" + verifyUtilisateurs
				+ ", formateurs=" + formateurs + ", apprenants=" + apprenants + "]";
	}


	

}