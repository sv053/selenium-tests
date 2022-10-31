package air.userservice.service.exception;

/**
 * Thrown to indicate that there was some kind of problem accessing the remote resource.
 */
public class RemoteResourceException extends RuntimeException {

    /**
     * Constructs a new RemoteResourceException with null as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to initCause().
     */
    public RemoteResourceException() {
        super();
    }

    /**
     * Constructs a new RemoteResourceException with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to initCause().
     *
     * @param message the detail message. The detail message is saved
     *                for later retrieval by the getMessage() method
     */
    public RemoteResourceException(String message) {
        super(message);
    }

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

    /**
     * Constructs a new RemoteResourceException with the specified cause and a detail message.
     *
     * @param cause the cause (which is saved for later retrieval by the getCause() method)
     */
    public RemoteResourceException(Throwable cause) {
        super(cause);
    }
}
