package net.medrag.account_service.controller;

import io.micrometer.core.annotation.Timed;
import net.medrag.account_service.controller.api.AccountService;
import net.medrag.account_service.service.api.AccountCachingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@author} Stanislav Tretyakov
 * 22.02.2020
 */
@Timed
@RestController
@RequestMapping("/amount")
public class AccountServiceImpl implements AccountService {

    private AccountCachingService accountCachingService;

    @Autowired
    public AccountServiceImpl(AccountCachingService accountCachingService) {
        this.accountCachingService = accountCachingService;
    }

    @Override
    @RequestMapping("/get/{id}")
    public Long getAmount(@PathVariable(value = "id") Integer id) {
        return accountCachingService.getAmount(id);
    }

    //TODO: make POST
    @Override
    @RequestMapping("/add/{id}/{value}")
    public void addAmount(@PathVariable(value = "id") Integer id, @PathVariable(value = "value") Long value) {
        accountCachingService.addAmount(id, value);
    }
}
