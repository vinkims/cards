package ke.vincent.cards.services.auth;

import ke.vincent.cards.dtos.general.AuthDTO;

public interface IAuth {
    
    String authenticateUser(AuthDTO authDTO);
}
