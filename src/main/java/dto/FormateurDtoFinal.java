package dto;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class FormateurDtoFinal {

	private Integer idFormateurDto;
	private String nom;
	private String prenom;
	private String mail;
	private String password;
	private Date dateInscriptionDate;
	private Date dateNaissance;
	private Boolean active;
	private MultipartFile photo;
	private Boolean isAdmin;
	private Boolean isReferent;
	private String question;
	private String reponse;
	private List<Integer> idPromotions;
	private String dateNaissanceString;

	public FormateurDtoFinal(Integer idFormateurDto, String nom, String prenom, String mail, String password,
			Date dateInscriptionDate, Date dateNaissance, Boolean active, MultipartFile photo, Boolean isAdmin,
			Boolean isReferent, String question, String reponse, List<Integer> idPromotions) {
		super();
		this.idFormateurDto = idFormateurDto;
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
		this.password = password;
		this.dateInscriptionDate = dateInscriptionDate;
		this.dateNaissance = dateNaissance;
		this.active = active;
		this.photo = photo;
		this.isAdmin = isAdmin;
		this.isReferent = isReferent;
		this.question = question;
		this.reponse = reponse;
		this.idPromotions = idPromotions;
	}

	
	
	public String getDateNaissanceString() {
		return dateNaissanceString;
	}



	public void setDateNaissanceString(String dateNaissanceString) {
		this.dateNaissanceString = dateNaissanceString;
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

	public List<Integer> getIdPromotions() {
		return idPromotions;
	}

	public void setIdPromotions(List<Integer> idPromotions) {
		this.idPromotions = idPromotions;
	}

	public Date getDateInscriptionDate() {
		return dateInscriptionDate;
	}

	public void setDateInscriptionDate(Date dateInscriptionDate) {
		this.dateInscriptionDate = dateInscriptionDate;
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

	public MultipartFile getPhoto() {
		return photo;
	}

	public void setPhoto(MultipartFile photo) {
		this.photo = photo;
	}

	@Override
	public String toString() {
		return "FormateurDtoFinal [idFormateurDto=" + idFormateurDto + ", nom=" + nom + ", prenom=" + prenom + ", mail="
				+ mail + ", password=" + password + ", dateInscriptionDate=" + dateInscriptionDate + ", dateNaissance="
				+ dateNaissance + ", active=" + active + ", photo=" + photo + ", isAdmin=" + isAdmin + ", isReferent="
				+ isReferent + ", question=" + question + ", reponse=" + reponse + ", idPromotions=" + idPromotions
				+ "]";
	}

}
