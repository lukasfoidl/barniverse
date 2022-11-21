package at.barniverse.backend.barniverse_backend.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

/**
 * validator for AfterToday annotation
 */
public class AfterTodayValidator implements ConstraintValidator<AfterToday, Date> {

    /**
     * initializes properties
     * @param constraintAnnotation AfterToday annotation
     */
    @Override
    public void initialize(AfterToday constraintAnnotation) { }

    /**
     * check if annotation is valid or not
     * @param dateField dateField with the date to be checked
     * @param context ConstraintValidatorContext
     * @return true if date is in the future, otherwise false
     */
    @Override
    public boolean isValid(Date dateField, ConstraintValidatorContext context) {
        if (dateField == null) { return false; }

        Date today = new Date();

        return dateField.after(today);
    }
}
