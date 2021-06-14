package service.impl;

import java.io.File;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dto.ApprenantDtoFinal;
import dto.FormateurDto;
import dto.FormateurDtoFinal;
import dto.UtilisateurDto;
import dto.VerifyCodeDto;
import mail.Mail;
import mail.MailService;
import model.Apprenant;
import model.Formateur;
import model.FormateurGroupeFormateur;
import model.FormateurMatiere;
import model.Promotion;
import model.PromotionFormateur;
import model.Role;
import model.Utilisateur;
import model.VerifyUtilisateur;
import repository.ApprenantRepository;
import repository.FormateurGroupeFormateurRepository;
import repository.FormateurMatiereRepository;
import repository.FormateurRepository;
import repository.GroupeRepository;
import repository.MatiereRepository;
import repository.PromotionFormateurRepository;
import repository.PromotionRepository;
import repository.UtilisateurRepository;
import repository.VerifyUtilisateurRepository;
import service.RoleService;
import service.UtilisateurService;
import utils.RandomUtil;

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
	ApprenantRepository apprenantRepository;

	@Autowired
	PromotionRepository promotionRepository;

	@Autowired
	FormateurRepository formateurRepository;

	@Autowired
	VerifyUtilisateurRepository verifyUtilisateurRepository;

	@Autowired
	GroupeRepository groupeRepository;

	@Autowired
	MatiereRepository matiereRepository;

	@Autowired
	FormateurMatiereRepository formateurMatiereRepository;

	@Autowired
	PromotionFormateurRepository promotionFormateurRepository;

	@Autowired
	FormateurGroupeFormateurRepository formateurGroupeFormateurRepository;

	@Override
	public Utilisateur createAdmin(UtilisateurDto admin) throws Exception {

		Utilisateur util = utilisateurRepository.findById(admin.getIdUtilisateurDto()).get();

		Date dateInscription = new Date(System.currentTimeMillis());
		System.err.println("admin createAdmin" + admin);
		Role role = roleService.findById(1).get();
		Utilisateur utilisateur;
		Set<Role> listeRoles = new HashSet<>();

		if (admin.getIdUtilisateurDto() != null) {
			System.err.println("if id admin != null");
			utilisateur = new Utilisateur(admin.getIdUtilisateurDto(), admin.getNom(), admin.getPrenom(),
					admin.getMail(), passwordEncoder.encode(admin.getPassword()), admin.getQuestionSecrete(),
					admin.getReponseSecrete(), dateInscription, null, null, util.getPhoto(), true, listeRoles, true, 1);
			utilisateur.setIdUtilisateur(admin.getIdUtilisateurDto());
			System.err.println("utilisateur admin + " + utilisateur);
			utilisateur.getRoles().add(role);

		} else {
			System.err.println("if id admin == null");
			utilisateur = new Utilisateur(admin.getNom(), admin.getPrenom(), admin.getMail(),
					passwordEncoder.encode(admin.getPassword()), admin.getQuestionSecrete(), admin.getReponseSecrete(),
					dateInscription, null, null, null, true, listeRoles, true, 1);
			utilisateur.getRoles().add(role);
		}

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

		if (formateur.getIdUtilisateurDto() != null) {
			formateur2.setIdUtilisateur(formateur.getIdUtilisateurDto());
		}

		formateur2.setDateInscription(dateInscrDate);
		formateur2.setMail(formateur.getMail());
		formateur2.setNom(formateur.getNom());
		formateur2.setPrenom(formateur.getPrenom());
		formateur2.setPassword(passwordEncoder.encode(formateur.getPassword()));
		formateur2.setQuestionSecrete(formateur.getQuestionSecrete());
		formateur2.setReponseSecrete(formateur.getReponseSecrete());
		formateur2.setIsAdmin(formateur.getIsAdmin());
		formateur2.setPhoto(
				"https://images.generated.photos/TQmTFL6XoEssdRfGWvJZelhy_eIvoy-mLKQFaGOo1iI/rs:fit:512:512/Z3M6Ly9nZW5lcmF0/ZWQtcGhvdG9zL3Yz/XzA3OTUwMTYuanBn.jpg");
		System.err.println(">>>>> formateur " + formateur2);
		formateur2.setRoles(listeRoles);
		formateur2.getRoles().add(role);
		formateur2.setIsAdmin(false);
		formateur2.setIsReferent(false);

		Formateur formateur3 = formateurRepository.save(formateur2);

		return formateur3;
	}

	@Override
	public Formateur createFormateurParAdmin(FormateurDto formateur) throws MessagingException {

		FormateurGroupeFormateur formateurGroupeFormateur = new FormateurGroupeFormateur();

		Date dateInscrDate = new Date(System.currentTimeMillis());

		Role role = roleService.findById(2).get();

		Set<Role> listeRoles = new HashSet<>();
		Formateur formateur2 = new Formateur();

		formateur2.setDateInscription(formateur.getDateInscriptionDate());
		formateur2.setMail(formateur.getMail());
		formateur2.setNom(formateur.getNom());
		formateur2.setPrenom(formateur.getPrenom());

		formateur2.setIsAdmin(false);
		formateur2.setRoles(listeRoles);
		formateur2.getRoles().add(role);

		formateur2.setIsReferent(formateur.getIsReferent());

		String token = RandomUtil.generateRandomStringNumber(6).toUpperCase();

		VerifyUtilisateur verifyUtilisateur = new VerifyUtilisateur();

		verifyUtilisateur.setUtilisateur(formateur2);

		verifyUtilisateur.setCreatedDate(LocalDateTime.now());

		verifyUtilisateur.setExpiredDataToken(LocalDateTime.now().plusDays(1));
		verifyUtilisateur.setToken(token);
		verifyUtilisateurRepository.save(verifyUtilisateur);

		Map<String, Object> maps = new HashMap<>();
		maps.put("utilisateur", formateur2);
		maps.put("token", token);

		Mail mail = new Mail();
		mail.setFrom("postmaster@mg.iteacode.com");
		mail.setSubject("Validation inscription Evaly");
		mail.setTo(formateur2.getMail());
		mail.setModel(maps);
		mailService.sendEmail(mail);

		Formateur formateur3 = formateurRepository.save(formateur2);

		for (int i = 0; i < formateur.getIdMatieres().size(); i++) {
			FormateurMatiere formateurMatiere = new FormateurMatiere();
			formateurMatiere.setFormateur(formateur3);
			formateurMatiere.setMatiere(matiereRepository.findById(formateur.getIdMatieres().get(i)).get());
			formateurMatiereRepository.save(formateurMatiere);
		}

		formateurGroupeFormateur.setFormateur(formateur3);
		formateurGroupeFormateur.setGroupeFormateur(groupeRepository.findById(formateur.getIdGroupe()).get());
		formateurGroupeFormateurRepository.save(formateurGroupeFormateur);

		return formateur3;
	}

	@Override
	public Formateur createFormateurFinal(FormateurDtoFinal formateurDtoFinal) {

		Role role = roleService.findById(2).get();

		File file = new File(formateurDtoFinal.getPhoto().getOriginalFilename());

		System.err.println(role);
		Set<Role> listeRoles = new HashSet<>();

		Formateur formateur2 = new Formateur();
		formateur2.setIdUtilisateur(formateurDtoFinal.getIdFormateurDto());
		formateur2.setNom(formateurDtoFinal.getNom());
		formateur2.setPrenom(formateurDtoFinal.getPrenom());
		formateur2.setMail(formateurDtoFinal.getMail());
		formateur2.setPassword(passwordEncoder.encode(formateurDtoFinal.getPassword()));
		formateur2.setDateInscription(formateurDtoFinal.getDateInscriptionDate());
		formateur2.setDateNaissance(formateurDtoFinal.getDateNaissance());
		formateur2.setPhoto("/images/" + file.getName());
		System.err.println(">>>>> formateur " + formateur2);
		formateur2.setActive(true);
		formateur2.setIsAdmin(false);
		formateur2.setRoles(listeRoles);
		formateur2.getRoles().add(role);
		formateur2.setIsReferent(formateurDtoFinal.getIsReferent());
		formateur2.setQuestionSecrete(formateurDtoFinal.getQuestion());
		formateur2.setReponseSecrete(formateurDtoFinal.getReponse());

		Formateur formateur3 = formateurRepository.save(formateur2);

		if (formateurDtoFinal.getIdFormateurDto() != null) {
			List<PromotionFormateur> promotionFormateurs = promotionFormateurRepository.findByFormateur(formateur2);

			for (PromotionFormateur promotionFormateur : promotionFormateurs) {
				promotionFormateurRepository.delete(promotionFormateur);
			}

		}

		for (int i = 0; i < formateurDtoFinal.getIdPromotions().size(); i++) {
			PromotionFormateur promotionFormateur = new PromotionFormateur();
			Promotion promotion = promotionRepository.findById(formateurDtoFinal.getIdPromotions().get(i)).get();
			promotionFormateur.setFormateur(formateur3);
			promotionFormateur.setPromotion(promotion);
			promotionFormateurRepository.save(promotionFormateur);

		}

		return formateur3;
	}

	@Override
	public Apprenant createApprenantFinal(ApprenantDtoFinal apprenantDtoFinal) {

		Role role = roleService.findById(3).get();

		System.err.println(">>>> " + role);
		System.err.println(" >>>>> " + apprenantDtoFinal.getIdApprenantDto());
		File file = new File(apprenantDtoFinal.getPhoto().getOriginalFilename());
		Set<Role> listeRoles = new HashSet<>();

		System.err.println(">>>> " + role);

		Apprenant apprenant = new Apprenant();
		apprenant.setIdUtilisateur(apprenantDtoFinal.getIdApprenantDto());
		apprenant.setNom(apprenantDtoFinal.getNom());
		apprenant.setPrenom(apprenantDtoFinal.getPrenom());
		apprenant.setMail(apprenantDtoFinal.getMail());
		apprenant.setPhoto("/images/" + file.getName());
		apprenant.setPassword(passwordEncoder.encode(apprenantDtoFinal.getPassword()));
		apprenant.setDateInscription(apprenant.getDateInscription());
		apprenant.setDateNaissance(apprenantDtoFinal.getDateNaissance());
		apprenant.setActive(true);
		apprenant.setIsAdmin(false);
		apprenant.setRoles(listeRoles);
		apprenant.getRoles().add(role);
		apprenant.setQuestionSecrete(apprenantDtoFinal.getQuestion());
		apprenant.setReponseSecrete(apprenantDtoFinal.getReponse());

		System.err.println(" >>>>> dtoAppreFinla " + apprenantDtoFinal.toString());

		Promotion promotion = promotionRepository.findById(apprenantDtoFinal.getIdPromotion()).get();
		System.err.println("<<<<>>>>> " + promotion);
		apprenant.setPromotion(promotion);
		Apprenant apprenant3 = apprenantRepository.save(apprenant);

		return apprenant3;

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
	public Optional<Formateur> findById1(Integer id) {

		return formateurRepository.findById(id);
	}

	@Override
	public Optional<Apprenant> findById2(Integer id) {

		return apprenantRepository.findById(id);
	}

	@Override
	public Boolean verifyCode(VerifyCodeDto verifyCodeDto) {
		String token = verifyCodeDto.getToken();

		VerifyUtilisateur verifyUtilisateur = verifyUtilisateurRepository.findByToken(token).get();
		Utilisateur utilisateur = verifyUtilisateur.getUtilisateur();
		utilisateur.setActive(true);

		System.err.println(utilisateur.getActive());
		if (utilisateurRepository.save(utilisateur) != null) {

			return true;
		}

		return false;

	}

	@Override
	public Utilisateur findByEmailAndPassword(String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Utilisateur> findById(Integer id) {

		return utilisateurRepository.findById(id);
	}

}
