package ua.kiev.prog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by user on 16.09.2015.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UnknownMatchException extends RuntimeException {
    public UnknownMatchException(String matchId) {
        super("Unknown match: " + matchId);
    }
}
