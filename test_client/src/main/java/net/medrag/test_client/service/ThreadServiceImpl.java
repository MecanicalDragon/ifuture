package net.medrag.test_client.service;

import lombok.val;
import net.medrag.test_client.service.api.ThreadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ThreadServiceImpl implements ThreadService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ThreadServiceImpl.class);

    @Value("${account.service.url}")
    private String accountServiceUrl;

    @Value("${idList}")
    private String idList = "1";

    private List<Integer> ids;

    private ThreadFactory threadFactory;

    private RestTemplate restTemplate;

    private Runnable getRequest;
    private Runnable addRequest;


    public ThreadServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.rootUri(accountServiceUrl).build();
        this.threadFactory = Thread::new;
    }

    public Thread doGetAmountRequest() {
        return threadFactory.newThread(getRequest);
    }

    public Thread doAddAmountRequest() {
        return threadFactory.newThread(addRequest);
    }

    /**
     * Method parses 'idList' property and fills list of ids, program will work with
     */
    @PostConstruct
    public void init() {
        try {
            this.ids = Arrays.stream(idList.split(","))
                    .flatMap(s -> {
                        if (s.contains("-")) {
                            String[] split = s.split("-");
                            List<Integer> list = new ArrayList<>();
                            for (int i = Integer.parseInt(split[0]); i <= Integer.parseInt(split[1]); i++)
                                list.add(i);
                            return list.stream();
                        } else return Stream.of(Integer.parseInt(s));
                    })
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            System.err.println("Since NumberFormatException occurred all requests will be addressed to a single id = 1. Input data more intently!");
            ids = Collections.singletonList(1);
        }

        getRequest = () -> {
            try {
                val id = ids.get((int) (Math.random() * ids.size()));
                LOGGER.info("Sending getAmount request with id {} to AccountService...", id);
                val resp = restTemplate.getForObject(accountServiceUrl + "/amount/get/" + id, Integer.class);
                LOGGER.info("GetAmount request for id {} was successful. Response is {}", id, resp);
            } catch (Exception e) {
                LOGGER.error("Looks like connection refused: {}", e.getMessage());
            }
        };

        addRequest = () -> {
            try {
                val id = ids.get((int) (Math.random() * ids.size()));
                val amount = (int) (Math.random() * 1000);
                LOGGER.info("Sending addAmount request with id {} and amount {} to AccountService...", id, amount);
                restTemplate.postForLocation(String.format("%s/amount/add/%s/%s", accountServiceUrl, id, amount), null);
                LOGGER.info("AddAmount request for id {} was successful.", id);
            } catch (Exception e) {
                LOGGER.error("Looks like connection refused: {}", e.getMessage());
            }
        };

    }
}
