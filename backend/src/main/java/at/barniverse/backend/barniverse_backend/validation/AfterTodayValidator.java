package at.barniverse.backend.barniverse_backend.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

// validates if a date is in the future
public class AfterTodayValidator implements ConstraintValidator<AfterToday, Date> {

    @Override
    public void initialize(AfterToday constraintAnnotation) { }

    @Override
    public boolean isValid(Date dateField, ConstraintValidatorContext context) {
        if (dateField == null) { return false; }

        Date today = new Date();

        return dateField.after(today);
    }
}
