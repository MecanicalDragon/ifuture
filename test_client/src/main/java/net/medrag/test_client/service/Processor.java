package net.medrag.test_client.service;

import net.medrag.test_client.service.api.ProcessorApi;
import net.medrag.test_client.service.api.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class Processor implements ProcessorApi {

    @Value("${rCount}")
    private Integer rCount = 1;

    @Value("${wCount}")
    private Integer wCount = 1;

    private AtomicBoolean processing = new AtomicBoolean(true);

    private ThreadService threadService;

    @Autowired
    public Processor(ThreadService threadService) {
        this.threadService = threadService;
    }

    public void process() {

        ExecutorService readService = Executors.newFixedThreadPool(rCount);
        ExecutorService writeService = Executors.newFixedThreadPool(wCount);

        while (processing.get()) {
            readService.submit(threadService.doGetAmountRequest());
            writeService.submit(threadService.doAddAmountRequest());
        }
        readService.shutdownNow();
        writeService.shutdownNow();
    }

    public void stop() {    //  Hammertime!
        processing.compareAndSet(true, false);
    }
}
