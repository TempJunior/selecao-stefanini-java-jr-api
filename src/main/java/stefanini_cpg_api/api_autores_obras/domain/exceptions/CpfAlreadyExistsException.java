package stefanini_cpg_api.api_autores_obras.domain.exceptions;

public class CpfAlreadyExistsException extends RuntimeException {
    public CpfAlreadyExistsException(String message) {
        super(message);
    }
    
    public CpfAlreadyExistsException(String cpf, Long id) {
        super("CPF '" + cpf + "' is already registered" + 
              (id != null ? " by another author" : ""));
    }
}
