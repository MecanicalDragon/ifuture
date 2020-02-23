package net.medrag.account_service.model;

/**
 * Exists only for custom exception throwing
 * {@author} Stanislav Tretyakov
 * 22.02.2020
 */
public class CustomDataAccessException extends RuntimeException {
    public CustomDataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
