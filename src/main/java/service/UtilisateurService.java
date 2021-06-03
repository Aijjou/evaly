package service;

import java.util.Optional;

import dto.UtilisateurDto;
import dto.VerifyCodeDto;
import model.Utilisateur;

public interface UtilisateurService  {

	public Utilisateur createAdmin(UtilisateurDto utilisateurDto) throws Exception;
	
	public Utilisateur createFormateur(UtilisateurDto utilisateurDto);
	
	Optional<Utilisateur> findByUsernameOrEmail(String username, String email);

	Optional<Utilisateur> findByEmail(String email);

	Optional<Utilisateur> findByUsername(String username);

	Optional<Utilisateur> findById(Integer id);

	public void verifyCode(VerifyCodeDto verifyCodeDto);

	public Utilisateur findByEmailAndPassword(String email, String password);
	
	
}
