package stefanini_cpg_api.api_autores_obras.domain.exceptions;

public class ArtworkMustHaveAuthorsException extends RuntimeException {
    public ArtworkMustHaveAuthorsException(String message) {
        super(message);
    }
    
    public ArtworkMustHaveAuthorsException() {
        super("Artwork must have at least one author");
    }
}
