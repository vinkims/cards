package ke.vincent.cards.controllers;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ke.vincent.cards.dtos.card.CardDTO;
import ke.vincent.cards.dtos.general.PageDTO;
import ke.vincent.cards.models.ECard;
import ke.vincent.cards.models.EUser;
import ke.vincent.cards.responses.SuccessPaginatedResponse;
import ke.vincent.cards.responses.SuccessResponse;
import ke.vincent.cards.services.auth.IUserDetails;
import ke.vincent.cards.services.card.ICard;

@RestController
public class CCard {
    
    @Autowired
    private ICard sCard;

    @Autowired
    private IUserDetails sUserDetails;

    @PostMapping(path = "/card", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createCard(@Valid @RequestBody CardDTO cardDTO) throws URISyntaxException {

        EUser user = sUserDetails.getActiveUserByContact();
        cardDTO.setUserId(user.getId());
        
        ECard card = sCard.create(cardDTO);

        return ResponseEntity
            .created(new URI("/card/" + card.getId()))
            .body(new SuccessResponse(201, "successfully created card", new CardDTO(card)));
    }

    @GetMapping(path = "/card", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getCards(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);

        List<String> allowedFields = new ArrayList<>(Arrays.asList("name", "color", "status.id", "status.name", "dateCreated"));

        Page<ECard> cards = sCard.getPaginatedList(pageDTO, allowedFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched cards", cards, CardDTO.class, ECard.class));
    }

    @GetMapping(path = "/card/{cardId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getCardById(@PathVariable Integer cardId) {

        ECard card = sCard.getById(cardId, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched card", new CardDTO(card)));
    }

    @PatchMapping(path = "/card/{cardId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateCard(@PathVariable Integer cardId, @RequestBody CardDTO cardDTO) {

        ECard card = sCard.update(cardId, cardDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated card", new CardDTO(card)));
    }

    @DeleteMapping(path = "/card/{cardId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> deleteCard(@PathVariable Integer cardId) {

        sCard.delete(cardId);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully deleted card", cardId));
    }
}
