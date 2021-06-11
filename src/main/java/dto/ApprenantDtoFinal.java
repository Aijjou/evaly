package dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class ApprenantDtoFinal {

	private Integer idApprenantDto;
	private String nom;
	private String prenom;
	private String mail;
	private String password;
	private Date dateInscriptionDate;
	private Date dateNaissance;
	private Boolean active;
	private Boolean isAdmin;
	private String question;
	private String reponse;
	private Integer idPromotion;
	private MultipartFile photo;

	public ApprenantDtoFinal() {

	}

	public ApprenantDtoFinal(Integer idApprenantDto, String nom, String prenom, String mail, String password,
			Date dateInscriptionDate, Date dateNaissance, Boolean active, Boolean isAdmin, String question,
			String reponse, Integer idPromotion, MultipartFile photo) {
		super();
		this.idApprenantDto = idApprenantDto;
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
		this.password = password;
		this.dateInscriptionDate = dateInscriptionDate;
		this.dateNaissance = dateNaissance;
		this.active = active;
		this.isAdmin = isAdmin;
		this.question = question;
		this.reponse = reponse;
		this.idPromotion = idPromotion;
		this.photo = photo;
	}

	public Integer getIdApprenantDto() {
		return idApprenantDto;
	}

	public void setIdApprenantDto(Integer idApprenantDto) {
		this.idApprenantDto = idApprenantDto;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDateInscriptionDate() {
		return dateInscriptionDate;
	}

	public void setDateInscriptionDate(Date dateInscriptionDate) {
		this.dateInscriptionDate = dateInscriptionDate;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getReponse() {
		return reponse;
	}

	public void setReponse(String reponse) {
		this.reponse = reponse;
	}

	public Integer getIdPromotion() {
		return idPromotion;
	}

	public void setIdPromotion(Integer idPromotion) {
		this.idPromotion = idPromotion;
	}
	

	public MultipartFile getPhoto() {
		return photo;
	}

	public void setPhoto(MultipartFile photo) {
		this.photo = photo;
	}

	@Override
	public String toString() {
		return "ApprenantDtoFinal [idApprenantDto=" + idApprenantDto + ", nom=" + nom + ", prenom=" + prenom + ", mail="
				+ mail + ", password=" + password + ", dateInscriptionDate=" + dateInscriptionDate + ", dateNaissance="
				+ dateNaissance + ", active=" + active + ", isAdmin=" + isAdmin + ", question=" + question
				+ ", reponse=" + reponse + ", idPromotion=" + idPromotion + "]";
	}

}
