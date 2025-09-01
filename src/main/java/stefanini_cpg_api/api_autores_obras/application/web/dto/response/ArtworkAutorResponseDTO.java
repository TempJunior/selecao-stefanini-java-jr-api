package stefanini_cpg_api.api_autores_obras.application.web.dto.response;

import stefanini_cpg_api.api_autores_obras.domain.entities.Artwork;

import java.util.Set;
import java.util.stream.Collectors;

public record ArtworkAutorResponseDTO(
        String name,
        String description,
        Set<AutorResponseDTO> autores
) {
    public ArtworkAutorResponseDTO(Artwork artwork) {
        this(artwork.getName(),
                artwork.getDescription(),
                artwork.getAutor()
                        .stream()
                        .map(AutorResponseDTO::new)
                        .collect(Collectors.toSet())
                );
    }
}
