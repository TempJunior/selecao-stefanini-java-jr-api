package stefanini_cpg_api.api_autores_obras.domain.exceptions;

public class AutorNotFoundException extends RuntimeException {
    public AutorNotFoundException(String message) {
        super(message);
    }
    
    public AutorNotFoundException(Long id) {
        super("Autor not found with ID: " + id);
    }
}
