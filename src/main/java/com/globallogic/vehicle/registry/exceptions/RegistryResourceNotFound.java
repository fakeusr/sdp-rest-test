package com.globallogic.vehicle.registry.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RegistryResourceNotFound extends RegistryException {

    public RegistryResourceNotFound(String message) {
        super(message);
    }
}
