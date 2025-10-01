package stefanini_cpg_api.api_autores_obras.domain.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stefanini_cpg_api.api_autores_obras.application.web.dto.request.AutorRequestDTO;
import stefanini_cpg_api.api_autores_obras.application.web.dto.response.ArtworkResponseDTO;
import stefanini_cpg_api.api_autores_obras.application.web.dto.response.AutorResponseDTO;
import stefanini_cpg_api.api_autores_obras.domain.entities.Autor;
import stefanini_cpg_api.api_autores_obras.domain.exceptions.AutorHasArtworksException;
import stefanini_cpg_api.api_autores_obras.domain.exceptions.AutorNotFoundException;
import stefanini_cpg_api.api_autores_obras.domain.exceptions.CpfAlreadyExistsException;
import stefanini_cpg_api.api_autores_obras.domain.exceptions.EmailAlreadyExistsException;
import stefanini_cpg_api.api_autores_obras.domain.services.AutorService;
import stefanini_cpg_api.api_autores_obras.domain.services.helpers.CpfUtil;
import stefanini_cpg_api.api_autores_obras.resource.repository.ArtworkRepository;
import stefanini_cpg_api.api_autores_obras.resource.repository.AutorRepository;

@Service
@Transactional
public class AutorServiceImpl implements AutorService {

    private final AutorRepository autorRepository;
    private final ArtworkRepository artworkRepository;

    public AutorServiceImpl(AutorRepository autorRepository, ArtworkRepository artworkRepository){
        this.autorRepository = autorRepository;
        this.artworkRepository = artworkRepository;
    }

    @Override
    public Autor create(AutorRequestDTO autorRequestDTO) {
        if (autorRequestDTO.email() != null && !autorRequestDTO.email().trim().isEmpty()) {
            validateEmailUniqueness(autorRequestDTO.email(), null);
        }

        if (autorRequestDTO.cpf() != null && !autorRequestDTO.cpf().trim().isEmpty()) {
            validateCpfUniqueness(autorRequestDTO.cpf(), null);
        }
        
        var autor = new Autor(autorRequestDTO);
        
        if (autor.getCpf() != null && !autor.getCpf().trim().isEmpty()) {
            String cpfNormalized = CpfUtil.normalize(autor.getCpf());
            autor.setCpf(cpfNormalized);
        } else {
            autor.setCpf(null);
        }
        
        return autorRepository.save(autor);
    }

    @Override
    public Autor update(Long id, AutorRequestDTO autorRequestDTO) {
        var autor = autorRepository.findById(id)
                .orElseThrow(() -> new AutorNotFoundException(id));
        
        if (autorRequestDTO.email() != null && !autorRequestDTO.email().trim().isEmpty()) {
            validateEmailUniqueness(autorRequestDTO.email(), id);
        }
        
        if (autorRequestDTO.cpf() != null && !autorRequestDTO.cpf().trim().isEmpty()) {
            validateCpfUniqueness(autorRequestDTO.cpf(), id);
        }
        
        autor.setName(autorRequestDTO.name());
        autor.setGender(autorRequestDTO.gender());
        autor.setEmail(autorRequestDTO.email());
        autor.setDateOfBirth(autorRequestDTO.dateOfBirth());
        autor.setCountry(autorRequestDTO.country());
        
        if (autorRequestDTO.cpf() != null && !autorRequestDTO.cpf().trim().isEmpty()) {
            String cpfNormalized = CpfUtil.normalize(autorRequestDTO.cpf());
            autor.setCpf(cpfNormalized);
        } else {
            autor.setCpf(null);
        }
        
        return autorRepository.save(autor);
    }

    @Override
    @Transactional(readOnly = true)
    public Autor findById(Long id) {
        return autorRepository.findById(id)
                .orElseThrow(() -> new AutorNotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AutorResponseDTO> getAllAtores(Pageable pagination) {
        Pageable pageable = PageRequest.of(pagination.getPageNumber(), pagination.getPageSize(),
                Sort.by("name"));

        return autorRepository.findAll(pageable).map(AutorResponseDTO::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtworkResponseDTO> getAllArtworks(Long id, Pageable pagination) {
        
        findById(id);
        
        Pageable pageable = PageRequest.of(pagination.getPageNumber(), pagination.getPageSize(),
                Sort.by("name"));

        return artworkRepository.findByAutores_Id(id, pageable)
                .map(ArtworkResponseDTO::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtworkResponseDTO> getAllArtworksByAutorName(Pageable pagination, String autorName) {
        Pageable pageable = PageRequest.of(pagination.getPageNumber(), pagination.getPageSize(),
                Sort.by("name"));

         return artworkRepository.findByAutores_NameContaining(pageable, autorName)
                 .map(ArtworkResponseDTO::new);
    }

    @Override
    public void delete(Long id) {
        var autor = autorRepository.findById(id)
                .orElseThrow(() -> new AutorNotFoundException(id));
                
        if (hasArtworks(autor)) {
            throw new AutorHasArtworksException(id);
        }
        
        autorRepository.delete(autor);
    }

    private void validateEmailUniqueness(String email, Long excludeId) {
        boolean emailExists;
        if (excludeId != null) {
            emailExists = autorRepository.existsByEmailAndIdNot(email, excludeId);
        } else {
            emailExists = autorRepository.existsByEmail(email);
        }
        
        if (emailExists) {
            throw new EmailAlreadyExistsException(email, excludeId);
        }
    }

    private void validateCpfUniqueness(String cpf, Long excludeId) {
        String normalizedCpf = CpfUtil.normalize(cpf);
        boolean cpfExists;
        
        if (excludeId != null) {
            cpfExists = autorRepository.existsByCpfAndIdNot(normalizedCpf, excludeId);
        } else {
            cpfExists = autorRepository.existsByCpfAndIdNot(normalizedCpf, -1L);
        }
        
        if (cpfExists) {
            throw new CpfAlreadyExistsException(cpf, excludeId);
        }
    }

    private boolean hasArtworks(Autor autor) {
        return autor.getArtwork() != null && !autor.getArtwork().isEmpty();
    }
}
