package net.medrag.account_service.service;

import lombok.extern.slf4j.Slf4j;
import net.medrag.account_service.config.AtlasMetricsConfig;
import net.medrag.account_service.model.Statistics;
import net.medrag.account_service.service.api.MetricsCollector;
import net.medrag.account_service.service.api.MetricsMode;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Custom implementation of metrics collection service
 * {@author} Stanislav Tretyakov
 * 22.02.2020
 */
@ConditionalOnMissingBean(AtlasMetricsConfig.class)
@Slf4j
@Service
public class CustomMetricsMode implements MetricsMode, MetricsCollector {

    private AtomicInteger getAmountEndpointRequestsCount = new AtomicInteger(0);
    private AtomicInteger addAmountEndpointRequestsCount = new AtomicInteger(0);

    @Override
    public void increaseGetAmountCount() {
        getAmountEndpointRequestsCount.incrementAndGet();
    }

    @Override
    public void increaseAddAmountCount() {
        addAmountEndpointRequestsCount.incrementAndGet();
    }

    @Override
    public Statistics getStats() {
        int getAmount = getAmountEndpointRequestsCount.getAndSet(0);
        int addAmount = addAmountEndpointRequestsCount.getAndSet(0);
        log.info("Statistics have been requested. Returned values {} as getAmount and {} as addAmount", getAmount, addAmount);
        return new Statistics(getAmount, addAmount);
    }
}
