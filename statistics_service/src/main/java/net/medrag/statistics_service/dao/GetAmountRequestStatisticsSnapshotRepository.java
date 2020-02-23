package net.medrag.statistics_service.dao;

import net.medrag.statistics_service.model.GetAmountRequestStatisticsSnapshot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * {@author} Stanislav Tretyakov
 * 22.02.2020
 */
@Repository
public interface GetAmountRequestStatisticsSnapshotRepository extends CrudRepository<GetAmountRequestStatisticsSnapshot, Long> {
    Page<GetAmountRequestStatisticsSnapshot> findAllByEndBeforeOrderByIdDesc(ZonedDateTime now, Pageable pageable);
}
