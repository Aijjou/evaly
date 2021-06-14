package dto;

import java.util.Date;
import java.util.List;

public class FormateurDto {

	private Integer idFormateurDto;
	private String nom;
	private String prenom;
	private String mail;
	private Date dateInscriptionDate;
	private Integer idGroupe;
	private List<Integer> idMatieres;
	private Boolean active;
	private Boolean isAdmin;
	private Boolean isReferent;

	public FormateurDto(String nom, String prenom, String mail, Date dateInscriptionDate, Integer idGroupe,
			List<Integer> idMatieres, Boolean active, Boolean isAdmin, Boolean isReferent) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
		this.dateInscriptionDate = dateInscriptionDate;
		this.idGroupe = idGroupe;
		this.idMatieres = idMatieres;
		this.active = active;
		this.isAdmin = isAdmin;
		this.isReferent = isReferent;
	}
	
	public FormateurDto() {
    }

	public FormateurDto(Integer idFormateurDto, String nom, String prenom, String mail, Date dateInscriptionDate,
			Integer idGroupe, List<Integer> idMatieres, Boolean active, Boolean isAdmin, Boolean isReferent) {
		super();
		this.idFormateurDto = idFormateurDto;
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
		this.dateInscriptionDate = dateInscriptionDate;
		this.idGroupe = idGroupe;
		this.idMatieres = idMatieres;
		this.active = active;
		this.isAdmin = isAdmin;
		this.isReferent = isReferent;
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

	public Integer getIdGroupe() {
		return idGroupe;
	}

	public void setIdGroupe(Integer idGroupe) {
		this.idGroupe = idGroupe;
	}

	public List<Integer> getIdMatieres() {
		return idMatieres;
	}

	public void setIdMatieres(List<Integer> idMatieres) {
		this.idMatieres = idMatieres;
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

	public Boolean getIsReferent() {
		return isReferent;
	}

	public void setIsReferent(Boolean isReferent) {
		this.isReferent = isReferent;
	}
	

	public Integer getIdFormateurDto() {
		return idFormateurDto;
	}

	public void setIdFormateurDto(Integer idFormateurDto) {
		this.idFormateurDto = idFormateurDto;
	}

	@Override
	public String toString() {
		return "FormateurDto [nom=" + nom + ", prenom=" + prenom + ", mail=" + mail + ", dateInscriptionDate="
				+ dateInscriptionDate + ", idGroupe=" + idGroupe + ", idMatieres=" + idMatieres + ", active=" + active
				+ ", isAdmin=" + isAdmin + ", isReferent=" + isReferent + "]";
	}

}
