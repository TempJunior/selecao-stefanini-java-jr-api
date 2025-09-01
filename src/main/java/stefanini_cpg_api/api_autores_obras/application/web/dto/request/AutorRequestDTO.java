package stefanini_cpg_api.api_autores_obras.application.web.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;
import stefanini_cpg_api.api_autores_obras.domain.anottations.RequireCpfIfBrazil;
import stefanini_cpg_api.api_autores_obras.domain.anottations.ValidCountry;
import stefanini_cpg_api.api_autores_obras.domain.entities.Artwork;
import stefanini_cpg_api.api_autores_obras.domain.entities.Autor;
import stefanini_cpg_api.api_autores_obras.domain.entities.Gender;

import java.time.LocalDate;
import java.util.Set;

@RequireCpfIfBrazil(countryField = "country", cpfField = "cpf")
public record AutorRequestDTO(

        @NotNull
        String name,

        Gender gender,

        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$\n",
                message = "Email format invalid")
        String email,

        @NotNull
        @Past(message = "Date of birth must be in the past")
        LocalDate dateOfBirth,

        @NotBlank
        @ValidCountry
        String country,

        @CPF(message = "Invalid CPF")
        @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "Use o formato 000.000.000-00")
        @Column(name = "cpf", length = 11, unique = true)
        String cpf,

        Set<Artwork> artwork
) {
    public AutorRequestDTO(Autor autor) {
        this(autor.getName(), autor.getGender(), autor.getEmail(), autor.getDateOfBirth(), autor.getCountry(), autor.getCpf(), autor.getArtwork());
    }
}
