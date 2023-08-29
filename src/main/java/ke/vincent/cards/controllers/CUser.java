package ke.vincent.cards.controllers;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ke.vincent.cards.dtos.user.UserDTO;
import ke.vincent.cards.models.EUser;
import ke.vincent.cards.responses.SuccessResponse;
import ke.vincent.cards.services.user.IUser;

@RestController
public class CUser {
    
    @Autowired
    private IUser sUser;

    @PostMapping(path = "/user", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createUser(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {

        EUser user = sUser.create(userDTO);

        return ResponseEntity
            .created(new URI("/user/" + user.getId()))
            .body(new SuccessResponse(201, "successfully created user", new UserDTO(user)));
    }

    @GetMapping(path = "/user/{email}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getUser(@PathVariable String email) {

        EUser user = sUser.getByEmail(email, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched user", new UserDTO(user)));
    }
}
