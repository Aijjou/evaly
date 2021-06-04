package validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import model.VerifyUtilisateur;
import repository.VerifyUtilisateurRepository;


public class VerifyCodeValidator implements ConstraintValidator<ValidVerifyCode, String>{

	@Autowired
	private VerifyUtilisateurRepository verifyUtilisateurRepository;
	
	@Override
	public boolean isValid(String token, ConstraintValidatorContext context) {
		
		if(token.isEmpty()) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("Code not be empty")
					.addConstraintViolation();
			return false;
		} else if(!verifyUtilisateurRepository.findByToken(token).isPresent()) {
			
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("Code verification not found")
					.addConstraintViolation();
			return false;
			
			
		} else {
			VerifyUtilisateur verifyUtilisateur = verifyUtilisateurRepository.findByToken(token).get();
			if(verifyUtilisateur.isExpired()) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate("Verification code has expired")
						.addConstraintViolation();
				return false;
			}
		}
		
		
		return true;
	}
}
