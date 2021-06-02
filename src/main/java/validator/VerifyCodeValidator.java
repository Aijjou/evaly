package validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import fr.afpa.formation.model.VerifyAccount;
import fr.afpa.formation.repository.VerifyAccountRepository;
import model.VerifyUtilisateur;


public class VerifyCodeValidator implements ConstraintValidator<ValidVerifyCode, String>{

	@Autowired
	private VerifyAccountRepository verifyAccountDao;
	
	@Override
	public boolean isValid(String token, ConstraintValidatorContext context) {
		
		if(token.isEmpty()) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("Code not be empty")
					.addConstraintViolation();
			return false;
		} else if(!verifyAccountDao.findByToken(token).isPresent()) {
			
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("Code verification not found")
					.addConstraintViolation();
			return false;
			
			
		} else {
			VerifyUtilisateur verifyUtilisateur = verifyAccountDao.findByToken(token).get();
			if(verifyAccount.isExpired()) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate("Verification code has expired")
						.addConstraintViolation();
				return false;
			}
		}
		
		
		return true;
	}
}
