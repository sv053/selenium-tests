package com.airservice.booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE, reason = "no such entity")
public class RemoteResourceException extends RuntimeException{
    /**
     * Constructs a new RemoteResourceException with the specified detail message and cause.
     *
     * @param message the detail message. The detail message is saved
     *                for later retrieval by the getMessage() method
     * @param cause the cause (which is saved for later retrieval by the getCause() method)
     */
    public RemoteResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
