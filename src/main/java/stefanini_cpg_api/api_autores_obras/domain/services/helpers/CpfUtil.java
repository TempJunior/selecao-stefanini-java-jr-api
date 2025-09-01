package stefanini_cpg_api.api_autores_obras.domain.services.helpers;

public final class CpfUtil {
    private CpfUtil() {}
    public static String normalize(String cpf) {
        if (cpf == null) return null;
        return cpf.replaceAll("\\D", "");
    }
}
