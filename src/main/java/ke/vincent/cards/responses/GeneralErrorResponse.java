package ke.vincent.cards.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeneralErrorResponse {
    
    private String timestamp;

    private String message;

    private int status;
}
