package dto;

public class AdminEtablissementDto {

	private String name;
	private Integer numero;
	private String rue;
	private String ville;
	private String code;
	private Boolean isFormateur;
	private String nomReferent;
	private String prenomReferent;
	private String mail;
	private String password;
	private String question;
	private String reponse;

	public AdminEtablissementDto(String nom, Integer numero, String rue, String ville, String code, Boolean isFormateur,
			String nomReferent, String prenomReferent, String mail, String password, String question, String reponse) {
		super();
		this.name = nom;
		this.numero = numero;
		this.rue = rue;
		this.ville = ville;
		this.code = code;

		this.isFormateur = isFormateur;
		this.nomReferent = nomReferent;
		this.prenomReferent = prenomReferent;
		this.mail = mail;
		this.password = password;
		this.question = question;
		this.reponse = reponse;
	}

	public AdminEtablissementDto() {

	}

	public String getNom() {
		return name;
	}

	public void setNom(String nom) {
		this.name = nom;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getIsFormateur() {
		return isFormateur;
	}

	public void setIsFormateur(Boolean isFormateur) {
		this.isFormateur = isFormateur;
	}

	public String getNomReferent() {
		return nomReferent;
	}

	public void setNomReferent(String nomReferent) {
		this.nomReferent = nomReferent;
	}

	public String getPrenomReferent() {
		return prenomReferent;
	}

	public void setPrenomReferent(String prenomReferent) {
		this.prenomReferent = prenomReferent;
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

	@Override
	public String toString() {
		return "AdminEtablissementDto [nom=" + name + ", numero=" + numero + ", rue=" + rue + ", ville=" + ville
				+ ", code=" + code +  ", isFormateur=" + isFormateur + ", nomReferent="
				+ nomReferent + ", prenomReferent=" + prenomReferent + ", mail=" + mail + ", password=" + password
				+ ", question=" + question + ", reponse=" + reponse + "]";
	}

}
