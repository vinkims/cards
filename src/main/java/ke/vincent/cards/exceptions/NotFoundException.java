package ke.vincent.cards.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Data
@EqualsAndHashCode(callSuper = false)
public class NotFoundException extends RuntimeException {
    
    private String exceptionMsg;

    private String invalidField;

    public NotFoundException(String exception) {
        super(exception);
        this.exceptionMsg = exception;
    }

    public NotFoundException(String exception, String field) {
        super(exception);
        this.exceptionMsg = exception;
        this.invalidField = field;
    }
}
