package net.medrag.account_service.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import net.medrag.account_service.config.AtlasMetricsConfig;
import net.medrag.account_service.service.api.MetricsMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Atlas implementation of metrics collecting
 * <p>
 * To figure out with it you can use following links:
 * http://localhost:7101/api/v1/graph?q=0.1
 * https://www.baeldung.com/micrometer
 * https://docs.spring.io/spring-metrics/docs/current/public/atlas
 * https://cloud.spring.io/spring-cloud-netflix/multi/multi_netflix-metrics-atlas.html
 * https://github.com/Netflix/atlas/wiki/Graph
 * https://github.com/Netflix/atlas/wiki/Getting-Started
 * https://github.com/Netflix/atlas/wiki/Stack-Language
 * https://micrometer.io/docs/concepts#_naming_meters
 * <p>
 * {@author} Stanislav Tretyakov
 * 22.02.2020
 */
@ConditionalOnBean(AtlasMetricsConfig.class)
@Service
public class AtlasMetricsMode implements MetricsMode {

    private MeterRegistry meterRegistry;

    private Counter getCounter;
    private Counter addCounter;

//    private Timer getTimer;
//    private Timer addTimer;

//    private DistributionSummary getSummary;
//    private DistributionSummary addSummary;

    @Autowired
    public AtlasMetricsMode(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public void increaseGetAmountCount() {
        getCounter.increment();
    }

    @Override
    public void increaseAddAmountCount() {
        addCounter.increment();
    }

    @PostConstruct
    public void init() {

        getCounter = Counter
                .builder("getRequestsCounter")
                .description("Quantity of requests to AccountService getAmount method.")
                .tags("amount", "get")
                .register(meterRegistry);
        addCounter = Counter
                .builder("addRequestsCounter")
                .description("Quantity of requests to AccountService addAmount method.")
                .tags("amount", "add")
                .register(meterRegistry);

//        getTimer = Timer
//                .builder("getRequestsFrequency")
//                .description("Frequency of requests to AccountService getAmount method.")
//                .tags("amount", "get")
//                .register(meterRegistry);
//        addTimer = Timer
//                .builder("addRequestsFrequency")
//                .description("Frequency of requests to AccountService addAmount method.")
//                .tags("amount", "add")
//                .register(meterRegistry);

//        getSummary = DistributionSummary
//                .builder("getRequestsDistribution")
//                .description("stashes 'getAmount' requests information frequency")
//                .tags("amount", "get")
//                .register(meterRegistry);
//
//        addSummary = DistributionSummary
//                .builder("addRequestsDistribution")
//                .description("stashes 'addAmount' requests information frequency")
//                .tags("amount", "add")
//                .register(meterRegistry);
    }
}
