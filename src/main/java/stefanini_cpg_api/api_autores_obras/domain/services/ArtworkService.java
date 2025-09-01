package stefanini_cpg_api.api_autores_obras.domain.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import stefanini_cpg_api.api_autores_obras.application.web.dto.request.ArtworkRequestDTO;
import stefanini_cpg_api.api_autores_obras.application.web.dto.response.ArtworkAutorResponseDTO;
import stefanini_cpg_api.api_autores_obras.application.web.dto.response.ArtworkResponseDTO;

public interface ArtworkService {
    ArtworkResponseDTO create(ArtworkRequestDTO artworkRequestDTO);
    Page<ArtworkResponseDTO> getAllArtworks(Pageable pageable);
    Page<ArtworkAutorResponseDTO> getAllArtworksOfAutor(Pageable pageable, String autor);
    void delete(Long id);

}
