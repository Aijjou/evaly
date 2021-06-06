package repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import model.VerifyUtilisateur;

public interface VerifyUtilisateurRepository extends JpaRepository<VerifyUtilisateur, Integer> {
	
	
	Optional<VerifyUtilisateur> findByToken(String token);
	
	
	
	
	
}
