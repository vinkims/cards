package ke.vincent.cards.dtos.card;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;
import ke.vincent.cards.dtos.user.UserDTO;
import ke.vincent.cards.models.ECard;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class CardDTO {
    
    @ApiModelProperty(accessMode = AccessMode.READ_ONLY, hidden = true)
    private Integer id;

    @NotBlank
    private String name;

    @ApiModelProperty(accessMode = AccessMode.READ_ONLY, hidden = true)
    private LocalDateTime dateCreated;

    @Pattern(regexp = "^#[a-zA-Z0-9]{6}$", message = "Color should be in the format'#XXXXXX'")
    private String color;

    private String description;

    private String status;

    @ApiModelProperty(accessMode = AccessMode.READ_ONLY, hidden = true)
    private UserDTO user;

    @ApiModelProperty(hidden = true)
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
