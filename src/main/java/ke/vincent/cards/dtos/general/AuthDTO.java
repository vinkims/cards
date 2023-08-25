package ke.vincent.cards.dtos.general;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AuthDTO {
    
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
