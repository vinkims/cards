package ke.vincent.cards.services.card;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import ke.vincent.cards.dtos.card.CardDTO;
import ke.vincent.cards.dtos.general.PageDTO;
import ke.vincent.cards.exceptions.NotFoundException;
import ke.vincent.cards.models.ECard;
import ke.vincent.cards.models.EStatus;
import ke.vincent.cards.models.EUser;
import ke.vincent.cards.repositories.CardDAO;
import ke.vincent.cards.services.status.IStatus;
import ke.vincent.cards.services.user.IUser;
import ke.vincent.cards.specifications.SpecBuilder;
import ke.vincent.cards.specifications.SpecFactory;

@Service
public class SCard implements ICard {

    @Value(value = "${default.value.status.todo}")
    private Integer todoStatusId;

    @Autowired
    private CardDAO cardDAO;

    @Autowired
    private SpecFactory specFactory;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private IUser sUser;

    @Override
    public ECard create(CardDTO cardDTO) {

        ECard card = new ECard();
        card.setName(cardDTO.getName());
        card.setDateCreated(LocalDateTime.now());
        card.setColor(cardDTO.getColor());
        card.setDescription(cardDTO.getDescription());
        setStatus(card, todoStatusId);
        setUser(card, cardDTO.getUserId());

        save(card);
        return card;
    }

    @Override
    public Optional<ECard> getById(Integer cardId) {
        return cardDAO.findById(cardId);
    }

    @Override
    public ECard getById(Integer cardId, Boolean handleException) {
        Optional<ECard> card = getById(cardId);
        if (!card.isPresent() && handleException) {
            throw new NotFoundException("card with specified id not found", "cardId");
        }
        return card.get();
    }

    @Override
    public Page<ECard> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<ECard> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<ECard>) specFactory.generateSpecification(search, specBuilder, allowedFields);

        Specification<ECard> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return cardDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(ECard card) {
        cardDAO.save(card);
    }

    @Override
    public ECard update(Integer cardId, CardDTO cardDTO) {

        ECard card = getById(cardId, true);
        if (cardDTO.getName() != null) {
            card.setName(cardDTO.getName());
        }

        if (cardDTO.getColor() != null && !cardDTO.getColor().isBlank()) {
            card.setColor(cardDTO.getColor());
        } else {
            card.setColor(null);
        }

        if (cardDTO.getDescription() != null && !cardDTO.getDescription().isBlank()) {
            card.setDescription(cardDTO.getDescription());
        } else {
            card.setDescription(null);
        }

        setStatus(card, cardDTO.getStatusId());

        save(card);
        return card;
    }

    @Override
    public void delete(Integer cardId) {
        ECard card = getById(cardId, true);
        cardDAO.delete(card);
    }

    private void setStatus(ECard card, Integer statusId) {
        if (statusId != null) {
            EStatus status = sStatus.getById(statusId, true);
            card.setStatus(status);
        }
    }

    private void setUser(ECard card, Integer userId) {
        if (userId != null) {
            EUser user = sUser.getById(userId, true);
            card.setUser(user);
        }
    }
    
}
