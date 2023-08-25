package ke.vincent.cards.responses;

import lombok.Data;

@Data
public class SuccessResponse {
    
    private int status = 200;

    private String message;

    private Object content;

    public SuccessResponse(int status, String message, Object content) {
        this.setStatus(status);
        this.setMessage(message);
        this.setContent(content);
    }
}
