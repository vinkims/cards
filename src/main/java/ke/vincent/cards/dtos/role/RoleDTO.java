package ke.vincent.cards.dtos.role;

import ke.vincent.cards.models.ERole;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleDTO {
    
    private Integer id;

    private String name;

    public RoleDTO(ERole role) {
        setId(role.getId());
        setName(role.getName());
    }
}
