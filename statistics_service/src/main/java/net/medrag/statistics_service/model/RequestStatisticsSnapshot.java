package net.medrag.statistics_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * {@author} Stanislav Tretyakov
 * 22.02.2020
 */
@Data
@NoArgsConstructor
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class RequestStatisticsSnapshot {

    public RequestStatisticsSnapshot(ZonedDateTime start, ZonedDateTime end, Integer requestsAmount, Integer totalRequests) {
        this.start = start;
        this.end = end;
        this.requestsAmount = requestsAmount;
        this.totalRequests = totalRequests;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "stats_id_generator", sequenceName = "AMOUNT_SEQUENCE", allocationSize = 1)
    @GeneratedValue(generator = "stats_id_generator")
    @JsonIgnore
    private Long id;

    @Column(name = "START")
    private ZonedDateTime start;

    @Column(name = "END")
    private ZonedDateTime end;

    @Column(name = "LAST_AMOUNTS")
    private Integer requestsAmount;

    @Column(name = "TOTAL_AMOUNTS")
    private Integer totalRequests;
}
