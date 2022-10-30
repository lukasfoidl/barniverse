package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.model.User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

// validation service which validates entity specific extras (like foreign keys, subentities, etc.)
@Service
public class UserValidationService extends ValidationService<User> {

    @Override
    public List<String> validateEntitySpecificExtras(User user) {
        return Collections.emptyList();
    }
}
