package net.medrag.test_client;

import net.medrag.test_client.service.ClosingContextException;
import net.medrag.test_client.service.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

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
    private Processor processor;

    @Autowired
    private ApplicationContext ctx;

    @Override
    public void run(ApplicationArguments args) {
        try {
            processor.process();
        } catch (ClosingContextException e) {
            e.printStackTrace();
            SpringApplication.exit(ctx);
        }
    }
}
