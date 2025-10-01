package stefanini_cpg_api.api_autores_obras.domain.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import stefanini_cpg_api.api_autores_obras.application.web.dto.request.ArtworkRequestDTO;
import stefanini_cpg_api.api_autores_obras.application.web.dto.response.ArtworkResponseDTO;
import stefanini_cpg_api.api_autores_obras.application.web.dto.response.AutorResponseDTO;
import stefanini_cpg_api.api_autores_obras.domain.entities.Artwork;

public interface ArtworkService {
    Artwork create(ArtworkRequestDTO artworkRequestDTO);
    Artwork update(Long id, ArtworkRequestDTO artworkRequestDTO);
    Artwork findById(Long id);
    Page<ArtworkResponseDTO> getAllArtworks(Pageable pageable);
    Page<ArtworkResponseDTO> getAllArtworksFiltered(Pageable pageable, String name, String description);
    Page<AutorResponseDTO> getAllAutoresOfArtwork(Pageable pageable, Long id);
    void delete(Long id);
}
