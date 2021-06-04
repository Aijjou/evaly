package service.impl;

import java.sql.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dto.UtilisateurDto;
import dto.VerifyCodeDto;
import mail.MailService;
import model.Formateur;
import model.Role;
import model.Utilisateur;
import repository.FormateurRepository;
import repository.UtilisateurRepository;
import service.RoleService;
import service.UtilisateurService;

@Service
@Transactional
public class UtilisateurServiceImpl implements UtilisateurService {

	private final static Log log = LogFactory.getLog(UtilisateurServiceImpl.class);

	@Autowired
	private RoleService roleService;

	@Autowired
	private MailService mailService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	UtilisateurRepository utilisateurRepository;

	@Autowired
	FormateurRepository formateurRepository;

	@Override
	public Utilisateur createAdmin(UtilisateurDto admin) throws Exception {

		Date dateInscription = new Date(System.currentTimeMillis());
		System.err.println(admin);
		Role role = roleService.findById(1).get();

		Set<Role> listeRoles = new HashSet<>();

		Utilisateur utilisateur = new Utilisateur(admin.getNom(), admin.getPrenom(), admin.getMail(),
				passwordEncoder.encode(admin.getPassword()), admin.getQuestionSecrete(), admin.getReponseSecrete(),
				dateInscription, null, null, null, true, listeRoles, true);
		utilisateur.getRoles().add(role);

		log.info(utilisateur);

		return utilisateurRepository.save(utilisateur);
	}

	@Override
	public Utilisateur createFormateur(UtilisateurDto formateur) {

		Date dateInscrDate = new Date(System.currentTimeMillis());

		System.err.println(dateInscrDate);

		Role role = roleService.findById(2).get();

		System.err.println(role);
		Set<Role> listeRoles = new HashSet<>();
		Formateur formateur2 = new Formateur();

		formateur2.setActive(true);

		formateur2.setDateInscription(dateInscrDate);
		formateur2.setMail(formateur.getMail());
		formateur2.setNom(formateur.getNom());
		formateur2.setPrenom(formateur.getPrenom());
		formateur2.setPassword(passwordEncoder.encode(formateur.getPassword()));
		formateur2.setQuestionSecrete(formateur.getQuestionSecrete());
		formateur2.setReponseSecrete(formateur.getReponseSecrete());
		formateur2.setIsAdmin(true);
		formateur2.setRoles(listeRoles);
		formateur2.getRoles().add(role);
		formateur2.setIsAdmin(false);
		formateur2.setIsReferent(false);

		Formateur formateur3 = formateurRepository.save(formateur2);

		return formateur3;
	}

	@Override
	public Optional<Utilisateur> findByUsernameOrEmail(String username, String email) {

		System.err.println("find " + username + " " + email);
		
		Optional<Utilisateur> utilisateur = utilisateurRepository.findByNomOrMail(username, email);

		System.out.println("ds le repository :" + utilisateur);
		
		return utilisateur;
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

	public String test(String test) {

		System.out.println(test);
		return test;
	}

}
