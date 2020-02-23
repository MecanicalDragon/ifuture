package net.medrag.account_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@author} Stanislav Tretyakov
 * 22.02.2020
 */
@Slf4j
@Deprecated
@Service
public class CustomMetricsService {

//    private JmsTemplate jmsTemplate;

//    @Autowired
//    public MetricsService(JmsTemplate jmsTemplate) {
//        this.jmsTemplate = jmsTemplate;
//    }

//    @Value("${account.service.statistics.period}")
//    private static Integer period;
//
//    @Value("${account.service.statistics.queue.name}")
//    private static String queueName;

    private AtomicBoolean statisticsCollectionInProcess = new AtomicBoolean(false);

    private AtomicInteger totalGetAmountEndpointRequestsCount = new AtomicInteger(0);
    private AtomicInteger totalAddAmountEndpointRequestsCount = new AtomicInteger(0);

    private AtomicInteger lastGetAmountEndpointRequestsCount = new AtomicInteger(0);
    private AtomicInteger lastAddAmountEndpointRequestsCount = new AtomicInteger(0);

//    private LocalDateTime bottomBounds = LocalDateTime.now().withSecond(0).withNano(0);
//    private LocalDateTime upperBounds = bottomBounds.plusSeconds(period);

    public void increaseGetAmountCount() {
//        if (LocalDateTime.now().isAfter(upperBounds)) {
//            if (statisticsCollectionInProcess.compareAndSet(false, true)) {
//                collectStatistics();
//                statisticsCollectionInProcess.compareAndSet(true, false);
//
//            }
//        }
        lastGetAmountEndpointRequestsCount.incrementAndGet();
    }

    public void increaseAddAmountCount() {
//        if (LocalDateTime.now().isAfter(upperBounds)) {
//            if (statisticsCollectionInProcess.compareAndSet(false, true)) {
//                collectStatistics();
//                statisticsCollectionInProcess.compareAndSet(true, false);
//
//            }
//        }
        lastAddAmountEndpointRequestsCount.incrementAndGet();
    }

//    private void sendJms(Statistics statistics){
//        log.info("Sending statistics message...");
//        try {
//            String msg = new ObjectMapper().writeValueAsString(statistics);
//            jmsTemplate.send(queueName, mc -> mc.createObjectMessage(msg));
//            log.info("Message has been sent.");
//        } catch (JsonProcessingException e) {
//            log.error("Exception during statistics message sending: {}", e.getMessage());
//        }
//    }
//
//    private void collectStatistics() {
//
//        val now = LocalDateTime.now();
//        long duration = upperBounds.until(now, ChronoUnit.SECONDS);
//        long rate = duration / period;      //  how much time time periods there were no requests.
//
//        //  request quantity in current time period
//        val getCount = lastGetAmountEndpointRequestsCount.getAndSet(0);
//        val addCount = lastAddAmountEndpointRequestsCount.getAndSet(0);
//
//        val addList = new ArrayList<AddAmountRequestStatisticsSnapshot>();
//        val getList = new ArrayList<GetAmountRequestStatisticsSnapshot>();
//
//        //  if there were no requests any time periods
//        if (rate > 1) {
//
//            val formerTotalGet = totalGetAmountEndpointRequestsCount.get();
//            val formerTotalAdd = totalAddAmountEndpointRequestsCount.get();
//
//            // create blank periods for statistics
//            for (int i = 0; i < rate; i++) {
//                LocalDateTime formerStart = upperBounds.plusSeconds(i * period);
//                LocalDateTime formerEnd = upperBounds.plusSeconds(i * period + period);
//                addList.add(new AddAmountRequestStatisticsSnapshot(formerStart, formerEnd, 0, formerTotalAdd));
//                getList.add(new GetAmountRequestStatisticsSnapshot(formerStart, formerEnd, 0, formerTotalGet));
//            }
//        }
//
//        // handle last time period statistics
//
//        val start = bottomBounds;
//        val end = upperBounds;
//
//        bottomBounds = bottomBounds.plusSeconds(rate * period);
//        upperBounds = bottomBounds.plusSeconds(period);
//
//        val totalGet = totalGetAmountEndpointRequestsCount.addAndGet(getCount);
//        val totalAdd = totalAddAmountEndpointRequestsCount.addAndGet(addCount);
//
//        addList.add(new AddAmountRequestStatisticsSnapshot(start, end, addCount, totalAdd));
//        getList.add(new GetAmountRequestStatisticsSnapshot(start, end, getCount, totalGet));
//
//
//
//    }
//
//    public void resetStatistics() {
//        bottomBounds = LocalDateTime.now().withSecond(0).withNano(0);
//        upperBounds = bottomBounds.plusSeconds(period);
//        lastGetAmountEndpointRequestsCount.set(0);
//        totalGetAmountEndpointRequestsCount.set(0);
//        lastAddAmountEndpointRequestsCount.set(0);
//        totalAddAmountEndpointRequestsCount.set(0);
//    }
//
//    @PostConstruct
//    public void postConstruct() {
//        if (period < 1) period = 1;
//    }
}
