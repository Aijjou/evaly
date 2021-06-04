package service;

import java.util.Optional;

import model.VerifyUtilisateur;

public interface VerifyUtilisateurService {
	
	
	VerifyUtilisateur create(VerifyUtilisateur verifyUtilisateur);

	Optional<VerifyUtilisateur> findByToken(String token);

	Optional<VerifyUtilisateur> findById(Integer id);

}
