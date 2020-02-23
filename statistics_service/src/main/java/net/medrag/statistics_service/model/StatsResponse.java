package net.medrag.statistics_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@Getter
@AllArgsConstructor
public final class StatsResponse {
    private final List<GetAmountRequestStatisticsSnapshot> getStats;
    private final List<AddAmountRequestStatisticsSnapshot> addStats;
}
