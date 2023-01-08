package at.barniverse.backend.barniverse_backend.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * validator for AfterSpecificDate annotation
 */
public class AfterSpecificDateValidator implements ConstraintValidator<AfterSpecificDate, Object> {

    private String startDate;
    private String endDate;

    /**
     * initializes properties
     * @param constraintAnnotation AfterSpecificDate annotation
     */
    @Override
    public void initialize(AfterSpecificDate constraintAnnotation) {
        this.startDate = constraintAnnotation.startDate();
        this.endDate = constraintAnnotation.endDate();
    }

    /**
     * check if annotation is valid or not
     * @param value object to read properties and get data
     * @param context ConstraintValidatorContext
     * @return true if first date is after second date in the calendar, otherwise false
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        Object startDateObject = new BeanWrapperImpl(value)
                .getPropertyValue(startDate);
        Object endDateObject = new BeanWrapperImpl(value)
                .getPropertyValue(endDate);
        LocalDateTime startDateValue = (LocalDateTime) startDateObject;
        LocalDateTime endDateValue = (LocalDateTime) endDateObject;

        if (startDateValue == null || endDateValue == null) { return false; }

        return endDateValue.compareTo(startDateValue) > 0; // after specific date
    }

}
