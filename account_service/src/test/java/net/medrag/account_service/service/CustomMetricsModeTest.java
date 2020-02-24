package net.medrag.account_service.service;

import net.medrag.account_service.model.Statistics;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Some more useless tests
 */
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
class CustomMetricsModeTest {

    @TestConfiguration
    @ComponentScan("net.medrag")
    static class TestConfig {
    }

    @Autowired
    private CustomMetricsMode mode;

    @Test
    void increaseGetAmountCount() throws NoSuchFieldException, IllegalAccessException {
        mode.increaseGetAmountCount();
        Class<? extends CustomMetricsMode> aClass = mode.getClass();
        Field getAmountField = aClass.getDeclaredField("getAmountEndpointRequestsCount");
        getAmountField.setAccessible(true);
        Integer count = ((AtomicInteger)getAmountField.get(mode)).get();
        assertEquals(1, count);
    }

    @Test
    void increaseAddAmountCount() throws NoSuchFieldException, IllegalAccessException {
        mode.increaseAddAmountCount();
        Class<? extends CustomMetricsMode> aClass = mode.getClass();
        Field addAmountField = aClass.getDeclaredField("addAmountEndpointRequestsCount");
        addAmountField.setAccessible(true);
        Integer count = ((AtomicInteger)addAmountField.get(mode)).get();
        assertEquals(1, count);
    }

    @Test
    void getStats() {
        Statistics stats = mode.getStats();
        assertNotNull(stats);
    }
}