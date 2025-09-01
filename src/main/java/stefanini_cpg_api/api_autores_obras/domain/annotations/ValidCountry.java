package stefanini_cpg_api.api_autores_obras.domain.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CountryValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCountry {

    String message() default "Invalid Country";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
