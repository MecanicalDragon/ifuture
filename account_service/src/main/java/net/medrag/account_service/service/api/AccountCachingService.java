package net.medrag.account_service.service.api;

/**
 * Service for interaction with Account DAO layer
 * {@author} Stanislav Tretyakov
 * 22.02.2020
 */
public interface AccountCachingService {

    Long getAmount(Integer id);

    Long addAmount(Integer id, Long value);

    void evictCache(Integer id);
}
