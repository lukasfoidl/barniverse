package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.model.Auction;
import at.barniverse.backend.barniverse_backend.model.User;
import at.barniverse.backend.barniverse_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static at.barniverse.backend.barniverse_backend.configuration.Context.VALIDATION_ERROR;

/**
 * validation service which validates user specific extras
 */
@Service
public class UserValidationService extends ValidationService<User> {

    @Autowired private UserRepository userRepository;
    /**
     * validates user specific extras
     * @param user entity which should be validated
     * @return error messages, empty if validation was successful
     */
    @Override
    public List<String> validateEntitySpecificExtras(User user) {
        List<String> errors = new ArrayList<>();
        boolean isPOST = user.getId() == 0;

        try {
            errors = validateUser(errors, user, isPOST); // validate entity itself
        } catch (Exception exception) {
            return List.of(VALIDATION_ERROR);
        }
        return errors;
    }

    private List<String> validateUser(List<String> errors, User user, boolean isPOST) throws Exception {
        if (!isPOST) { // PUT
            User dbUser = userRepository.findById(user.getId()).get(); // existence already checked before validation
            // do not update inactive user, on POST always set to active
            if (!dbUser.isActive()) {
                errors.add("User is not active!");
            }
        }
        return errors;
    }
}
