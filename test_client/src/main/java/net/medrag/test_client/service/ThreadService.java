package net.medrag.test_client.service;

import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ThreadService {

    @Value("${account.service.url}")
    private String accountServiceUrl;

    @Value("${idList}")
    private String idList = "1";

    private List<Integer> ids;

    private ThreadFactory threadFactory;

//    public ThreadService() {
//        this.threadFactory = new ThreadFactory() {
//
//            Runnable r = () -> {
//                try {
//                    val id = ids.get((int) (Math.random() * ids.size()));
//                    LOGGER.info("Sending getAmount request with id {} to AccountService (count = {})", id, count++);
//                    val resp = restTemplate.getForObject("/amount/get/" + id, Integer.class);
//                    LOGGER.info("GetAmount request for id {} was successful. Response is {}", id, resp);
//                } catch (Exception e) {
//                    LOGGER.error("Looks like connection refused: {}", e.getMessage());
//                }
//            }
//
//            @Override
//            public Thread newThread(Runnable r) {
//                return null;
//            }
//        }
//    }

    /**
     * Method parses 'idList' property and fills list of ids, program will work with
     */
    @PostConstruct
    public void init() {
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
            System.err.println("Since NumberFormatException occurred all requests will be addressed to a single id = 1. Input data more intently!");
            ids = Collections.singletonList(1);
        }
    }
}
