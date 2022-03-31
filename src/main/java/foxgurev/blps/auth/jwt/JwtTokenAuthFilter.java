package foxgurev.blps.auth.jwt;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenAuthFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TOKEN_PREFIX = "Bearer ";

    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    @SneakyThrows
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) {
        final String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        log.info("Header: " + request.getHeader(AUTHORIZATION_HEADER));
        if (authHeader == null || !authHeader.startsWith(BEARER_TOKEN_PREFIX)) {
            log.warn("Caught unauthorized request for URL={}", request.getRequestURL().toString());
            filterChain.doFilter(request, response);
            return;
        }

        final String jwtToken = authHeader.replace(BEARER_TOKEN_PREFIX, "");
        try {
            if (jwtUtil.isTokenValid(jwtToken)) {
                final var username = jwtUtil.getUsername(jwtToken);
                final var user = userDetailsService.loadUserByUsername(username);

                final Authentication authentication =
                        new UsernamePasswordAuthenticationToken(user, null, jwtUtil.getAuthorities(jwtToken));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (final JwtException e) {
            log.warn("Error handling JWT token: {}", e.getMessage());
            throw e;
        } catch (final UsernameNotFoundException e) {
            log.warn("Username was not found during JWT authentication");
            throw e;
        }

        filterChain.doFilter(request, response);
    }
}
