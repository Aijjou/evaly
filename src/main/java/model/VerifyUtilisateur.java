package model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "verify_utilisateur", catalog = "evaly")
//@Table(name = "verify_utilisateur", catalog = "u575564338_evaly")
public class VerifyUtilisateur {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Lob
	@Column(name = "token", columnDefinition = "text", unique = true)
	@org.hibernate.annotations.Type(type = "org.hibernate.type.TextType")
	private String token;

	@Column(name = "expired_data_token")
	private LocalDateTime expiredDataToken;

	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_utilisateur")
	private Utilisateur utilisateur;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getExpiredDataToken() {
		return expiredDataToken;
	}

	public void setExpiredDataToken(LocalDateTime expiredDataToken) {
		this.expiredDataToken = expiredDataToken;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public void setExpiredDataToken(int minutes) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, minutes);
		this.expiredDataToken = now.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public boolean isExpired() {
		return LocalDateTime.now().isAfter(expiredDataToken);
	}

	@Override
	public String toString() {
		return "VerifyUtilisateur [id=" + id + ", token=" + token + ", expiredDataToken=" + expiredDataToken
				+ ", createdDate=" + createdDate + ", utilisateur=" + utilisateur + "]";
	}

}
