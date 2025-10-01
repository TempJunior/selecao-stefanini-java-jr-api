package stefanini_cpg_api.api_autores_obras.domain.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Locale;
import java.util.Set;

public class CountryValidator implements ConstraintValidator<ValidCountry, String> {
    private static final Set<String> ISO_COUNTRIES = Set.of(Locale.getISOCountries());
    
    private static final java.util.Map<String, String> COUNTRY_MAPPING = createCountryMapping();
    
    private static java.util.Map<String, String> createCountryMapping() {
        java.util.Map<String, String> mapping = new java.util.HashMap<>();
        mapping.put("Brasil", "BR");
        mapping.put("Brazil", "BR");
        mapping.put("Estados Unidos", "US");
        mapping.put("United States", "US");
        mapping.put("França", "FR");
        mapping.put("France", "FR");
        mapping.put("Alemanha", "DE");
        mapping.put("Germany", "DE");
        mapping.put("Itália", "IT");
        mapping.put("Italy", "IT");
        mapping.put("Espanha", "ES");
        mapping.put("Spain", "ES");
        mapping.put("Reino Unido", "GB");
        mapping.put("United Kingdom", "GB");
        mapping.put("Canadá", "CA");
        mapping.put("Canada", "CA");
        mapping.put("Austrália", "AU");
        mapping.put("Australia", "AU");
        mapping.put("Japão", "JP");
        mapping.put("Japan", "JP");
        mapping.put("China", "CN");
        mapping.put("Índia", "IN");
        mapping.put("India", "IN");
        mapping.put("México", "MX");
        mapping.put("Mexico", "MX");
        mapping.put("Argentina", "AR");
        mapping.put("Chile", "CL");
        mapping.put("Colômbia", "CO");
        mapping.put("Colombia", "CO");
        mapping.put("Peru", "PE");
        mapping.put("Venezuela", "VE");
        mapping.put("Uruguai", "UY");
        mapping.put("Uruguay", "UY");
        mapping.put("Paraguai", "PY");
        mapping.put("Paraguay", "PY");
        mapping.put("Bolívia", "BO");
        mapping.put("Bolivia", "BO");
        mapping.put("Equador", "EC");
        mapping.put("Ecuador", "EC");
        return java.util.Collections.unmodifiableMap(mapping);
    }

    @Override
    public boolean isValid(String country, ConstraintValidatorContext context) {
        if (country == null || country.trim().isEmpty()) {
            return true; // Deixar @NotBlank lidar com isso
        }
        
        String normalizedCountry = country.trim();
        
        // Verificar se é um código ISO válido
        if (ISO_COUNTRIES.contains(normalizedCountry.toUpperCase())) {
            return true;
        }
        
        // Verificar se é um nome de país conhecido
        String isoCode = COUNTRY_MAPPING.get(normalizedCountry);
        if (isoCode != null && ISO_COUNTRIES.contains(isoCode)) {
            return true;
        }
        
        return false;
    }
}
