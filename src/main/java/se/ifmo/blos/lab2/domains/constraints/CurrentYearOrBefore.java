package se.ifmo.blos.lab2.domains.constraints;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import se.ifmo.blos.lab2.domains.constraints.validators.CurrentYearOrBeforeValidator;

@Retention(RUNTIME)
@Target({METHOD, FIELD, CONSTRUCTOR, PARAMETER})
@Documented
@Constraint(validatedBy = CurrentYearOrBeforeValidator.class)
public @interface CurrentYearOrBefore {

  String message() default "Must be the current year or past.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
