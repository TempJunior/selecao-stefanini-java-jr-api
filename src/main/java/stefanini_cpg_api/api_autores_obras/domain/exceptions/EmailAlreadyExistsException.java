package stefanini_cpg_api.api_autores_obras.domain.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
    
    public EmailAlreadyExistsException(String email, Long id) {
        super("Email '" + email + "' is already registered" + 
              (id != null ? " by another author" : ""));
    }
}
