package stefanini_cpg_api.api_autores_obras.domain.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import stefanini_cpg_api.api_autores_obras.application.web.dto.request.ArtworkRequestDTO;
import stefanini_cpg_api.api_autores_obras.application.web.dto.response.ArtworkAutorResponseDTO;
import stefanini_cpg_api.api_autores_obras.application.web.dto.response.ArtworkResponseDTO;
import stefanini_cpg_api.api_autores_obras.domain.entities.Artwork;
import stefanini_cpg_api.api_autores_obras.domain.services.ArtworkService;
import stefanini_cpg_api.api_autores_obras.resource.repository.ArtworkRepository;

@Service
public class ArtworkServiceImpl implements ArtworkService {
    private final ArtworkRepository artworkRepository;

    public ArtworkServiceImpl(ArtworkRepository artworkRepository){
        this.artworkRepository = artworkRepository;
    }

    @Override
    public ArtworkResponseDTO create(ArtworkRequestDTO artworkRequestDTO) {
        var artwork = new Artwork(artworkRequestDTO);
        this.artworkRepository.save(artwork);
        return new ArtworkResponseDTO(artwork);
    }

    @Override
    public Page<ArtworkResponseDTO> getAllArtworks(Pageable pagination) {
        Pageable pageable = PageRequest.of(pagination.getPageNumber(), pagination.getPageSize(),
                Sort.by("name"));
        var page = artworkRepository.findAll(pageable).map(ArtworkResponseDTO::new);
        return page;
    }

    @Override
    public Page<ArtworkAutorResponseDTO> getAllArtworksOfAutor(Pageable pagination, String autor) {
        Pageable pageable = PageRequest.of(
                pagination.getPageNumber(), pagination.getPageSize(),
                Sort.by("name")
        );
        return artworkRepository.findByNameWithArtworks(autor, pageable)
                .map(ArtworkAutorResponseDTO::new);
    }

    @Override
    public void delete(Long id) {
        var art = this.artworkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artwork not found"));

        this.artworkRepository.delete(art);
    }
}
