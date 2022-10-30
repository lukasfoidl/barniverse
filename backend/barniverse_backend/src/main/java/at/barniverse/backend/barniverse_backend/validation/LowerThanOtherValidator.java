package at.barniverse.backend.barniverse_backend.validation;

import org.springframework.beans.BeanWrapperImpl;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

// validates if a value is lower than another value
public class LowerThanOtherValidator implements ConstraintValidator<LowerThanOther, Object> {

    private String lowerField;
    private String higherField;

    @Override
    public void initialize(LowerThanOther constraintAnnotation) {
        this.lowerField = constraintAnnotation.lowerField();
        this.higherField = constraintAnnotation.higherField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        Object lowerObject = new BeanWrapperImpl(value)
                .getPropertyValue(lowerField);
        Object higherObject = new BeanWrapperImpl(value)
                .getPropertyValue(higherField);
        double lowerValue = (double) lowerObject;
        double higherValue = (double) higherObject;

        return lowerValue < higherValue;
    }
}
