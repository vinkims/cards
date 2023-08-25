package ke.vincent.cards.services.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import ke.vincent.cards.dtos.general.AuthDTO;
import ke.vincent.cards.exceptions.InvalidInputException;
import ke.vincent.cards.models.EUser;
import ke.vincent.cards.services.user.IUser;
import ke.vincent.cards.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SAuth implements IAuth {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUser sUser;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private IUserDetails sUserDetails;
    
    @Override
    public String authenticateUser(AuthDTO authDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authDTO.getUsername(), authDTO.getPassword()));
        } catch (BadCredentialsException ex) {
            log.info("{}", ex.getLocalizedMessage());
            throw new InvalidInputException("invalid credentials provided", "username/password");
        }

        UserDetails userDetails = sUserDetails.loadUserByUsername(authDTO.getUsername());

        EUser user = sUser.getByEmail(authDTO.getUsername()).get();
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("role", user.getRole().getName());

        final String token = jwtUtil.generateToken(userDetails, claims);

        return token;
    }
    
}
