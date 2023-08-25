package ke.vincent.cards.dtos.card;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import ke.vincent.cards.dtos.user.UserDTO;
import ke.vincent.cards.models.ECard;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CardDTO {
    
    private Integer id;

    @NotBlank
    private String name;

    private LocalDateTime dateCreated;

    @Pattern(regexp = "^#[a-zA-Z0-9]{6}$", message = "Color should be in the format'#XXXXXX'")
    private String color;

    private String description;

    private String status;

    private Integer statusId;

    private UserDTO user;

    private Integer userId;

    public CardDTO(ECard card) {
        setId(card.getId());
        setName(card.getName());
        setDateCreated(card.getDateCreated());
        setColor(card.getColor());
        setDescription(card.getDescription());
        setStatus(card.getStatus().getName());
        setUser(new UserDTO(card.getUser()));
    }
}
