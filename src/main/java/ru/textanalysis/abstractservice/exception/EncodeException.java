package ru.textanalysis.abstractservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class EncodeException extends RuntimeException {

    public EncodeException(String message) {
        super(message);
    }

    public EncodeException(String message, Throwable cause) {
        super(message, cause);
    }

}
