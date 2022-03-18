package se.ifmo.blos.lab2.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import se.ifmo.blos.lab2.dtos.RestErrorResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@NoArgsConstructor(access = PRIVATE)
public class HttpUtil {

    public static RestErrorResponse getRestErrorResponse(
            HttpStatus status, String errorMessage, HttpServletRequest httpServletRequest) {
        return RestErrorResponse.of(Instant.now(), status.value(), status.getReasonPhrase(),
                errorMessage, httpServletRequest.getRequestURL().toString());
    }
}
