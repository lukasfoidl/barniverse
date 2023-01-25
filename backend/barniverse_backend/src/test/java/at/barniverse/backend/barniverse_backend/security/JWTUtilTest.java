package at.barniverse.backend.barniverse_backend.security;

import at.barniverse.backend.barniverse_backend.dto.AuthDto;
import at.barniverse.backend.barniverse_backend.enums.Role;
import at.barniverse.backend.barniverse_backend.exception.BarniverseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@ExtendWith(MockitoExtension.class)
class JWTUtilTest {
    @InjectMocks
    JWTUtil jwtUtil;



    AuthDto authDto;

    @BeforeEach
    void setUp(){
        authDto = new AuthDto();
        authDto.setUuid("1");
        authDto.setEmail("jonny.doe@test.at");
        authDto.setUsername("jonnydoe123");
        authDto.setRole(Role.ROLE_USER.toString());
    }

    @Test
    void getToken() throws BarniverseException {
        //because secret will be null
        BarniverseException thrown = Assertions.assertThrows(BarniverseException.class, () -> {
            Map<String, String> returned = jwtUtil.getToken(authDto);
        });




    }

    @Test
    void validateTokenAndRetrieveSubject() {
        
        AtomicReference<AuthDto> authDto = new AtomicReference<>(new AuthDto());
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            authDto.set(jwtUtil.validateTokenAndRetrieveSubject("token"));
        });

    }
}