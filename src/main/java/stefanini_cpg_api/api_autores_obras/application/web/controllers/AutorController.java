package stefanini_cpg_api.api_autores_obras.application.web.controllers;

import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stefanini_cpg_api.api_autores_obras.application.web.dto.request.AutorRequestDTO;
import stefanini_cpg_api.api_autores_obras.application.web.dto.response.ArtworkResponseDTO;
import stefanini_cpg_api.api_autores_obras.application.web.dto.response.AutorArtworkResponseDTO;
import stefanini_cpg_api.api_autores_obras.application.web.dto.response.AutorResponseDTO;
import stefanini_cpg_api.api_autores_obras.domain.services.impl.AutorServiceImpl;

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
    public ResponseEntity<Page<AutorArtworkResponseDTO>>getAllArtworksByAutorName(@PageableDefault(size = 10, sort = {"name"}) Pageable pagination, @PathVariable String autorName){
        var page = this.autorService.getAllArtworksByAutorName(pagination, autorName);
        return ResponseEntity.ok(page);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(Long id){
        this.autorService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
