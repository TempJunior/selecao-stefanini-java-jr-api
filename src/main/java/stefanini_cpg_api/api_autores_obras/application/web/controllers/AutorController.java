package stefanini_cpg_api.api_autores_obras.application.web.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stefanini_cpg_api.api_autores_obras.application.web.dto.request.AutorRequestDTO;
import stefanini_cpg_api.api_autores_obras.application.web.dto.response.ArtworkResponseDTO;
import stefanini_cpg_api.api_autores_obras.application.web.dto.response.AutorResponseDTO;
import stefanini_cpg_api.api_autores_obras.domain.services.impl.AutorServiceImpl;

@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"}, allowCredentials = "true")
@RestController
@RequestMapping("/autor")
public class AutorController {

    private final AutorServiceImpl autorService;

    public AutorController(AutorServiceImpl autorService){
        this.autorService = autorService;
    }

    @PostMapping
    public ResponseEntity<AutorResponseDTO> create(@RequestBody @Valid AutorRequestDTO autorRequest){
        var createdAutor = this.autorService.create(autorRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AutorResponseDTO(createdAutor));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<AutorResponseDTO>> getAllAtores(@PageableDefault(size = 10, sort = {"name"}) Pageable pagination){
        var page = this.autorService.getAllAtores(pagination);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/artworks/by-id/{id}")
    public ResponseEntity<Page<ArtworkResponseDTO>> getAllArtworksOfAutor(@PageableDefault(size = 10, sort = {"name"}) Pageable pagination, @PathVariable Long id){
        var page = this.autorService.getAllArtworks(id, pagination);

        return ResponseEntity.ok(page);
    }

    @GetMapping("artworks/by-name/{autorName}")
    public ResponseEntity<Page<ArtworkResponseDTO>>getAllArtworksByAutorName(@PageableDefault(size = 10, sort = {"name"}) Pageable pagination, @PathVariable String autorName){
        var page = this.autorService.getAllArtworksByAutorName(pagination, autorName);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> getById(@PathVariable Long id){
        var autor = this.autorService.findById(id);
        return ResponseEntity.ok(new AutorResponseDTO(autor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> update(@PathVariable Long id, @RequestBody @Valid AutorRequestDTO autorRequest){
        var updatedAutor = this.autorService.update(id, autorRequest);
        return ResponseEntity.ok(new AutorResponseDTO(updatedAutor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        this.autorService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
