package stefanini_cpg_api.api_autores_obras.domain.exceptions;

public class ArtworkNotFoundException extends RuntimeException {
    public ArtworkNotFoundException(String message) {
        super(message);
    }
    
    public ArtworkNotFoundException(Long id) {
        super("Artwork not found with ID: " + id);
    }
}
