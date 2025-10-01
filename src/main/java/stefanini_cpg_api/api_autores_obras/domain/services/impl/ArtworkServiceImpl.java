package stefanini_cpg_api.api_autores_obras.domain.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stefanini_cpg_api.api_autores_obras.application.web.dto.request.ArtworkRequestDTO;
import stefanini_cpg_api.api_autores_obras.application.web.dto.response.ArtworkResponseDTO;
import stefanini_cpg_api.api_autores_obras.application.web.dto.response.AutorResponseDTO;
import stefanini_cpg_api.api_autores_obras.domain.entities.Artwork;
import stefanini_cpg_api.api_autores_obras.domain.entities.Autor;
import stefanini_cpg_api.api_autores_obras.domain.exceptions.ArtworkMustHaveAuthorsException;
import stefanini_cpg_api.api_autores_obras.domain.exceptions.ArtworkNotFoundException;
import stefanini_cpg_api.api_autores_obras.domain.services.ArtworkService;
import stefanini_cpg_api.api_autores_obras.resource.repository.ArtworkRepository;
import stefanini_cpg_api.api_autores_obras.resource.repository.AutorRepository;

import java.util.Set;

@Service
@Transactional
public class ArtworkServiceImpl implements ArtworkService {
    private final ArtworkRepository artworkRepository;
    private final AutorRepository autorRepository;

    public ArtworkServiceImpl(ArtworkRepository artworkRepository, AutorRepository autorRepository){
        this.artworkRepository = artworkRepository;
        this.autorRepository = autorRepository;
    }

    @Override
    public Artwork create(ArtworkRequestDTO artworkRequestDTO) {
        validateArtworkHasAuthors(artworkRequestDTO.authorIds());
        
        Set<Autor> autores = findAutoresByIds(artworkRequestDTO.authorIds());
        
        var artwork = new Artwork();
        artwork.setName(artworkRequestDTO.name());
        artwork.setDescription(artworkRequestDTO.description());
        artwork.setPublicationDate(artworkRequestDTO.publicationDate());
        artwork.setExposureDate(artworkRequestDTO.exposureDate());
        artwork.setAutores(autores);
        
        Artwork savedArtwork = this.artworkRepository.save(artwork);
        
        return savedArtwork;
    }


    @Override
    public Artwork update(Long id, ArtworkRequestDTO artworkRequestDTO) {
        var artwork = artworkRepository.findById(id)
                .orElseThrow(() -> new ArtworkNotFoundException(id));
        
        // Validar se a obra tem pelo menos um autor
        validateArtworkHasAuthors(artworkRequestDTO.authorIds());
        
        // Buscar os autores pelos IDs
        Set<Autor> autores = findAutoresByIds(artworkRequestDTO.authorIds());
        
        // Atualizar campos
        artwork.updateArtwork(artworkRequestDTO, autores);
        
        // Salvar a obra
        Artwork savedArtwork = artworkRepository.save(artwork);
        
        return savedArtwork;
    }

    @Override
    @Transactional(readOnly = true)
    public Artwork findById(Long id) {
        return artworkRepository.findById(id)
                .orElseThrow(() -> new ArtworkNotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtworkResponseDTO> getAllArtworks(Pageable pagination) {
        Pageable pageable = PageRequest.of(pagination.getPageNumber(), pagination.getPageSize(),
                Sort.by("name"));
        return artworkRepository.findAllWithAutores(pageable).map(ArtworkResponseDTO::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtworkResponseDTO> getAllArtworksFiltered(Pageable pagination, String name, String description) {
        Pageable pageable = PageRequest.of(pagination.getPageNumber(), pagination.getPageSize(),
                Sort.by("name"));
        
        // Aplicar filtros conforme Extra 4 - Filtragem e Paginação
        if (name != null && !name.trim().isEmpty() && description != null && !description.trim().isEmpty()) {
            // Filtrar por nome E descrição
            return artworkRepository.findByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCaseWithAutores(
                    name.trim(), description.trim(), pageable).map(ArtworkResponseDTO::new);
        } else if (name != null && !name.trim().isEmpty()) {
            // Filtrar apenas por nome
            return artworkRepository.findByNameContainingIgnoreCaseWithAutores(name.trim(), pageable)
                    .map(ArtworkResponseDTO::new);
        } else if (description != null && !description.trim().isEmpty()) {
            // Filtrar apenas por descrição
            return artworkRepository.findByDescriptionContainingIgnoreCaseWithAutores(description.trim(), pageable)
                    .map(ArtworkResponseDTO::new);
        } else {
            // Sem filtros - retornar todas
            return getAllArtworks(pagination);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AutorResponseDTO> getAllAutoresOfArtwork(Pageable pagination, Long id) {
        findById(id);
        
        Pageable pageable = PageRequest.of(
                pagination.getPageNumber(), pagination.getPageSize(),
                Sort.by("name")
        );
        return autorRepository.findByArtwork_Id(pageable, id)
                .map(AutorResponseDTO::new);
    }

    @Override
    public void delete(Long id) {
        var artwork = artworkRepository.findById(id)
                .orElseThrow(() -> new ArtworkNotFoundException(id));

        artworkRepository.delete(artwork);
    }

    private void validateArtworkHasAuthors(java.util.Set<Long> authorIds) {
        if (authorIds == null || authorIds.isEmpty()) {
            throw new ArtworkMustHaveAuthorsException();
        }
    }
    
    private Set<Autor> findAutoresByIds(Set<Long> authorIds) {
        Set<Autor> autores = new java.util.HashSet<>();
        
        for (Long authorId : authorIds) {
            Autor autor = autorRepository.findById(authorId)
                    .orElseThrow(() -> new stefanini_cpg_api.api_autores_obras.domain.exceptions.AutorNotFoundException(authorId));
            autores.add(autor);
        }
        
        return autores;
    }
    
}
