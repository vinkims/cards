package ke.vincent.cards.services.card;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import ke.vincent.cards.dtos.card.CardDTO;
import ke.vincent.cards.dtos.general.PageDTO;
import ke.vincent.cards.models.ECard;

public interface ICard {
    
    Boolean checkIsOwner(Integer cardId);
    
    ECard create(CardDTO cardDTO);

    Optional<ECard> getById(Integer cardId);

    ECard getById(Integer cardId, Boolean handleException);

    Page<ECard> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(ECard card);

    ECard update(Integer cardId, CardDTO cardDTO);

    void delete(Integer cardId);
}
