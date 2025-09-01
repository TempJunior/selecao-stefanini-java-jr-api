package stefanini_cpg_api.api_autores_obras.domain.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.util.Objects;

public class RequireCpfIfBrazilValidator implements ConstraintValidator<RequireCpfIfBrazil, Object> {
    private String countryField;
    private String cpfField;

    @Override
    public void initialize(RequireCpfIfBrazil ann) {
        this.countryField = ann.countryField();
        this.cpfField = ann.cpfField();
    }
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext ctx) {
        try {
            Field fCountry = value.getClass().getDeclaredField(countryField);
            Field fCpf = value.getClass().getDeclaredField(cpfField);
            fCountry.setAccessible(true);
            fCpf.setAccessible(true);

            Object countryVal = fCountry.get(value);
            Object cpfVal = fCpf.get(value);

            boolean isBrazil =
                    Objects.equals(countryVal, "BR") ||
                            Objects.equals(String.valueOf(countryVal), "BR") ||
                            (countryVal != null && countryVal.toString().endsWith("BR")); // cobre enum Country.BR

            String cpf = cpfVal == null ? null : cpfVal.toString().trim();

            if (isBrazil) {
                // obrigatório e não vazio
                boolean ok = cpf != null && !cpf.isBlank();
                if (!ok) {
                    ctx.disableDefaultConstraintViolation();
                    ctx.buildConstraintViolationWithTemplate(
                            "CPF é obrigatório para país BR"
                    ).addPropertyNode(cpfField).addConstraintViolation();
                }
                return ok; // @CPF fará a validação de dígitos
            } else {
                // não-BR: opcional; se vier preenchido, deixa @CPF validar
                return true;
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // se algo deu errado, falhe seguro
            return false;
        }
    }
}
