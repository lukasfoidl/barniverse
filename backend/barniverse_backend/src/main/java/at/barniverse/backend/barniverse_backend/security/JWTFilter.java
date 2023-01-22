package at.barniverse.backend.barniverse_backend.security;

import at.barniverse.backend.barniverse_backend.dto.AuthDto;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static at.barniverse.backend.barniverse_backend.configuration.Context.JWT_TOKEN_EXPIRED;
import static at.barniverse.backend.barniverse_backend.configuration.Context.JWT_TOKEN_INVALID;

/**
 * filters every request and checks the json web token
 */
@Component
public class JWTFilter extends OncePerRequestFilter {

    // Injecting Dependencies
    @Autowired private AuthUserDetailsService authUserDetailsService;
    @Autowired private JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Extracting the "Authorization" header
        String authHeader = request.getHeader("Authorization");

        // Checking if the header contains a Bearer token
        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
            // Extract JWT
            String jwt = authHeader.substring(7);
            if (jwt.isBlank()) {
                // Invalid JWT
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, JWT_TOKEN_INVALID);
            } else {
                try {
                    // Verify token and extract email
                    AuthDto authDto = jwtUtil.validateTokenAndRetrieveSubject(jwt);

                    // Fetch User Details
                    UserDetails userDetails = authUserDetailsService.loadUserByUsername(authDto.getEmail());

                    // Create Authentication Token
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(authDto.getEmail(), userDetails.getPassword(), userDetails.getAuthorities());

                    // Setting the authentication on the Security Context using the created token
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } catch (TokenExpiredException exception) {
                    // no BarniverseException because of Override method
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, JWT_TOKEN_EXPIRED);
                } catch (JWTVerificationException exc) {
                    // no BarniverseException because of Override method
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, JWT_TOKEN_INVALID);
                }
            }
        }

        // Continuing the execution of the filter chain
        filterChain.doFilter(request, response);
    }
}
