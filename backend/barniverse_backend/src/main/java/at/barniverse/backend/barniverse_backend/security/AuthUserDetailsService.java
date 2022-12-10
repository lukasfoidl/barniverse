package at.barniverse.backend.barniverse_backend.security;

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

@Component
public class AuthUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user;
        try {
            user = userRepository.findByEmail(email);
        } catch (Exception exception) {
            throw new PersistenceException();
        }
        if (user.isEmpty())
            throw new UsernameNotFoundException("Could not find User with email = " + email);
        return new org.springframework.security.core.userdetails.User(
                email,
                user.get().getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(convertToRole(user.get().isAdmin()))));
    }

    private String convertToRole(boolean isAdmin) {
        if (isAdmin) {
            return "ROLE_ADMIN";
        } else {
            return "ROLE_USER";
        }
    }
}