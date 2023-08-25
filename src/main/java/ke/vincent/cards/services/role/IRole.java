package ke.vincent.cards.services.role;

import java.util.Optional;

import ke.vincent.cards.models.ERole;

public interface IRole {
    
    Optional<ERole> getById(Integer roleId);

    ERole getById(Integer roleId, Boolean handleException);
}
