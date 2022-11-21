package at.barniverse.backend.barniverse_backend.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * annotation if one date is after another date in the calendar
 */
@Constraint(validatedBy = AfterSpecificDateValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(AfterSpecificDate.List.class)
public @interface AfterSpecificDate {

    String startDate();
    String endDate();
    String message();

    // spring needs this values
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    // necessary to use annotation multiple times (repeatable annotation)
    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        AfterSpecificDate[] value();
    }


}
