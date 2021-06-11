package service.impl;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ch.qos.logback.core.joran.conditional.IfAction;
import dto.ApprenantDto;
import dto.ApprenantDtoFinal;
import dto.FormateurDto;
import mail.Mail;
import mail.MailService;
import model.Apprenant;
import model.Formateur;
import model.FormateurGroupeFormateur;
import model.FormateurMatiere;
import model.Promotion;
import model.Role;
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
import service.ApprenantService;
import service.RoleService;
import utils.RandomUtil;

@Service
@Transactional
public class ApprenantServiceImpl implements ApprenantService {

	private final static Log log = LogFactory.getLog(ApprenantServiceImpl.class);

	@Resource
	ApprenantRepository apprenantRepository;

	@Autowired
	private RoleService roleService;

	@Autowired
	private MailService mailService;

	@Autowired
	UtilisateurRepository utilisateurRepository;

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
	public List<Apprenant> apprenants() {

		return (List<Apprenant>) apprenantRepository.findAll();
	}

	@Override
	public Optional<Apprenant> findById(Integer id) {
		return apprenantRepository.findById(id);
	}

	@Override
	public void save(Apprenant s) {
		apprenantRepository.save(s);
	}

	@Override
	public List<Apprenant> apprenantsByPromotion(Promotion promotion) {
		return apprenantRepository.findByPromotion(promotion);
	}

	@Override
	public Apprenant createApprenantParFormateur(ApprenantDto apprenantDto) {

		Promotion promotion = promotionRepository.findById(apprenantDto.getIdPromotion()).get();

		Role role = roleService.findById(3).get();

		Set<Role> listeRoles = new HashSet<>();
		Apprenant apprenant = new Apprenant();

		System.err.println("id " + apprenantDto.getIdApprenantDto());

		if (apprenantDto.getIdApprenantDto() != null) {
			Apprenant apprenantRecup = apprenantRepository.findById(apprenantDto.getIdApprenantDto()).get();
			System.out.println("ici id pas null");
			apprenant.setIdUtilisateur(apprenantDto.getIdApprenantDto());
			apprenant.setDateInscription(apprenantDto.getDateInscriptionDate());
			apprenant.setIsAdmin(false);
			apprenant.setNom(apprenantDto.getNom());
			apprenant.setPrenom(apprenantDto.getPrenom());
			apprenant.setMail(apprenantDto.getMail());
			apprenant.setPromotion(promotion);
			apprenant.setActive(true);
			apprenant.setDateNaissance(apprenantRecup.getDateNaissance());
			apprenant.setPassword(apprenantRecup.getPassword());
			apprenant.setPhoto(apprenantRecup.getPhoto());
			apprenant.setQuestionSecrete(apprenantRecup.getQuestionSecrete());
			apprenant.setReponseSecrete(apprenantRecup.getReponseSecrete());
			apprenant.setRoles(listeRoles);
			apprenant.getRoles().add(role);

		} else {

			System.out.println("ici id null");
			apprenant.setDateInscription(apprenantDto.getDateInscriptionDate());
			apprenant.setIsAdmin(false);
			apprenant.setNom(apprenantDto.getNom());
			apprenant.setPrenom(apprenantDto.getPrenom());
			apprenant.setMail(apprenantDto.getMail());
			apprenant.setPromotion(promotion);

			apprenant.setRoles(listeRoles);
			apprenant.getRoles().add(role);

			String token = RandomUtil.generateRandomStringNumber(6).toUpperCase();

			VerifyUtilisateur verifyUtilisateur = new VerifyUtilisateur();

			verifyUtilisateur.setUtilisateur(apprenant);

			verifyUtilisateur.setCreatedDate(LocalDateTime.now());

			verifyUtilisateur.setExpiredDataToken(LocalDateTime.now().plusDays(1));
			verifyUtilisateur.setToken(token);
			verifyUtilisateurRepository.save(verifyUtilisateur);

			Map<String, Object> maps = new HashMap<>();
			maps.put("utilisateur", apprenant);
			maps.put("token", token);

			Mail mail = new Mail();
			mail.setFrom("postmaster@mg.iteacode.com");
			mail.setSubject("Validation inscription Evaly");
			mail.setTo(apprenant.getMail());
			mail.setModel(maps);
			try {
				mailService.sendEmail(mail);
			} catch (MessagingException e) {

				e.printStackTrace();
			}

		}
		Apprenant apprenant2 = apprenantRepository.save(apprenant);

		System.err.println("apprenant validee " + apprenant2.getPromotion().getIdPromotion());

		return apprenant2;
	}

	@Override
	public boolean delete(Integer id) {

		boolean isDelete = false;

		apprenantRepository.deleteById(id);

		if (apprenantRepository.findById(id) != null) {
			isDelete = true;
		}

		return isDelete;
	}

}
