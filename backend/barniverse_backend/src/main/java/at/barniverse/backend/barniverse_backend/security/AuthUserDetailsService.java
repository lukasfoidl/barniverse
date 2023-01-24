package at.barniverse.backend.barniverse_backend.security;

import at.barniverse.backend.barniverse_backend.enums.RoleConverter;
import at.barniverse.backend.barniverse_backend.model.User;
import at.barniverse.backend.barniverse_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.persistence.PersistenceException;
import java.util.Collections;
import java.util.Optional;

/**
 * extension class for authentication process which loads needed data
 */
@Component
public class AuthUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * loads corresponding user data from database
     * @param email email of the user which needs to be loaded
     * @return user data needed for authentication
     * @throws UsernameNotFoundException thrown if no user exists to the given email
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user;
        try {
            user = userRepository.findByEmail(email);
        } catch (Exception exception) {
            throw new PersistenceException();
        }
        if (user == null) {
            throw new UsernameNotFoundException("Could not find User with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(
                email,
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(RoleConverter.getRole(user.getIsAdmin()).toString())));
    }
}