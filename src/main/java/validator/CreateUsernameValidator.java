package validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import service.UtilisateurService;

public class CreateUsernameValidator implements ConstraintValidator<ValidCreateUsername, String>{

	@Autowired
	private UtilisateurService utilisateurService;
	
	@Override
	public void initialize(ValidCreateUsername constraintAnnotation) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
		
		if(utilisateurService.findByUsername(username).isPresent()) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("Username already taken")
					.addConstraintViolation();
			return false;
		}
		
		return true;
	}

}
