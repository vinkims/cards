package ke.vincent.cards.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ke.vincent.cards.annotations.IsEmailValid;
import ke.vincent.cards.services.user.IUser;

@Component
public class VEmailValid implements ConstraintValidator<IsEmailValid, String> {

    @Autowired
    private IUser sUser;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null ? true : !sUser.checkExistsByEmail(value);
    }
    
}
