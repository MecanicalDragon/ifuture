package net.medrag.test_client.service;

import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class Processor {

    private final static Logger LOGGER = LoggerFactory.getLogger(Processor.class);

    @Value("${rCount}")
    private Integer rCount = 1;

    @Value("${wCount}")
    private Integer wCount = 1;

    @Value("${idList}")
    private String idList = "1";

    @Value("${account.service.url}")
    private String accountServiceUrl;

    private List<Integer> ids;

    private AtomicBoolean processing = new AtomicBoolean(true);

    private RestTemplate restTemplate;

    public Processor(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.rootUri(accountServiceUrl).build();
    }

    public void process() {
        try {
            this.ids = Arrays.stream(idList.split(",")).flatMap(
                    s -> {
                        if (s.contains("-")) {
                            String[] split = s.split("-");
                            List<Integer> list = new ArrayList<>();
                            for (int i = Integer.parseInt(split[0]); i <= Integer.parseInt(split[1]); i++) {
                                list.add(i);
                            }
                            return list.stream();
                        } else return Stream.of(Integer.parseInt(s));
                    }
            ).collect(Collectors.toList());
        } catch (NumberFormatException e) {
            System.err.println("NumberFormatException occurred! Application will be closed! Input data more intently!");
            throw new ClosingContextException();
        }
        System.out.println(ids);

        val readService = Executors.newFixedThreadPool(rCount);
        val writeService = Executors.newFixedThreadPool(wCount);

        for (int i = 0; i < rCount; i++) {
            readService.submit(() -> {
                int count = 0;
                while (processing.get()) {
                    try {
                        val id = ids.get((int) (Math.random() * ids.size()));
                        LOGGER.info("Sending getAmount request with id {} to AccountService (count = {})", id, count++);
                        val resp = restTemplate.getForObject("/amount/get/" + id, Integer.class);
                        LOGGER.info("GetAmount request for id {} was successful. Response is {}", id, resp);
                    } catch (Exception e) {
                        LOGGER.error("Looks like connection refused: {}", e.getMessage());
                    }
                }
            });
        }

        for (int i = 0; i < wCount; i++) {
            writeService.submit(() -> {
                int count = 0;
                while (processing.get()) {
                    try {
                        val id = ids.get((int) (Math.random() * ids.size()));
                        val amount = (int) (Math.random() * 1000);
                        LOGGER.info("Sending addAmount request with id {} and amount {} to AccountService (count = {})",
                                id, amount, count++);
                        val resp = restTemplate.getForObject(String.format("/amount/add/%s/%s", id, amount), Integer.class);
                        LOGGER.info("AddAmount request for id {} was successful.", id);
                    } catch (Exception e) {
                        LOGGER.error("Looks like connection refused: {}", e.getMessage());
                    }
                }
            });
        }

    }
}
