package at.barniverse.backend.barniverse_backend.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = AfterTodayValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AfterToday {

    String message();

    // spring needs this values
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
