package stefanini_cpg_api.api_autores_obras.domain.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tb_artwork")
public class Artwork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @Column(name = "publication_date")
    private LocalDate publicationDate;
    @Column(name = "exposure_date")
    private LocalDate exposureDate;
}
