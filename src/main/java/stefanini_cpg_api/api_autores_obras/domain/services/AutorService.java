package stefanini_cpg_api.api_autores_obras.domain.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import stefanini_cpg_api.api_autores_obras.application.web.dto.request.AutorRequestDTO;
import stefanini_cpg_api.api_autores_obras.application.web.dto.response.AutorArtworkResponseDTO;
import stefanini_cpg_api.api_autores_obras.application.web.dto.response.AutorResponseDTO;
import stefanini_cpg_api.api_autores_obras.domain.entities.Autor;

public interface AutorService {
    AutorResponseDTO create(AutorRequestDTO autorRequestDTO);
    Page<AutorResponseDTO> getAllAtores(Pageable pageable);
    Page<AutorArtworkResponseDTO> getAllArtworks(Pageable pageable);
    Page<AutorArtworkResponseDTO> getAllArtworksByAutorName(Pageable pageable, String autorName);
    void delete(Long id);
}
