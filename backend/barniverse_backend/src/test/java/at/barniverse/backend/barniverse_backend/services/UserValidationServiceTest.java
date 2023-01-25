package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.enums.UserState;
import at.barniverse.backend.barniverse_backend.exception.BarniverseException;
import at.barniverse.backend.barniverse_backend.model.User;
import at.barniverse.backend.barniverse_backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserValidationServiceTest {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserValidationService userValidationService;



    @Test
    void validateEntitySpecificExtras() throws BarniverseException {

        List<String> list= new ArrayList<>();

        User user = new User();
        user.setId(1);
        user.setFirstname("John1");
        user.setLastname("Doe1");
        user.setUsername("JonnyDoe1231");
        user.setEmail("jonnydoe@test1.at");
        user.setPassword("dgsdfg456dzhhmnnlkklghknfghndhgmztuitjl5");
        user.setState(UserState.active);
        user.setPicture("test1.png");

        given(userRepository.existsByIdAndState(1, UserState.active)).willReturn(true);

        List<String> listNew =  userValidationService.validateEntitySpecificExtras(user);

        System.out.println(list.size());
        System.out.println(listNew.size());

        assertEquals(list.size(), listNew.size());



    }

    @Test
    void validateEntitySpecificExtrasError() throws BarniverseException {

        List<String> list= new ArrayList<>();
        list.add("User is not active!");

        User user = new User();
        user.setId(1);
        user.setFirstname("John1");
        user.setLastname("Doe1");
        user.setUsername("JonnyDoe1231");
        user.setEmail("jonnydoe@test1.at");
        user.setPassword("dgsdfg456dzhhmnnlkklghknfghndhgmztuitjl5");
        user.setState(UserState.active);
        user.setPicture("test1.png");

        given(userRepository.existsByIdAndState(1, UserState.active)).willReturn(false);

        List<String> listNew =  userValidationService.validateEntitySpecificExtras(user);

        System.out.println(list.get(0));
        System.out.println(listNew.get(0));

        assertEquals(list.size(), listNew.size());
        assertEquals(list.get(0), listNew.get(0));



    }
}