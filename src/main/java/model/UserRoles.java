//package model;
//
//import static javax.persistence.GenerationType.IDENTITY;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "user_roles", catalog = "u575564338_evaly")
//public class UserRoles implements java.io.Serializable {
//
//	private Integer idUserRolesInteger;
//	private Role role;
//	private Utilisateur utilisateur;
//	
//	@Id
//	@GeneratedValue(strategy = IDENTITY)
//
//	@Column(name = "id_user_roles", unique = true, nullable = false)
//	public Integer getIdUserRolesInteger() {
//		return idUserRolesInteger;
//	}
//
//	public void setIdUserRolesInteger(Integer idUserRolesInteger) {
//		this.idUserRolesInteger = idUserRolesInteger;
//	}
//
//	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "id_roles")
//	public Role getRole() {
//		return role;
//	}
//
//	public void setRole(Role role) {
//		this.role = role;
//	}
//
//	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "id_utilisateur")
//	public Utilisateur getUtilisateur() {
//		return utilisateur;
//	}
//
//	public void setUtilisateur(Utilisateur utilisateur) {
//		this.utilisateur = utilisateur;
//	}
//
//}
