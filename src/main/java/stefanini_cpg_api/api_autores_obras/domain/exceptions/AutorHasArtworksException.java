package stefanini_cpg_api.api_autores_obras.domain.exceptions;

public class AutorHasArtworksException extends RuntimeException {
    public AutorHasArtworksException(String message) {
        super(message);
    }
    
    public AutorHasArtworksException(Long autorId) {
        super("Cannot delete author with ID " + autorId + " because they have associated artworks");
    }
}
