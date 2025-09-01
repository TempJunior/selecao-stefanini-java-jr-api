package stefanini_cpg_api.api_autores_obras.application.web.dto.response;

import stefanini_cpg_api.api_autores_obras.domain.entities.Artwork;

import java.time.LocalDate;

public record ArtworkResponseDTO(
        Long id,
        String name,
        String description,
        LocalDate publicationDate,
        LocalDate exposureDate
) {
    public ArtworkResponseDTO(Artwork artwork){
        this(artwork.getId(), artwork.getName(), artwork.getDescription(), artwork.getPublicationDate(), artwork.getExposureDate());
    }
}
