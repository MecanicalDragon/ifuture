package net.medrag.statistics_service.service;

import net.medrag.statistics_service.model.AddAmountRequestStatisticsSnapshot;
import net.medrag.statistics_service.model.GetAmountRequestStatisticsSnapshot;
import org.springframework.data.domain.Page;

public interface StatisticsService {

    void resetStatistics();

    Page<GetAmountRequestStatisticsSnapshot> getLastGetAmountStatsRecords(int amount);

    Page<AddAmountRequestStatisticsSnapshot> getLastAddAmountStatsRecords(int amount);
}
