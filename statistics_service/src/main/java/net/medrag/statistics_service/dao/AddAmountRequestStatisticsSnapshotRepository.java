package net.medrag.statistics_service.dao;

import net.medrag.statistics_service.model.AddAmountRequestStatisticsSnapshot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;

/**
 * {@author} Stanislav Tretyakov
 * 22.02.2020
 */
@Repository
public interface AddAmountRequestStatisticsSnapshotRepository extends CrudRepository<AddAmountRequestStatisticsSnapshot, Long> {
    Page<AddAmountRequestStatisticsSnapshot> findAllByEndBeforeOrderByIdDesc(ZonedDateTime now, Pageable pageable);

}
