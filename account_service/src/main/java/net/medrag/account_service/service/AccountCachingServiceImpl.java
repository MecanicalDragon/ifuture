package net.medrag.account_service.service;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.medrag.account_service.dao.AccountRepository;
import net.medrag.account_service.model.AccountData;
import net.medrag.account_service.model.CustomDataAccessException;
import net.medrag.account_service.service.api.AccountCachingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@author} Stanislav Tretyakov
 * 22.02.2020
 */
@Slf4j
@Service
public class AccountCachingServiceImpl implements AccountCachingService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountCachingServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Cacheable(value = "amount")
    public Long getAmount(Integer id) {
        try {
            log.debug("Amount has been requested for account with id {}", id);
            return accountRepository.getAmount(id).orElse(0L);
        } catch (DataAccessException e) {
            log.error("Exception occurred during amount retrieving operation: {}", e.getMessage());
            throw new CustomDataAccessException(e.getMessage(), e);
        }
    }

    @Override
    @CachePut(value = "amount", key = "#p0")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Long addAmount(Integer id, Long value) {
        log.debug("New amount adding request for id {} has come.", id);
        try {
            val amount = accountRepository.getAmount(id);
            if (amount.isPresent()) {
                val total = value + amount.get();
                log.debug("Amount of {} is gonna be added to account with id {}.", value, id);
                accountRepository.addAmount(id, value);
                log.debug("Adding transaction for account with id {} was successful. New amount value is {}", id, total);
                return total;
            } else {
                log.debug("There is no account with id {} in the database. The new one will be created.", id);
                accountRepository.save(new AccountData(id, value));
                log.info("New account with id {} has been created. Initial amount value is {}.", id, value);
            }
            return value;
        } catch (DataAccessException e) {
            log.error("Exception occurred during amount summing operation: {}", e.getMessage());
            throw new CustomDataAccessException(e.getMessage(), e);
        }
    }

    @CacheEvict("amount")
    public void evictCache(Integer id) {
        log.info("Evicting cached amount for id {}.", id);
    }
}
