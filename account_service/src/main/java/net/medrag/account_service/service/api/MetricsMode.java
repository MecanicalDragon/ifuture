package net.medrag.account_service.service.api;

import net.medrag.account_service.filter.MetricFilter;

/**
 * Exists for injecting custom or Atlas implementation into {@link MetricFilter}
 * {@author} Stanislav Tretyakov
 * 22.02.2020
 */
public interface MetricsMode {
    void increaseGetAmountCount();
    void increaseAddAmountCount();
}
