package stefanini_cpg_api.api_autores_obras.domain.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import stefanini_cpg_api.api_autores_obras.application.web.dto.request.AutorRequestDTO;
import stefanini_cpg_api.api_autores_obras.application.web.dto.response.AutorArtworkResponseDTO;
import stefanini_cpg_api.api_autores_obras.application.web.dto.response.AutorResponseDTO;
import stefanini_cpg_api.api_autores_obras.domain.entities.Autor;
import stefanini_cpg_api.api_autores_obras.domain.services.AutorService;
import stefanini_cpg_api.api_autores_obras.domain.services.helpers.CpfUtil;
import stefanini_cpg_api.api_autores_obras.resource.repository.AutorRepository;

import java.util.stream.Collectors;

@Service
public class AutorServiceImpl implements AutorService {

    private final AutorRepository autorRepository;

    public AutorServiceImpl(AutorRepository autorRepository){
        this.autorRepository = autorRepository;
    }

    @Override
    public AutorResponseDTO create(AutorRequestDTO autorRequestDTO) {
        var autor = new Autor(autorRequestDTO);
        cpfValidate(autor.getCpf(), autor.getId());
        String cpfNormalized = CpfUtil.normalize(autor.getCpf());
        autor.setCpf(cpfNormalized);
        autorRepository.save(autor);

        return new AutorResponseDTO(autor);
    }

    @Override
    public Page<AutorResponseDTO> getAllAtores(Pageable pagination) {
        Pageable pageable = PageRequest.of(pagination.getPageNumber(), pagination.getPageSize(),
                Sort.by("name"));

        var page = autorRepository.findAll(pageable).map(AutorResponseDTO::new);
        return page;
    }

    @Override
    public Page<AutorArtworkResponseDTO> getAllArtworks(Pageable pagination) {
        Pageable pageable = PageRequest.of(pagination.getPageNumber(), pagination.getPageSize(),
                Sort.by("id"));

        var page = autorRepository.findPageWithArtworks(pageable)
                .map(AutorArtworkResponseDTO::new);

        return page;
    }

    @Override
    public Page<AutorArtworkResponseDTO> getAllArtworksByAutorName(Pageable pagination, String autorName) {
        Pageable pageable = PageRequest.of(pagination.getPageNumber(), pagination.getPageSize(),
                Sort.by("name"));
        var page = autorRepository.findByName(pageable, autorName);

        return page;
    }

    @Override
    public void delete(Long id) {
        var autor = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor not found by ID"));
        if (autorRepository.existsByArtwork_id(id)){
            throw new RuntimeException("Cannot delete because author is associated with one or more works");
        }
        autorRepository.delete(autor);
    }

    public boolean cpfValidate(String cpf, Long id){
        boolean cpfAlreadyRegistered = this.autorRepository.existsByCpfAndIdNot(
                CpfUtil.normalize(cpf), id);
        if (cpfAlreadyRegistered){
            throw new RuntimeException("Cpf Already used");
        }
        return false;
    }
}
