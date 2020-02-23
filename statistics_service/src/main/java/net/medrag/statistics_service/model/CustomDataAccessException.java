package net.medrag.statistics_service.model;

/**
 * {@author} Stanislav Tretyakov
 * 22.02.2020
 */
public class CustomDataAccessException extends RuntimeException {
    public CustomDataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
