package principal;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import model.Utilisateur;

public class UserPrincipal implements UserDetails {

	private static final long serialVersionUID = 4313653395184670905L;

	public Integer id;

	private String username;

	private String prenom;


	private String mail;

	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	private String photo;

	public UserPrincipal() {
		super();

	}

	public UserPrincipal(Integer id, String username, String email, String password,

			Collection<? extends GrantedAuthority> authorities, String prenom, String photo) {

		super();
		this.id = id;
		this.username = username;
		this.prenom = prenom;
		this.mail = email;
		this.password = password;
		this.authorities = authorities;
		this.photo = photo;
	}

	public static UserDetails create(Utilisateur utilisateur) {
		List<GrantedAuthority> authorities = utilisateur.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

		return new UserPrincipal(utilisateur.getIdUtilisateur(), utilisateur.getNom(), utilisateur.getMail(),
				utilisateur.getPassword(), authorities, utilisateur.getPrenom(), utilisateur.getPhoto());

	}

	public Integer getId() {
		return id;
	}

	public String getEmail() {
		return mail;
	}

	public String getPrenom() {
		return prenom;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserPrincipal that = (UserPrincipal) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "UserPrincipal [id=" + id + ", username=" + username + ", prenom=" + prenom + ", mail=" + mail
				+ ", password=" + password + ", authorities=" + authorities + "]";
	}

}