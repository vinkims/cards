package ke.vincent.cards.services.user;

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

import ke.vincent.cards.dtos.general.PageDTO;
import ke.vincent.cards.dtos.user.UserDTO;
import ke.vincent.cards.exceptions.NotFoundException;
import ke.vincent.cards.models.ERole;
import ke.vincent.cards.models.EUser;
import ke.vincent.cards.repositories.UserDAO;
import ke.vincent.cards.services.role.IRole;
import ke.vincent.cards.specifications.SpecBuilder;
import ke.vincent.cards.specifications.SpecFactory;

@Service
public class SUser implements IUser {

    @Value(value = "${default.value.role.member}")
    private String memberRole;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private SpecFactory specFactory;

    @Autowired
    private IRole sRole;

    @Override
    public Boolean checkExistsByEmail(String email) {
        return userDAO.existsByEmail(email);
    }

    @Override
    public EUser create(UserDTO userDTO) {

        EUser user = new EUser();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setDateCreated(LocalDateTime.now());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        String roleName = userDTO.getRole() == null ? memberRole : userDTO.getRole();
        setRole(user, roleName);

        save(user);
        return user;
    }

    @Override
    public Optional<EUser> getByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    public Optional<EUser> getById(Integer userId) {
        return userDAO.findById(userId);
    }

    @Override
    public EUser getById(Integer userId, Boolean handleException) {
        Optional<EUser> user = getById(userId);
        if (!user.isPresent() && handleException) {
            throw new NotFoundException("user with specified id not found", "userId");
        }
        return user.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EUser> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<EUser> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<EUser>) specFactory.generateSpecification(search, specBuilder, allowedFields);

        Specification<EUser> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return userDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EUser user) {
        userDAO.save(user);
    }

    public void setRole(EUser user, String roleName) {
        ERole role = sRole.getByName(roleName, true);
        user.setRole(role);
    }
    
}
