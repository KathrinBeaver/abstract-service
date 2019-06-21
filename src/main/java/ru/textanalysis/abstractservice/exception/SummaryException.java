package ru.textanalysis.abstractservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class SummaryException extends RuntimeException {

    public SummaryException(String message) {
        super(message);
    }

    public SummaryException(String message, Throwable cause) {
        super(message, cause);
    }
}
