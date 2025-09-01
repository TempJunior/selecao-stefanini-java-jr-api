package stefanini_cpg_api.api_autores_obras.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import stefanini_cpg_api.api_autores_obras.application.web.dto.request.AutorRequestDTO;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_autor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String email;
    private LocalDate dateOfBirth;
    private String country;
    private String cpf;

    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "tb_autor_obras",
            joinColumns = { @JoinColumn ( name = "obras_id" )},
            inverseJoinColumns = { @JoinColumn ( name = "projeto_id" )}
    )
    private Set<Artwork> artwork = new HashSet<>();

    public Autor(AutorRequestDTO autorRequestDTO) {
        this.name = autorRequestDTO.name();
        this.gender = autorRequestDTO.gender();
        this.email = autorRequestDTO.email();
        this.dateOfBirth = autorRequestDTO.dateOfBirth();
        this.country = autorRequestDTO.country();
        this.cpf = autorRequestDTO.cpf();
        this.artwork = autorRequestDTO.artwork();
    }
}
