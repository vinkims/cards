package ke.vincent.cards.services.auth;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ke.vincent.cards.exceptions.InvalidInputException;
import ke.vincent.cards.models.EUser;
import ke.vincent.cards.services.user.IUser;

@Service
public class SUserDetails implements IUserDetails {

    @Value(value = "${default.value.role.admin-id}")
    private Integer adminRoleId;

    @Autowired
    private IUser sUser;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<EUser> user = sUser.getByEmail(username);
        if (!user.isPresent()) {
            throw new InvalidInputException("invalid credentials provided", "username/password");
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        String password = user.get().getPassword();
        UserDetails userDetails = (UserDetails) new User(username, password == null ? "" : password, grantedAuthorities);

        return userDetails;
    }

    @Override
    public Boolean checkIsAdmin() {
        EUser activeUser = getActiveUserByContact();
        Integer roleId = activeUser.getRole().getId();
        return roleId == adminRoleId;
    }

    @Override
    public EUser getActiveUserByContact() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<EUser> activeUser = sUser.getByEmail(((UserDetails) principal).getUsername());
        return activeUser.get();
    }
    
}
