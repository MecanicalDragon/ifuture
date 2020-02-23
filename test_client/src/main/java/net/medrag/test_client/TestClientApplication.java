package net.medrag.test_client;

import net.medrag.test_client.service.api.ProcessorApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * '--arg=value' in console or
 * Edit Configurations -> Spring boot -> Override parameters -> arg
 */
@SpringBootApplication
public class TestClientApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(TestClientApplication.class, args);
    }

    @Autowired
    private ProcessorApi processor;

    @Autowired
    private ApplicationContext ctx;

    @Override
    public void run(ApplicationArguments args) {
        processor.process();
        SpringApplication.exit(ctx);
    }
}
