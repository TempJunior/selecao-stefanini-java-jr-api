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

    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_autor_obras",
            joinColumns = { @JoinColumn ( name = "obras_id" )},
            inverseJoinColumns = { @JoinColumn ( name = "autor_id" )}
    )
    private Set<Autor> autores = new HashSet<>();

    public void updateArtwork(ArtworkRequestDTO artworkRequestDTO, Set<Autor> autores) {
        if (artworkRequestDTO.name() != null) {
            this.name = artworkRequestDTO.name();
        }
        if (artworkRequestDTO.description() != null) {
            this.description = artworkRequestDTO.description();
        }
        if (artworkRequestDTO.publicationDate() != null) {
    }
        if (artworkRequestDTO.exposureDate() != null) {
            this.exposureDate = artworkRequestDTO.exposureDate();
        }
        if (artworkRequestDTO.authorIds() != null) {
            this.autores = autores;
        }
    }
}
