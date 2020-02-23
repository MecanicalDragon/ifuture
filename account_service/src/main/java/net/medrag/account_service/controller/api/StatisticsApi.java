package net.medrag.account_service.controller.api;

import net.medrag.account_service.model.Statistics;
import org.springframework.http.ResponseEntity;

/**
 * Meant for providing request statistics to custom statistics service
 * {@author} Stanislav Tretyakov
 * 22.02.2020
 */
public interface StatisticsApi {

    /**
     * @return {@link Statistics} object
     */
    ResponseEntity<Statistics> getStats();
}
