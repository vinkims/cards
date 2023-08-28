package ke.vincent.cards.services.role;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ke.vincent.cards.exceptions.NotFoundException;
import ke.vincent.cards.models.ERole;
import ke.vincent.cards.repositories.RoleDAO;

@Service
public class SRole implements IRole {
    
    @Autowired
    private RoleDAO roleDAO;

    @Override
    public Optional<ERole> getByName(String name) {
        return roleDAO.findByName(name);
    }

    @Override
    public ERole getByName(String name, Boolean handleException) {
        Optional<ERole> role = getByName(name);
        if (!role.isPresent() && handleException) {
            throw new NotFoundException("role with specified name not found", "role");
        }
        return role.get();
    }
}
