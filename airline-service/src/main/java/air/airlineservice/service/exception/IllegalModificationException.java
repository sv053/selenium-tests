package air.airlineservice.service.exception;

/**
 * Thrown to indicate that there was some kind of problem modifying the remote repository.
 */
public class IllegalModificationException extends RuntimeException {

    /**
     * Constructs a new IllegalModificationException with null as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to initCause().
     */
    public IllegalModificationException() {
        super();
    }

    /**
     * Constructs a new IllegalModificationException with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to initCause().
     *
     * @param message the detail message. The detail message is saved
     *                for later retrieval by the getMessage() method
     */
    public IllegalModificationException(String message) {
        super(message);
    }

    /**
     * Constructs a new IllegalModificationException with the specified detail message and cause.
     *
     * @param message the detail message. The detail message is saved
     *                for later retrieval by the getMessage() method
     * @param cause the cause (which is saved for later retrieval by the getCause() method)
     */
    public IllegalModificationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new IllegalModificationException with the specified cause and a detail message.
     *
     * @param cause the cause (which is saved for later retrieval by the getCause() method)
     */
    public IllegalModificationException(Throwable cause) {
        super(cause);
    }
}
