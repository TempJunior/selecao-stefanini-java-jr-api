package stefanini_cpg_api.api_autores_obras.domain.anottations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Locale;
import java.util.Set;

public class CountryValidator implements ConstraintValidator<ValidCountry, String> {
    private static final Set<String> ISO_COUNTRIES = Set.of(Locale.getISOCountries());

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) return true;
        return ISO_COUNTRIES.contains(s.toUpperCase());
    }
}
