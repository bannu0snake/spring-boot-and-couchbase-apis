package com.mahaboob.assignment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NthManagerNotFoundException  extends RuntimeException {

    public NthManagerNotFoundException(int n) {
        super("Manager at level " +n + " does not exist");
    }
}
