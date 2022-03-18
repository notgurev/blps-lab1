package se.ifmo.blos.lab2.filters;

import io.jsonwebtoken.JwtException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class JwtTokenAuthErrorHandlingFilter extends OncePerRequestFilter {
    @Override
    @SneakyThrows
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            // todo
//            HttpUtil.writeRestErrorResponse(request, response, e, FORBIDDEN, objectMapper);
        } catch (UsernameNotFoundException e) {
            // todo
//            HttpUtil.writeRestErrorResponse(request, response, e, NOT_FOUND, objectMapper);
        }
    }
}
