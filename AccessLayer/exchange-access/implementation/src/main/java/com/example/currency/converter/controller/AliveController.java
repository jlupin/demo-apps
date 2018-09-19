package com.example.currency.converter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Piotr Heilman
 */
@RestController
public class AliveController {
    private boolean alive = true;

    @GetMapping("/isAlive")
    public String isAlive() {
        if (!alive) {
            throw new UnavailableException();
        }

        return "OK";
    }

    @GetMapping("/toggleAlive")
    public String toggleAlive() {
        this.alive = !this.alive;

        return "OK";
    }

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    private class UnavailableException extends RuntimeException {}
}
