package ke.vincent.cards.services.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import ke.vincent.cards.dtos.general.PageDTO;
import ke.vincent.cards.dtos.user.UserDTO;
import ke.vincent.cards.models.EUser;

public interface IUser {
    
    Boolean checkExistsByEmail(String email);
    
    EUser create(UserDTO userDTO);

    Optional<EUser> getByEmail(String email);

    Optional<EUser> getById(Integer userId);

    EUser getById(Integer userId, Boolean handleException);

    Page<EUser> getPaginatedList(PageDTO pageDTO, List<String> allowedFields);

    void save(EUser user);
}
