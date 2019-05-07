package ru.textanalysis.abstractservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class KeyWordsException extends RuntimeException{

    public KeyWordsException(String message) {
        super(message);
    }

    public KeyWordsException(String message, Throwable cause) {
        super(message, cause);
    }
}
