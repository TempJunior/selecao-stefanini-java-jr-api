package stefanini_cpg_api.api_autores_obras.resource.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stefanini_cpg_api.api_autores_obras.application.web.dto.response.ArtworkResponseDTO;
import stefanini_cpg_api.api_autores_obras.application.web.dto.response.AutorArtworkResponseDTO;
import stefanini_cpg_api.api_autores_obras.domain.entities.Autor;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    boolean existsByCpfAndIdNot(String cpf, Long id);

    Page<ArtworkResponseDTO> findByName(Pageable pageable, String autor);

    boolean existsByArtwork_id(Long id);
    Page<Autor> findByAutor_Id(Pageable pageable, Long id);
}
