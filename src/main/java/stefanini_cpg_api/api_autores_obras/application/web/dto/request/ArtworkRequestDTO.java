package stefanini_cpg_api.api_autores_obras.application.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import stefanini_cpg_api.api_autores_obras.domain.annotations.PublicationOrExposureDateRequired;

import java.time.LocalDate;
import java.util.Set;

@PublicationOrExposureDateRequired
public record ArtworkRequestDTO(

        @NotBlank
        String name,

        @Size(max = 240, message = "Description must contain at most 240 characters")
        @NotBlank
        String description,

        LocalDate publicationDate,

        LocalDate exposureDate,

        @NotNull(message = "At least one author ID must be provided")
        Set<Long> authorIds
) {
}
