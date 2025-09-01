package stefanini_cpg_api.api_autores_obras.resource.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import stefanini_cpg_api.api_autores_obras.application.web.dto.response.AutorArtworkResponseDTO;
import stefanini_cpg_api.api_autores_obras.domain.entities.Artwork;
import stefanini_cpg_api.api_autores_obras.domain.entities.Autor;

import java.util.Set;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    boolean existsByCpfAndIdNot(String cpf, Long id);

    @Query(
            value = "select distinct a from Autor a left join fetch a.artwork",
            countQuery = "select count(a) from Autor a"
    )
    Page<Autor> findPageWithArtworks(Pageable pageable);

    Page<AutorArtworkResponseDTO> findByName(Pageable pageable, String autor);

    boolean existsByArtwork_id(Long id);
}
