package net.medrag.account_service.controller;

import lombok.val;
import net.medrag.account_service.config.AtlasMetricsConfig;
import net.medrag.account_service.controller.api.StatisticsApi;
import net.medrag.account_service.model.Statistics;
import net.medrag.account_service.service.api.MetricsCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Provides request statistics to statistics service
 * {@author} Stanislav Tretyakov
 * 22.02.2020
 */
@ConditionalOnMissingBean(AtlasMetricsConfig.class)
@RestController
public class StatisticsController implements StatisticsApi {

    private MetricsCollector metricsCollector;

    @Autowired
    public StatisticsController(MetricsCollector metricsCollector) {
        this.metricsCollector = metricsCollector;
    }

    @Override
    @GetMapping("/getStatistics")
    public ResponseEntity<Statistics> getStats() {
        val stats = metricsCollector.getStats();
        return ResponseEntity.ok(stats);
    }
}
