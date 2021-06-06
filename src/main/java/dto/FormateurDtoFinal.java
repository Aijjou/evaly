package dto;

import java.util.Date;

import ognl.InappropriateExpressionException;

public class FormateurDtoFinal {

	private Integer idFormateurDto;
	private String nom;
	private String prenom;
	private String mail;
	private String password;
	private Date dateNaissance;
	private String question;
	private String reponse;
	private Integer idPromotion;

	public FormateurDtoFinal(Integer idFormateurDto, String nom, String prenom, String mail, String password,
			Date dateNaissance, String question, String reponse, Integer idPromotion) {
		super();
		this.idFormateurDto = idFormateurDto;
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
		this.password = password;
		this.dateNaissance = dateNaissance;
		this.question = question;
		this.reponse = reponse;
		this.idPromotion = idPromotion;
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

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
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

	public Integer getIdFormateurDto() {
		return idFormateurDto;
	}

	public void setIdFormateurDto(Integer idFormateurDto) {
		this.idFormateurDto = idFormateurDto;
	}

	@Override
	public String toString() {
		return "FormateurDtoFinal [nom=" + nom + ", prenom=" + prenom + ", mail=" + mail + ", password=" + password
				+ ", dateNaissance=" + dateNaissance + ", question=" + question + ", reponse=" + reponse
				+ ", idPromotion=" + idPromotion + "]";
	}

}
