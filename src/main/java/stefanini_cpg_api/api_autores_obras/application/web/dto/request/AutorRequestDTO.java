package stefanini_cpg_api.api_autores_obras.application.web.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;
import stefanini_cpg_api.api_autores_obras.domain.annotations.RequireCpfIfBrazil;
import stefanini_cpg_api.api_autores_obras.domain.annotations.ValidCountry;
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

        @Pattern(regexp = "^([0-9a-zA-Z]+([_.-]?[0-9a-zA-Z]+)*@[0-9a-zA-Z]+[0-9,a-z,A-Z,.,-]*(.){1}[a-zA-Z]{2,4})+$",
                message = "Email format invalid")
        String email,

        @NotNull
        @Past(message = "Date of birth must be in the past")
        LocalDate dateOfBirth,

        @NotBlank
        @ValidCountry
        String country,

        @Column(name = "cpf", length = 11, unique = true)
        String cpf,

        Set<Artwork> artwork
) {
    public AutorRequestDTO(Autor autor) {
        this(autor.getName(), autor.getGender(), autor.getEmail(), autor.getDateOfBirth(), autor.getCountry(), autor.getCpf(), autor.getArtwork());
    }
}
