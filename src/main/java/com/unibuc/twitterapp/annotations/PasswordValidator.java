package com.unibuc.twitterapp.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private ValidPassword validPassword;

    @Override
    public void initialize(ValidPassword validPassword) {
        this.validPassword = validPassword;
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext arg1) {

        return !Objects.isNull(password) && validatePassword(password);
    }

    /**
     * Returns true if the given password is valid
     *
     * @param password password string
     * @return
     */
    private boolean validatePassword(String password) {
        // contains digits
        if (!password.matches(".*\\d.*")) {
            return false;
        }
        // contains at least one upper-case and one lower-case letter
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z]).{1,}$")) {
            return false;
        }
        //  contains at least one special character from the list: [@, #, $, %, ^, &, +, =, !]
        return password.matches("^(?=.*[@#$%^&+=!]).{1,}$");
    }

}

