package at.barniverse.backend.barniverse_backend.validation;

import org.springframework.beans.BeanWrapperImpl;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * validator for LowerOrEqualThanOther annotation
 */
public class LowerOrEqualThanOtherValidator implements ConstraintValidator<LowerOrEqualThanOther, Object> {

    private String lowerField;
    private String higherField;

    /**
     * initialize properties
     * @param constraintAnnotation LowerOrEqualThanOther annotation
     */
    @Override
    public void initialize(LowerOrEqualThanOther constraintAnnotation) {
        this.lowerField = constraintAnnotation.lowerField();
        this.higherField = constraintAnnotation.higherField();
    }

    /**
     * check if annotation is valid or not
     * @param value object to read properties and get data
     * @param context ConstraintValidatorContext
     * @return true if first value is lower or equal than second value, otherwise false
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        Object lowerObject = new BeanWrapperImpl(value)
                .getPropertyValue(lowerField);
        Object higherObject = new BeanWrapperImpl(value)
                .getPropertyValue(higherField);
        double lowerValue = (double) lowerObject;
        double higherValue = (double) higherObject;

        return lowerValue <= higherValue;
    }
}
