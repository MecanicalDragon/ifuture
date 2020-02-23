package net.medrag.account_service.config;

import com.netflix.spectator.atlas.AtlasConfig;
import io.micrometer.atlas.AtlasMeterRegistry;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Atlas configuration. Loaded only if 'account.service.metrics.mode' property is set on 'atlas'
 * Download Atlas server with a link below:
 * https://github.com/Netflix/atlas/releases/download/v1.6.4/atlas-1.6.4-standalone.jar
 * {@author} Stanislav Tretyakov
 * 22.02.2020
 */
@ConditionalOnProperty(
        name="account.service.metrics.mode",
        havingValue = "atlas")
@Configuration
@ComponentScan("net.medrag")
public class AtlasMetricsConfig {

    @Bean
    public MeterRegistry meterRegistry(){
        return new AtlasMeterRegistry(atlasConfig(), Clock.SYSTEM);
    }

    @Bean
    public AtlasConfig atlasConfig(){
        return new AtlasConfig() {
            @Override
            public Duration step() {
                return Duration.ofSeconds(60);
            }
            @Override
            public String get(String s) {
                return null;
            }
        };
    }
}