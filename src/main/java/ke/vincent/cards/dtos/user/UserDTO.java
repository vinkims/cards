package ke.vincent.cards.dtos.user;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;
import ke.vincent.cards.annotations.IsEmailValid;
import ke.vincent.cards.models.EUser;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class UserDTO {
    
    @ApiModelProperty(accessMode = AccessMode.READ_ONLY, hidden = true)
    private Integer id;

    private String firstName;

    private String lastName;

    @ApiModelProperty(accessMode = AccessMode.READ_ONLY, hidden = true)
    private LocalDateTime dateCreated;

    @IsEmailValid
    private String email;

    private String password;

    private String role;

    public UserDTO(EUser user) {
        setId(user.getId());
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setDateCreated(user.getDateCreated());
        setEmail(user.getEmail());
        setRole(user.getRole().getName());
    }
}
