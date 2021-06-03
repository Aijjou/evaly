package service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dto.UtilisateurDto;
import dto.VerifyCodeDto;
import model.Utilisateur;
import repository.UtilisateurRepository;
import service.UtilisateurService;

@Service
public class UtilisateurServiceImpl implements UtilisateurService{

	
	@Autowired
	UtilisateurRepository utilisateurRepository;
	
	
	@Override
	public Utilisateur createAdmin(UtilisateurDto utilisateurDto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Utilisateur createFormateur(UtilisateurDto utilisateurDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Utilisateur> findByUsernameOrEmail(String username, String email) {
	
		return utilisateurRepository.findByNomOrMail(username, email) ;
	}

	@Override
	public Optional<Utilisateur> findByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Utilisateur> findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Utilisateur> findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void verifyCode(VerifyCodeDto verifyCodeDto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Utilisateur findByEmailAndPassword(String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}

}
