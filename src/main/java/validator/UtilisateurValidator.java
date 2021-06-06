package validator;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import dto.UtilisateurDto;
import service.UtilisateurService;

@Component
public class UtilisateurValidator implements Validator {

	private static final Pattern EMAIL_REGEX = Pattern.compile("^[\\w\\d._-]+@[\\w\\d.-]+\\.[\\w\\d]{2,6}$");
	private static final Pattern PASSWORD_REGEX = Pattern
			.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
	private static final Pattern NAME_REGEX = Pattern.compile("^[A-Za-z]*$");

	@Autowired
	UtilisateurService utilisateurService;

	@Override
	public boolean supports(Class<?> clazz) {

		return UtilisateurDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "nom", "compte.nom.empty");
		ValidationUtils.rejectIfEmpty(errors, "prenom", "compte.prenom.empty");
		ValidationUtils.rejectIfEmpty(errors, "email", "compte.email.empty");
		ValidationUtils.rejectIfEmpty(errors, "password", "compte.password.empty");

		UtilisateurDto compte = (UtilisateurDto) target;

		if (compte.getMail() != null && !EMAIL_REGEX.matcher(compte.getMail()).matches()) {

			errors.rejectValue("email", "compte.email", "Le format du mail est incorrect");

		}

		if (compte.getPassword() != null && !PASSWORD_REGEX.matcher(compte.getPassword()).matches()) {

			errors.rejectValue("password", "compte.password", "Le mot de passe est incorrect");
		}

		if (compte.getNom() != null && !NAME_REGEX.matcher(compte.getNom()).matches()) {

			errors.rejectValue("name", "compte.nom", "Uniquement des caractères alphabétiques");
		}

		if (compte.getPrenom() != null && !NAME_REGEX.matcher(compte.getPrenom()).matches()) {

			errors.rejectValue("name", "compte.nom", "Uniquement des caractères alphabétiques");
		}

	}

}
