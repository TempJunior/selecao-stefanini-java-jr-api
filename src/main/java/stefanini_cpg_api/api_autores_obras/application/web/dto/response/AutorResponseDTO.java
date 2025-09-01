package stefanini_cpg_api.api_autores_obras.application.web.dto.response;

import stefanini_cpg_api.api_autores_obras.domain.entities.Artwork;
import stefanini_cpg_api.api_autores_obras.domain.entities.Autor;
import stefanini_cpg_api.api_autores_obras.domain.entities.Gender;

import java.time.LocalDate;
import java.util.Set;

public record AutorResponseDTO(
        Long id,
        String name,
        Gender gender,
        String email,
        LocalDate dateOfBirth,
        String country,
        String cpf,
        Set<Artwork> artwork
) {
    public AutorResponseDTO(Autor autor){
        this(autor.getId(), autor.getName(), autor.getGender(), autor.getEmail(), autor.getDateOfBirth(), autor.getCountry(), autor.getCpf(), autor.getArtwork());
    }
}
