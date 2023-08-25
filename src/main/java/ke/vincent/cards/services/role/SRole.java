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
    public Optional<ERole> getById(Integer roleId) {
        return roleDAO.findById(roleId);
    }

    @Override
    public ERole getById(Integer roleId, Boolean handleException) {
        Optional<ERole> role = getById(roleId);
        if (!role.isPresent() && handleException) {
            throw new NotFoundException("role with specified id not found", "roleId");
        }
        return role.get();
    }
}
