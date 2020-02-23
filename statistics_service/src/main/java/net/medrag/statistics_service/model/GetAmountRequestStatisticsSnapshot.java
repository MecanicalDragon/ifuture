package net.medrag.statistics_service.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.ZonedDateTime;

/**
 * {@author} Stanislav Tretyakov
 * 22.02.2020
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "GET_AMOUNT_STATISTICS")
public class GetAmountRequestStatisticsSnapshot extends RequestStatisticsSnapshot {

    public GetAmountRequestStatisticsSnapshot(ZonedDateTime start, ZonedDateTime end, Integer requestsAmount, Integer totalRequests) {
        super(start, end, requestsAmount, totalRequests);
    }
}
