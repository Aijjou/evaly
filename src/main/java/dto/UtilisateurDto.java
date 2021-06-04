package dto;

public class UtilisateurDto {

	private String nom;
	private String prenom;
	private String mail;
	private String password;
	private String photo;
	private String questionSecrete;
	private String reponseSecrete;
	
	
	
	public UtilisateurDto(String nom, String prenom, String mail, String password, String photo, String questionSecrete,
			String reponseSecrete) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
		this.password = password;
		this.photo = photo;
		this.questionSecrete = questionSecrete;
		this.reponseSecrete = reponseSecrete;
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



	public String getPhoto() {
		return photo;
	}



	public void setPhoto(String photo) {
		this.photo = photo;
	}



	public String getQuestionSecrete() {
		return questionSecrete;
	}



	public void setQuestionSecrete(String questionSecrete) {
		this.questionSecrete = questionSecrete;
	}



	public String getReponseSecrete() {
		return reponseSecrete;
	}



	public void setReponseSecrete(String reponseSecrete) {
		this.reponseSecrete = reponseSecrete;
	}



	@Override
	public String toString() {
		return "UtilisateurDto [nom=" + nom + ", prenom=" + prenom + ", mail=" + mail + ", password=" + password
				+ ", photo=" + photo + ", questionSecrete=" + questionSecrete + ", reponseSecrete=" + reponseSecrete
				+ "]";
	}
	
	
	
}
