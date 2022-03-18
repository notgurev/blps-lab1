package se.ifmo.blos.lab2.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;

import java.time.Instant;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Value(staticConstructor = "of")
public class RestErrorResponse implements Dto {
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSXXX", timezone = "UTC")
    Instant timestamp;

    int status;

    String error;

    String message;

    String requestUrl;
}
