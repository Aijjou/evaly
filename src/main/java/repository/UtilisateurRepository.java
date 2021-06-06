package repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import model.Utilisateur;



public interface UtilisateurRepository extends CrudRepository<Utilisateur, Integer>{

	
	
	@Query("select c FROM Utilisateur c where c.nom =:nom or c.mail =:email")
	Optional<Utilisateur> findByNomOrMail(@Param("nom")String nom, @Param("email")String email);

	Optional<Utilisateur> findByMail(String email);

	Optional<Utilisateur> findByNom(String nom);
	
	@Query("select c FROM Utilisateur c where c.email =:email and c.password =:password")
	public Utilisateur findByMailAndPassword(String email, String password);
	
	
}
