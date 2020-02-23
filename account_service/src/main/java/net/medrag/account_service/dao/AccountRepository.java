package net.medrag.account_service.dao;

import net.medrag.account_service.model.AccountData;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Account persistence layer
 * {@author} Stanislav Tretyakov
 * 22.02.2020
 */
@Repository
public interface AccountRepository extends CrudRepository<AccountData, Integer> {

    @Query("select a.amount from AccountData a where a.id = ?1")
    Optional<Long> getAmount(Integer id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update AccountData a set a.amount = a.amount + ?2 where a.id = ?1")
    void addAmount(Integer id, Long amount);

}
