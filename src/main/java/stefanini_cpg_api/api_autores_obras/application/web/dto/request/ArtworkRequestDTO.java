package stefanini_cpg_api.api_autores_obras.application.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import stefanini_cpg_api.api_autores_obras.domain.anottations.PublicationOrExposureDateRequired;
import stefanini_cpg_api.api_autores_obras.domain.entities.Autor;

import java.time.LocalDate;
import java.util.Set;

@PublicationOrExposureDateRequired
public record ArtworkRequestDTO(

        @NotBlank
        String name,

        @Size(max = 250, message = "Cannot contain more than 250 characters")
                @NotBlank
        String description,

        LocalDate publicationDate,

        LocalDate exposureDate,

        Set<Autor> autores
) {
}
