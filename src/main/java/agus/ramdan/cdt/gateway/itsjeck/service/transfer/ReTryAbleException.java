package agus.ramdan.cdt.gateway.itsjeck.service.transfer;

import agus.ramdan.base.exception.ErrorValidation;
import agus.ramdan.base.exception.Errors;
import agus.ramdan.base.exception.XxxException;
import jakarta.validation.constraints.NotNull;

import java.util.Collection;

public class ReTryAbleException extends XxxException {
    public ReTryAbleException(String message, int code) {
        super(message, code);
    }

    public ReTryAbleException(String message, int code, Throwable cause) {
        super(message, code, cause);
    }

    public ReTryAbleException(String message, int code, String errCode, Throwable cause, ErrorValidation... errorValidations) {
        super(message, code, errCode, cause, errorValidations);
    }

    public ReTryAbleException(String message, int code, String errCode, Throwable cause, Errors errors) {
        super(message, code, errCode, cause, errors);
    }

    public ReTryAbleException(String message, int code, String errCode, Throwable cause, @NotNull Collection<ErrorValidation> errorValidations) {
        super(message, code, errCode, cause, errorValidations);
    }
}
