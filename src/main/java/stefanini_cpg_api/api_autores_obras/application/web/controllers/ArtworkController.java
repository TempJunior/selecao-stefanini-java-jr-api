package stefanini_cpg_api.api_autores_obras.application.web.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stefanini_cpg_api.api_autores_obras.application.web.dto.request.ArtworkRequestDTO;
import stefanini_cpg_api.api_autores_obras.application.web.dto.response.ArtworkResponseDTO;
import stefanini_cpg_api.api_autores_obras.application.web.dto.response.AutorResponseDTO;
import stefanini_cpg_api.api_autores_obras.domain.services.impl.ArtworkServiceImpl;

@RestController
@RequestMapping("/artwork")
public class ArtworkController {

    private final ArtworkServiceImpl artworkService;

    public ArtworkController(ArtworkServiceImpl artworkService){
        this.artworkService = artworkService;
    }

    @PostMapping
    public ResponseEntity<ArtworkResponseDTO> create(@RequestBody ArtworkRequestDTO artworkRequest){
        var artworkCreated = this.artworkService.create(artworkRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ArtworkResponseDTO(artworkCreated));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ArtworkResponseDTO>> getAllArtworks(@PageableDefault(size = 10, sort = {"name"}) Pageable pagination){
        var page = this.artworkService.getAllArtworks(pagination);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/autor/by-id/{id}")
    public ResponseEntity<Page<AutorResponseDTO>> getAllAtoresOfArtworkById(@PageableDefault(size = 10, sort = {"name"}) Pageable pagination, Long id){
        var page = this.artworkService.getAllAutoresOfArtwork(pagination, id);
        return ResponseEntity.ok(page);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>delete(@PathVariable Long id){
        this.artworkService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
