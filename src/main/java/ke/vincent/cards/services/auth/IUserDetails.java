package ke.vincent.cards.services.auth;

import org.springframework.security.core.userdetails.UserDetailsService;

import ke.vincent.cards.models.EUser;

public interface IUserDetails extends UserDetailsService {
    
    Boolean checkIsAdmin();

    EUser getActiveUserByContact();
}
