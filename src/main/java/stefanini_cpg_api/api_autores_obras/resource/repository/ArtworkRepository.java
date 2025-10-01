package stefanini_cpg_api.api_autores_obras.resource.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;        
import stefanini_cpg_api.api_autores_obras.domain.entities.Artwork;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {

    @Query(value = "select distinct a from Autor a left join fetch a.artwork where lower(a.name) like lower(concat('%', :autorName, '%'))",
            countQuery = "select count(a) from Autor a where lower(a.name) like lower(concat('%', :autorName, '%'))")
    Page<Artwork> findByNameWithArtworks(@Param("autorName") String autorName, Pageable pageable);

    Page<Artwork> findByAutores_Id(Long autorId, Pageable pageable);
    Page<Artwork> findByAutores_NameContaining(Pageable pageable, String name);
    
    // Queries para carregar obras com autores
    @Query("SELECT DISTINCT a FROM Artwork a LEFT JOIN FETCH a.autores")
    Page<Artwork> findAllWithAutores(Pageable pageable);
    
    @Query("SELECT DISTINCT a FROM Artwork a LEFT JOIN FETCH a.autores WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Artwork> findByNameContainingIgnoreCaseWithAutores(@Param("name") String name, Pageable pageable);
    
    @Query("SELECT DISTINCT a FROM Artwork a LEFT JOIN FETCH a.autores WHERE LOWER(a.description) LIKE LOWER(CONCAT('%', :description, '%'))")
    Page<Artwork> findByDescriptionContainingIgnoreCaseWithAutores(@Param("description") String description, Pageable pageable);
    
    @Query("SELECT DISTINCT a FROM Artwork a LEFT JOIN FETCH a.autores WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%')) AND LOWER(a.description) LIKE LOWER(CONCAT('%', :description, '%'))")
    Page<Artwork> findByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCaseWithAutores(@Param("name") String name, @Param("description") String description, Pageable pageable);
    
    // Queries originais mantidas para compatibilidade
    Page<Artwork> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Artwork> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);
    Page<Artwork> findByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCase(String name, String description, Pageable pageable);

}
