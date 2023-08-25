package ke.vincent.cards.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ke.vincent.cards.dtos.general.AuthDTO;
import ke.vincent.cards.responses.SuccessResponse;
import ke.vincent.cards.services.auth.IAuth;

@RestController
public class CAuth {
    
    @Autowired
    private IAuth sAuth;

    @PostMapping(path = "/auth/signin", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> authenticateUser(@Valid @RequestBody AuthDTO authDTO) {

        String token = sAuth.authenticateUser(authDTO);

        Map<String, Object> res = new HashMap<>();
        res.put("token", token);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully authenticated user", res));
    }
}
