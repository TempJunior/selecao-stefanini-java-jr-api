package stefanini_cpg_api.api_autores_obras.domain.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import stefanini_cpg_api.api_autores_obras.application.web.dto.request.ArtworkRequestDTO;

public class PublicationOrExposureDateValidator implements ConstraintValidator<PublicationOrExposureDateRequired, ArtworkRequestDTO> {

    @Override
    public boolean isValid(ArtworkRequestDTO artwork, ConstraintValidatorContext context) {
        if (artwork == null) {
            return true;
        }

        boolean hasPublication = artwork.publicationDate() != null;
        boolean hasExposure = artwork.exposureDate() != null;

        return hasPublication || hasExposure;
    }
}
