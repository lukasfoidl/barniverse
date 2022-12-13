package at.barniverse.backend.barniverse_backend.security;

import at.barniverse.backend.barniverse_backend.dto.AuthDto;
import at.barniverse.backend.barniverse_backend.enums.Role;
import at.barniverse.backend.barniverse_backend.enums.RoleConverter;
import at.barniverse.backend.barniverse_backend.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt_secret}")
    private String secret;

    public String generateToken(AuthDto authDto) throws IllegalArgumentException, JWTCreationException {
        return JWT.create()
                .withSubject("User Details")
                .withClaim("email", authDto.getEmail())
                .withClaim("username", authDto.getUsername())
                .withClaim("role", authDto.getRole())
                .withClaim("uuid", authDto.getUuid())
                .withIssuedAt(new Date())
                .withExpiresAt(calculateDateInXMinutes(15))
                .withIssuer("Barniverse")
                .sign(Algorithm.HMAC256(secret));
    }

    public AuthDto validateTokenAndRetrieveSubject(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .withIssuer("Barniverse")
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return new AuthDto(jwt.getClaim("email").asString(), jwt.getClaim("username").asString(), jwt.getClaim("role").asString(), jwt.getClaim("uuid").asString());
    }

    private Date calculateDateInXMinutes(int minutes) {
        Calendar date = Calendar.getInstance();
        long timeInSecs = date.getTimeInMillis();
        return new Date(timeInSecs + ((long) minutes * 60 * 1000));
    }
}