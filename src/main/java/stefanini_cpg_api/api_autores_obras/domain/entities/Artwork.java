package stefanini_cpg_api.api_autores_obras.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import stefanini_cpg_api.api_autores_obras.application.web.dto.request.ArtworkRequestDTO;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_artwork")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Artwork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Column(name = "publication_date")
    private LocalDate publicationDate;
    @Column(name = "exposure_date")
    private LocalDate exposureDate;

    @ManyToMany(mappedBy = "artwork")
    private Set<Autor> autor = new HashSet<>();

    public Artwork(ArtworkRequestDTO artworkRequestDTO) {
        this.name = artworkRequestDTO.name();
        this.description = artworkRequestDTO.description();
        this.publicationDate = artworkRequestDTO.publicationDate();
        this.exposureDate = artworkRequestDTO.exposureDate();
        this.autor = artworkRequestDTO.autores();
    }
}
