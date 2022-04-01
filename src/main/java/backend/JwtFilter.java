package backend;

import backend.entities.User;
import backend.security.JwtUtil;
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
import java.util.ArrayList;

import static org.springframework.util.StringUtils.hasText;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = getTokenFromRequest(request);
        if (jwt != null && jwtUtil.validateToken(jwt)) {
            User user = userRepository.findUserByEmail(jwtUtil.getWordForToken(jwt)).get();

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,
                    null, new ArrayList<>());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null; // todo optional
    }

//    private String parseJwt(HttpServletRequest request) {
//        String headerName = "Authorization";
//        String prefix = "Bearer ";
//        String headerAuth = request.getHeader(headerName);
//
//        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(prefix)) {
//            return headerAuth.substring(prefix.length());
//        }
////        log.debug("Invalid header name or auth prefix for request to {}", request.getRequestURI());
//        return null;
//    }
}
