package principal;



import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import model.Utilisateur;
import service.UtilisateurService;



@Service
public class CustomUtilisateurDetailsService implements UserDetailsService {

	
	
	@Autowired
	UtilisateurService utilisateurService;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		System.out.println("load-user" + usernameOrEmail);
		Utilisateur utilisateur = utilisateurService.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(
				() -> new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail));

		System.out.println( "coucou user" + utilisateur);
		
		if (utilisateur != null) {
			return UserPrincipal.create(utilisateur);
		}

		throw new UsernameNotFoundException("User '" + usernameOrEmail + "' not found");
	}

}
