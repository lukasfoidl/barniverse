package at.barniverse.backend.barniverse_backend.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

// validates if one date is after another date in the calendar
public class AfterSpecificDateValidator implements ConstraintValidator<AfterSpecificDate, Object> {

    private String startDate;
    private String endDate;

    @Override
    public void initialize(AfterSpecificDate constraintAnnotation) {
        this.startDate = constraintAnnotation.startDate();
        this.endDate = constraintAnnotation.endDate();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        Object startDateObject = new BeanWrapperImpl(value)
                .getPropertyValue(startDate);
        Object endDateObject = new BeanWrapperImpl(value)
                .getPropertyValue(endDate);
        Date startDateValue = (Date) startDateObject;
        Date endDateValue = (Date) endDateObject;

        if (startDateValue == null || endDateValue == null) { return false; }

        return endDateValue.after(startDateValue);
    }

}
