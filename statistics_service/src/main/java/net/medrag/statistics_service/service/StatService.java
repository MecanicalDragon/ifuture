package net.medrag.statistics_service.service;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.medrag.statistics_service.dao.AddAmountRequestStatisticsSnapshotRepository;
import net.medrag.statistics_service.dao.GetAmountRequestStatisticsSnapshotRepository;
import net.medrag.statistics_service.model.AddAmountRequestStatisticsSnapshot;
import net.medrag.statistics_service.model.GetAmountRequestStatisticsSnapshot;
import net.medrag.statistics_service.model.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Does scheduled statistics collecting job
 * {@author} Stanislav Tretyakov
 * 22.02.2020
 */
@Slf4j
@Service
public class StatService {

    private RestTemplate restTemplate;
    private AddAmountRequestStatisticsSnapshotRepository addRepository;
    private GetAmountRequestStatisticsSnapshotRepository getRepository;

    @Value("${statistics.collecting.target}")
    private String accountServiceUrl;

    private AtomicInteger totalGetAmountEndpointRequestsCount = new AtomicInteger(0);
    private AtomicInteger totalAddAmountEndpointRequestsCount = new AtomicInteger(0);
    private ZonedDateTime lastUpdateTimestamp = ZonedDateTime.now(ZoneOffset.UTC);

    @Autowired
    public StatService(
            AddAmountRequestStatisticsSnapshotRepository addRepository,
            GetAmountRequestStatisticsSnapshotRepository getRepository,
            RestTemplateBuilder restTemplateBuilder) {
        this.addRepository = addRepository;
        this.getRepository = getRepository;
        this.restTemplate = restTemplateBuilder.build();
    }

    /**
     * Task requests AccountService for statistics and stores it in the database every scheduled time.
     */
    @Scheduled(cron = "${statistics.collecting.cron}")
    public void collectStatistics() {
        log.info("Starting statistics collecting job...");
        Statistics statistics = restTemplate.getForObject(accountServiceUrl + "/getStatistics", Statistics.class);
        if (statistics != null) {
            val now = ZonedDateTime.now(ZoneOffset.UTC);
            val totalGet = totalGetAmountEndpointRequestsCount.addAndGet(statistics.getGetAmountRequestQuantity());
            val totalAdd = totalAddAmountEndpointRequestsCount.addAndGet(statistics.getAddAmountRequestQuantity());

            val newGetAmountSnapshot = new GetAmountRequestStatisticsSnapshot(lastUpdateTimestamp, now,
                    statistics.getGetAmountRequestQuantity(), totalGet);

            val newAddAmountSnapshot = new AddAmountRequestStatisticsSnapshot(lastUpdateTimestamp, now,
                    statistics.getAddAmountRequestQuantity(), totalAdd);

            try {
                getRepository.save(newGetAmountSnapshot);
                addRepository.save(newAddAmountSnapshot);
                log.info("Statistics have been stored in the database successfully.");
            } catch (DataAccessException e) {
                log.error("Statistics for last time period have not been recorded because of DataAccessException: {}", e.getMessage());
                e.printStackTrace();
            }
            lastUpdateTimestamp = now;
        }
    }

    @Transactional
    public void resetStatistics() {
        getRepository.deleteAll();
        addRepository.deleteAll();
        totalGetAmountEndpointRequestsCount.set(0);
        totalAddAmountEndpointRequestsCount.set(0);
        lastUpdateTimestamp = ZonedDateTime.now(ZoneOffset.UTC);
    }

    public Page<GetAmountRequestStatisticsSnapshot> getLastGetAmountStatsRecords(int amount) {
        Pageable page = PageRequest.of(0, amount);
        return getRepository.findAllByEndBeforeOrderByIdDesc(ZonedDateTime.now(ZoneOffset.UTC), page);
    }

    public Page<AddAmountRequestStatisticsSnapshot> getLastAddAmountStatsRecords(int amount) {
        Pageable page = PageRequest.of(0, amount);
        return addRepository.findAllByEndBeforeOrderByIdDesc(ZonedDateTime.now(ZoneOffset.UTC), page);
    }
}
