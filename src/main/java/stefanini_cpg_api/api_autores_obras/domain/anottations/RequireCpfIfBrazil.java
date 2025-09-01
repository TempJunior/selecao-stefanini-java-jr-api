package stefanini_cpg_api.api_autores_obras.domain.anottations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RequireCpfIfBrazilValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireCpfIfBrazil {

    String message() default "CPF é obrigatório quando o país de origem é Brasil";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    // nomes dos campos
    String countryField();
    String cpfField();
}
