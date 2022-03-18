package foxgurev.blps.auth;

import foxgurev.blps.auth.user.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    public void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        Optional<String> optionalJwt = getTokenFromRequest(request);
        if (optionalJwt.isPresent() && jwtUtil.tokenIsValid(optionalJwt.get())) {
            String email = jwtUtil.subjectFromToken(optionalJwt.get());
            User user = userRepository.findUserByEmail(email).orElseThrow(
                    () -> new RuntimeException("Failed to find user by email")
            );
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())
            );
        }
        filterChain.doFilter(request, response);
    }

    private Optional<String> getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer == null) return Optional.empty();
        return bearer.startsWith("Bearer ") ? Optional.of(bearer.substring(7)) : Optional.empty();
    }
}
