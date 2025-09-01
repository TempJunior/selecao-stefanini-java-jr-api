package stefanini_cpg_api.api_autores_obras.domain.anottations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PublicationOrExposureDateValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PublicationOrExposureDateRequired {
    String message() default "Deve informar a data de publicação OU a data de exposição";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
