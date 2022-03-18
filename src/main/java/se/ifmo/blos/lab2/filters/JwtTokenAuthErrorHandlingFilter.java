package se.ifmo.blos.lab2.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;
import se.ifmo.blos.lab2.utils.HttpUtil;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
public class JwtTokenAuthErrorHandlingFilter extends OncePerRequestFilter {
//    private final ObjectMapper objectMapper;

//    public JwtTokenAuthErrorHandlingFilter() {
//        this.objectMapper = Jackson2ObjectMapperBuilder.json().build();
//    } todo

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
