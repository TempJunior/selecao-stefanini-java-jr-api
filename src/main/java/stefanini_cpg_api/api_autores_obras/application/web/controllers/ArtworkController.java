package stefanini_cpg_api.api_autores_obras.application.web.controllers;

import jakarta.validation.Valid;
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

@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"}, allowCredentials = "true")
@RestController
@RequestMapping("/artwork")
public class ArtworkController {

    private final ArtworkServiceImpl artworkService;

    public ArtworkController(ArtworkServiceImpl artworkService){
        this.artworkService = artworkService;
    }

    @PostMapping
    public ResponseEntity<ArtworkResponseDTO> create(@RequestBody @Valid ArtworkRequestDTO artworkRequest){
        var artworkCreated = this.artworkService.create(artworkRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ArtworkResponseDTO(artworkCreated));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ArtworkResponseDTO>> getAllArtworks(
            @PageableDefault(size = 10, sort = {"name"}) Pageable pagination){
        
        var page = this.artworkService.getAllArtworks(pagination);
        return ResponseEntity.ok(page);
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<ArtworkResponseDTO>> searchArtworks(
            @PageableDefault(size = 10, sort = {"name"}) Pageable pagination,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description){
        
        var page = this.artworkService.getAllArtworksFiltered(pagination, name, description);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtworkResponseDTO> getById(@PathVariable Long id){
        var artwork = this.artworkService.findById(id);
        return ResponseEntity.ok(new ArtworkResponseDTO(artwork));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtworkResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ArtworkRequestDTO artworkRequest){
        var updatedArtwork = this.artworkService.update(id, artworkRequest);
        return ResponseEntity.ok(new ArtworkResponseDTO(updatedArtwork));
    }

    @GetMapping("/{id}/autores")
    public ResponseEntity<Page<AutorResponseDTO>> getAllAutoresOfArtworkById(
            @PageableDefault(size = 10, sort = {"name"}) Pageable pagination, 
            @PathVariable Long id){
        var page = this.artworkService.getAllAutoresOfArtwork(pagination, id);
        return ResponseEntity.ok(page);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        this.artworkService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/test-relationship")
    public ResponseEntity<String> testRelationship(){
        // Teste simples para verificar se o relacionamento está funcionando
        var artwork = this.artworkService.findById(1L);
        int authorCount = artwork.getAutores() != null ? artwork.getAutores().size() : 0;
        return ResponseEntity.ok("Obra ID 1 tem " + authorCount + " autores");
    }
    
}
