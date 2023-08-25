package ke.vincent.cards.dtos.user;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ke.vincent.cards.models.EUser;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class UserDTO {
    
    private Integer id;

    private String firstName;

    private String lastName;

    private LocalDateTime dateCreated;

    private String email;

    private String password;

    private String role;

    private Integer roleId;

    public UserDTO(EUser user) {
        setId(user.getId());
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setDateCreated(user.getDateCreated());
        setEmail(user.getEmail());
        setRole(user.getRole().getName());
    }
}
