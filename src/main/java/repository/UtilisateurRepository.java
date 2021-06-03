package repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import model.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer>{

	Optional<Utilisateur> findByNomOrMail(String nom, String email);

	Optional<Utilisateur> findByMail(String email);

	Optional<Utilisateur> findByNom(String nom);
	
	@Query("select c FROM Utilisateur c where c.email =:email and c.password =:password")
	public Utilisateur findByMailAndPassword(String email, String password);
	
	
}
