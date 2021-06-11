package dto;

import java.util.Date;
import java.util.List;

public class ApprenantDto {

	private Integer idApprenantDto;
	private String nom;
	private String prenom;
	private String mail;
	private Date dateInscriptionDate;
	private Integer idPromotion;

	public ApprenantDto() {

	}

	public ApprenantDto(Integer idApprenant, String nom, String prenom, String mail, Date dateInscriptionDate,
			Integer idPromotion) {

		super();
		this.idApprenantDto = idApprenant;
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
		this.dateInscriptionDate = dateInscriptionDate;
		this.idPromotion = idPromotion;

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

	public Date getDateInscriptionDate() {
		return dateInscriptionDate;
	}

	public void setDateInscriptionDate(Date dateInscriptionDate) {
		this.dateInscriptionDate = dateInscriptionDate;
	}

	public Integer getIdPromotion() {
		return idPromotion;
	}

	public void setIdPromotion(Integer idPromotion) {
		this.idPromotion = idPromotion;
	}

	@Override
	public String toString() {
		return "ApprenantDto [nom=" + nom + ", prenom=" + prenom + ", mail=" + mail + ", dateInscriptionDate="
				+ dateInscriptionDate + ", idPromotion=" + idPromotion + "]";
	}

}
