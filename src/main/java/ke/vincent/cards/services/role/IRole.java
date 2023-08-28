package ke.vincent.cards.services.role;

import java.util.Optional;

import ke.vincent.cards.models.ERole;

public interface IRole {
    
    Optional<ERole> getByName(String name);

    ERole getByName(String name, Boolean handleException);
}
