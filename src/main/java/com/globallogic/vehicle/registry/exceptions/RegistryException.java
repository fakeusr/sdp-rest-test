package com.globallogic.vehicle.registry.exceptions;

import lombok.Getter;

@Getter
public abstract class RegistryException extends RuntimeException {

    public RegistryException(String message) {
        super(message);
    }

	public RegistryException(Throwable e, String message) {
		super(message, e);
	}
}
