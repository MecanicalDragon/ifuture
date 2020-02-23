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
@Entity(name = "ADD_AMOUNT_STATISTICS")
public class AddAmountRequestStatisticsSnapshot extends RequestStatisticsSnapshot {

    public AddAmountRequestStatisticsSnapshot(ZonedDateTime start, ZonedDateTime end, Integer requestsAmount, Integer totalRequests) {
        super(start, end, requestsAmount, totalRequests);
    }
}
