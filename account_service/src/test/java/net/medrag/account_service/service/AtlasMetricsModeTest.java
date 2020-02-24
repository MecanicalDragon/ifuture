package net.medrag.account_service.service;

import io.micrometer.core.instrument.Counter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:test_atlas.properties")
class AtlasMetricsModeTest {

    @TestConfiguration
    @ComponentScan("net.medrag")
    static class TestConfig {
    }

    @Mock
    private Counter getCounter;

    @Mock
    private Counter addCounter;

    @InjectMocks
    @Autowired
    private AtlasMetricsMode atlas;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Checks if increment method was really executed on getCounter field
     */
    @Test
    void increaseGetAmountCount() {
        atlas.increaseGetAmountCount();
        verify(getCounter, only()).increment();
        verify(addCounter, never()).increment();
    }

    /**
     * Checks if increment method was really executed on addCounter field
     */
    @Test
    void increaseAddAmountCount() {
        atlas.increaseAddAmountCount();
        verify(addCounter, only()).increment();
        verify(getCounter, never()).increment();
    }
}