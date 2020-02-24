package net.medrag.account_service.controller;

import net.medrag.account_service.controller.api.AccountService;
import net.medrag.account_service.service.api.AccountCachingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * {@author} Stanislav Tretyakov
 * 22.02.2020
 */
@RestController
@RequestMapping("/amount")
public class AccountServiceImpl implements AccountService {

    private AccountCachingService accountCachingService;

    @Autowired
    public AccountServiceImpl(AccountCachingService accountCachingService) {
        this.accountCachingService = accountCachingService;
    }

    @Override
    @GetMapping("/get/{id}")
    public Long getAmount(@PathVariable(value = "id") Integer id) {
        return accountCachingService.getAmount(id);
    }

    @Override
    @PostMapping("/add/{id}/{value}")
    public void addAmount(@PathVariable(value = "id") Integer id, @PathVariable(value = "value") Long value) {
        accountCachingService.addAmount(id, value);
    }
}
