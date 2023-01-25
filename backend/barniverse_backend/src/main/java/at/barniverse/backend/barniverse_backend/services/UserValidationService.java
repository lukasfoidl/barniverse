package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.exception.BarniverseException;
import at.barniverse.backend.barniverse_backend.enums.UserState;
import at.barniverse.backend.barniverse_backend.model.User;
import at.barniverse.backend.barniverse_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static at.barniverse.backend.barniverse_backend.configuration.Context.VALIDATION_ERROR;

/**
 * validation service with user validation functionality
 */
@Service
public class UserValidationService extends ValidationService<User> {

    @Autowired private UserRepository userRepository;

    /**
     * validates user specific extras
     * @param user entity which should be validated
     * @return error messages, empty if validation was successful
     * @throws BarniverseException in case of failure which includes error messages
     */
    @Override
    public List<String> validateEntitySpecificExtras(User user) throws BarniverseException {
        List<String> errors = new ArrayList<>();
        boolean isPOST = user.getId() == 0;

        try {
            errors = validateUser(errors, user, isPOST); // validate entity itself
        } catch (Exception exception) {
            throw new BarniverseException(List.of(VALIDATION_ERROR), HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
        return errors;
    }

    /**
     * extension method which validates user itself
     * @param errors messages of errors which already arisen
     * @param user user which should be validated
     * @param isPOST true if validation happens in context of a POST request, otherwise false
     * @return messages of already arisen errors as well as new error messages
     */
    private List<String> validateUser(List<String> errors, User user, boolean isPOST) {
        if (!isPOST) { // PUT (existence already checked in getEntity())
            // do not update inactive user, on POST always set to active
            if (!userRepository.existsByIdAndState(user.getId(), UserState.active)) {
                errors.add("User is not active!");
            }
        }
        return errors;
    }
}
