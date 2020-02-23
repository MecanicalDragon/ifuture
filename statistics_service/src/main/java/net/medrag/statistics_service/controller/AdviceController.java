package net.medrag.statistics_service.controller;

import lombok.extern.slf4j.Slf4j;
import net.medrag.statistics_service.model.CustomDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Catches CustomDataAccessException and returns status 555 to dispatcher
 * {@author} Stanislav Tretyakov
 * 22.02.2020
 */
@Slf4j
@ControllerAdvice
public class AdviceController {
    @ExceptionHandler(value = CustomDataAccessException.class)
    ResponseEntity handleDataAccessException(CustomDataAccessException e) {
        log.error("Exception occurred during database interaction: {}. HTTP status 555 will be returned.", e.getMessage());
        e.printStackTrace();
        return ResponseEntity.status(555).build();
    }
}
