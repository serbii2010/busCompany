package net.thumbtack.school.buscompany.validator;

import net.thumbtack.school.buscompany.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueLoginValidator implements ConstraintValidator<UniqueLogin, String> {
    @Autowired
    private AccountService accountService;

    @Override
    public boolean isValid(String login, ConstraintValidatorContext constraintValidatorContext) {
        return accountService.getAccountByLogin(login) == null;
    }
}
