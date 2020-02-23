package net.medrag.account_service.service.api;

import net.medrag.account_service.model.Statistics;

/**
 *
 * {@author} Stanislav Tretyakov
 * 22.02.2020
 */
public interface MetricsCollector {
    Statistics getStats();
}
