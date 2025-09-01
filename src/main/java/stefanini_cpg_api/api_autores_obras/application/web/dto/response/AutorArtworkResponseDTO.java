package stefanini_cpg_api.api_autores_obras.application.web.dto.response;

import stefanini_cpg_api.api_autores_obras.domain.entities.Autor;

import java.util.Set;
import java.util.stream.Collectors;

public record AutorArtworkResponseDTO(
        String name,
        String country,
        Set<ArtworkResponseDTO> artwork
) {
    public AutorArtworkResponseDTO(Autor autor){
        this(autor.getName(),
                autor.getCountry(),
                autor.getArtwork()
                        .stream()
                        .map(ArtworkResponseDTO::new)
                        .collect(Collectors.toSet())
        );
    }
}
