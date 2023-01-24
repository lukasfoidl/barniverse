package at.barniverse.backend.barniverse_backend.security;

import at.barniverse.backend.barniverse_backend.exception.BarniverseException;
import at.barniverse.backend.barniverse_backend.dto.AuthDto;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.*;

import static at.barniverse.backend.barniverse_backend.configuration.Context.ERROR;

/**
 * creates and validates json web tokens
 */
@Component
public class JWTUtil {

    @Value("${jwt_secret}")
    private String secret;

    /**
     * generates a json web token
     * @param authDto dto with the needed data for token generation
     * @return response with the corresponding status code and a jwt token or error message in case of failure
     */
    public Map<String, String> getToken(AuthDto authDto) throws BarniverseException {
        String token = generateToken(authDto);
        return Collections.singletonMap("jwt-token", token);
    }

    /**
     * extension method which generates a json web token
     * @param authDto dto with the needed data for token generation
     * @return json web token
     */
    private String generateToken(AuthDto authDto) throws BarniverseException {
        try {
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
        } catch (Exception exception) {
            throw new BarniverseException(List.of(ERROR), HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
    }

    /**
     * validates the jwt and extracts the included data
     * @param token json web token
     * @return extracted data from the jwt
     * @throws JWTVerificationException thrown if the jwt is no valid
     */
    public AuthDto validateTokenAndRetrieveSubject(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .withIssuer("Barniverse")
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return new AuthDto(jwt.getClaim("email").asString(), jwt.getClaim("username").asString(), jwt.getClaim("role").asString(), jwt.getClaim("uuid").asString());
    }

    /**
     * extension method which calculates a date in x minutes
     * @param minutes number of minutes in the future
     * @return a date x minutes in the future from now
     */
    private Date calculateDateInXMinutes(int minutes) {
        Calendar date = Calendar.getInstance();
        long timeInSecs = date.getTimeInMillis();
        return new Date(timeInSecs + ((long) minutes * 60 * 1000));
    }
}