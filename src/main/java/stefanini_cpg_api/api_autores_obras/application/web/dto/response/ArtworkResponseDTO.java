package stefanini_cpg_api.api_autores_obras.application.web.dto.response;

import stefanini_cpg_api.api_autores_obras.domain.entities.Artwork;
import stefanini_cpg_api.api_autores_obras.domain.entities.Autor;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public record ArtworkResponseDTO(
        Long id,
        String name,
        String description,
        LocalDate publicationDate,
        LocalDate exposureDate,
        Set<String> nameOfAutores
) {
    public ArtworkResponseDTO(Artwork artwork){
        this(artwork.getId(), artwork.getName(), artwork.getDescription(), artwork.getPublicationDate(), artwork.getExposureDate(),
                artwork.getAutores()
                        .stream()
                        .map(Autor::getName)
                        .collect(Collectors.toSet()));
    }
}
