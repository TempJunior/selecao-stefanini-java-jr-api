package stefanini_cpg_api.api_autores_obras.resource.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import stefanini_cpg_api.api_autores_obras.application.web.dto.response.AutorResponseDTO;
import stefanini_cpg_api.api_autores_obras.domain.entities.Artwork;
import stefanini_cpg_api.api_autores_obras.domain.entities.Autor;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {

    @Query(value = "select distinct a from Autor a left join fetch a.artwork where lower(a.name) like lower(concat('%', :autorName, '%'))",
            countQuery = "select count(a) from Autor a where lower(a.name) like lower(concat('%', :autorName, '%'))")
    Page<Artwork> findByNameWithArtworks(@Param("autorName") String autorName, Pageable pageable);

    Page<Artwork> findByAutor_Id(Long autorId, Pageable pageable);
}
