package at.barniverse.backend.barniverse_backend.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

// annotation if a value is lower than another value
@Constraint(validatedBy = LowerThanOtherValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(LowerThanOther.List.class)
public @interface LowerThanOther {

    String lowerField();
    String higherField();
    String message();

    // spring needs this values
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    // necessary to use annotation multiple times (repeatable annotation)
    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        LowerThanOther[] value();
    }


}
