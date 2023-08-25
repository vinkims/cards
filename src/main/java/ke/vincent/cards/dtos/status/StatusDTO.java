package ke.vincent.cards.dtos.status;

import ke.vincent.cards.models.EStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StatusDTO {
    
    private Integer id;

    private String name;

    public StatusDTO(EStatus status) {
        setId(status.getId());
        setName(status.getName());
    }
}
