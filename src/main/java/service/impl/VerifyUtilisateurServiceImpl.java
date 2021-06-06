package service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.VerifyUtilisateur;
import repository.VerifyUtilisateurRepository;
import service.VerifyUtilisateurService;


@Service
public class VerifyUtilisateurServiceImpl implements VerifyUtilisateurService{

	@Autowired
	private VerifyUtilisateurRepository verifyAccountRepository;
	
	@Override
	public VerifyUtilisateur create(VerifyUtilisateur verifyUtilisateur) {
		
		return verifyAccountRepository.save(verifyUtilisateur);
	}

	@Override
	public Optional<VerifyUtilisateur> findByToken(String token) {
		
		return verifyAccountRepository.findByToken(token);
	}

	@Override
	public Optional<VerifyUtilisateur> findById(Integer id) {
		
		return verifyAccountRepository.findById(id);
	}



}
