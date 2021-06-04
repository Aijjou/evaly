package service;

import java.util.Optional;

import javax.mail.MessagingException;

import dto.FormateurDto;
import dto.UtilisateurDto;
import dto.VerifyCodeDto;
import model.Formateur;
import model.Utilisateur;

public interface UtilisateurService  {

	public Utilisateur createAdmin(UtilisateurDto admin) throws Exception;
	
	public Utilisateur createFormateur(UtilisateurDto formateur);
	
	public Formateur createFormateurParAdmin(FormateurDto formateur) throws MessagingException;
	
	Optional<Utilisateur> findByUsernameOrEmail(String username, String email);

	Optional<Utilisateur> findByEmail(String email);

	Optional<Utilisateur> findByUsername(String username);

	Optional<Utilisateur> findById(Integer id);

	public void verifyCode(VerifyCodeDto verifyCodeDto);

	public Utilisateur findByEmailAndPassword(String email, String password);
	
	public String test(String test);
	
	
}
