package stefanini_cpg_api.api_autores_obras.domain.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import stefanini_cpg_api.api_autores_obras.application.web.dto.request.ArtworkRequestDTO;
import stefanini_cpg_api.api_autores_obras.application.web.dto.response.ArtworkAutorResponseDTO;
import stefanini_cpg_api.api_autores_obras.application.web.dto.response.ArtworkResponseDTO;
import stefanini_cpg_api.api_autores_obras.application.web.dto.response.AutorResponseDTO;
import stefanini_cpg_api.api_autores_obras.domain.entities.Artwork;
import stefanini_cpg_api.api_autores_obras.domain.services.ArtworkService;
import stefanini_cpg_api.api_autores_obras.resource.repository.ArtworkRepository;
import stefanini_cpg_api.api_autores_obras.resource.repository.AutorRepository;

@Service
public class ArtworkServiceImpl implements ArtworkService {
    private final ArtworkRepository artworkRepository;
    private final AutorRepository autorRepository;

    public ArtworkServiceImpl(ArtworkRepository artworkRepository, AutorRepository autorRepository){
        this.artworkRepository = artworkRepository;
        this.autorRepository = autorRepository;
    }

    @Override
    public Artwork create(ArtworkRequestDTO artworkRequestDTO) {
        var artwork = new Artwork(artworkRequestDTO);
        this.artworkRepository.save(artwork);
        return artwork;
    }

    @Override
    public Page<ArtworkResponseDTO> getAllArtworks(Pageable pagination) {
        Pageable pageable = PageRequest.of(pagination.getPageNumber(), pagination.getPageSize(),
                Sort.by("name"));
        var page = artworkRepository.findAll(pageable).map(ArtworkResponseDTO::new);
        return page;
    }

    @Override
    public Page<AutorResponseDTO> getAllAutoresOfArtwork(Pageable pagination, Long id) {
        Pageable pageable = PageRequest.of(
                pagination.getPageNumber(), pagination.getPageSize(),
                Sort.by("name")
        );
        return autorRepository.findByArtwork_Id(pageable, id)
                .map(AutorResponseDTO::new);
    }

    @Override
    public void delete(Long id) {
        var art = this.artworkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artwork not found"));

        this.artworkRepository.delete(art);
    }
}
