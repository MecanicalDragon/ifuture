package net.medrag.account_service.controller.api;

/**
 * Requirement api
 * {@author} Stanislav Tretyakov
 * 22.02.2020
 */
public interface AccountService {
    /**
     * Retrieves current balance or zero if addAmount() method was not called before for specified id
     *
     * @param id balance identifier
     *               
     */
    Long getAmount(Integer id);

    /**
     * Increases balance or set if addAmount() method was called first time
     *
     * @param id    balance identifier
     * @param value positive or negative value, which must be added to current balance
     *                  
     */
    void addAmount(Integer id, Long value);
}
