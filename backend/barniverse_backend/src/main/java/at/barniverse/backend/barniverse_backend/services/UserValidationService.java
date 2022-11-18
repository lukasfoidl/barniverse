package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.model.User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * validation service which validates user specific extras
 */
@Service
public class UserValidationService extends ValidationService<User> {

    /**
     * validates user specific extras
     * @param user entity which should be validated
     * @return error messages, empty if validation was successful
     */
    @Override
    public List<String> validateEntitySpecificExtras(User user) {
        return Collections.emptyList();
    }
}
