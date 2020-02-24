package net.medrag.account_service.service;

import io.micrometer.core.instrument.Counter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:test_atlas.properties")
public class AtlasMetricsModeTestInitMethod {

    @TestConfiguration
    @ComponentScan("net.medrag")
    static class TestConfig {
    }

    @Autowired
    private AtlasMetricsMode atlas;

    /**
     * This test just checks if getCounter and addCounter were initialized by init method during bean initialization
     */
    @Test
    void init() throws NoSuchFieldException, IllegalAccessException {

        System.out.println("powermock-module-junit5 required here");

        Class<? extends AtlasMetricsMode> atlasClass = atlas.getClass();

        Field getCounterField = atlasClass.getDeclaredField("getCounter");
        Field addCounterField = atlasClass.getDeclaredField("addCounter");

        getCounterField.setAccessible(true);
        addCounterField.setAccessible(true);

        Counter getCounter = (Counter) getCounterField.get(atlas);
        Counter addCounter = (Counter) addCounterField.get(atlas);

        assertNotNull(getCounter);
        assertNotNull(addCounter);
    }
}
