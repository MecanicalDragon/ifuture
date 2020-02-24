package net.medrag.account_service.service;

import net.medrag.account_service.dao.AccountRepository;
import net.medrag.account_service.model.AccountData;
import net.medrag.account_service.model.CustomDataAccessException;
import net.medrag.account_service.service.api.AccountCachingService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
class AccountCachingServiceImplTest {

    @TestConfiguration
    @ComponentScan("net.medrag")
    static class TestConfig {
    }

    @Autowired
    private AccountCachingService service;

    @MockBean
    private AccountRepository accountRepository;

    @AfterEach
    public void tearDown(){
        service.evictCache(5);
    }

    /**
     * {@link AccountRepository} is supposed to call one time
     */
    @Test
    void getAmount() {

        when(accountRepository.getAmount(anyInt())).thenReturn(Optional.of(123L));
        Long amount = service.getAmount(5);
        verify(accountRepository, only()).getAmount(5);
        assertEquals(123L, amount);
    }

    /**
     * Sole {@link AccountRepository} request expected
     */
    @Test
    void getAmountCaching() {

        when(accountRepository.getAmount(anyInt())).thenReturn(Optional.of(123L));
        Long amount = service.getAmount(5);
        Long amount2 = service.getAmount(5);
        Long amount3 = service.getAmount(5);
        verify(accountRepository, only()).getAmount(5);
        assertEquals(123L, amount);
        assertEquals(123L, amount2);
        assertEquals(123L, amount3);
    }

    /**
     * {@link CustomDataAccessException} expected
     */
    @Test
    void getAmountException() {

        when(accountRepository.getAmount(anyInt())).thenThrow(QueryTimeoutException.class);
        assertThrows(CustomDataAccessException.class, () -> {
            service.getAmount(5);
        });
        verify(accountRepository, only()).getAmount(5);
    }

    /**
     * Testing adding amount to exiting id
     */
    @Test
    void addAmountExisting() {
        when(accountRepository.getAmount(anyInt())).thenReturn(Optional.of(123L));
        doNothing().when(accountRepository).addAmount(anyInt(), anyLong());
        service.addAmount(5, 321L);
        verify(accountRepository, times(1)).getAmount(5);
        verify(accountRepository, times(1)).addAmount(5, 321L);
        verify(accountRepository, never()).save(any(AccountData.class));
    }

    /**
     * Testing creating new id
     */
    @Test
    void addAmountNew() {
        when(accountRepository.getAmount(anyInt())).thenReturn(Optional.empty());
        service.addAmount(5, 321L);
        verify(accountRepository, times(1)).getAmount(5);
        verify(accountRepository, never()).addAmount(5, 321L);
        verify(accountRepository, times(1)).save(any(AccountData.class));
    }

    /**
     * Testing exception
     */
    @Test
    void addAmountException() {
        when(accountRepository.getAmount(anyInt())).thenThrow(QueryTimeoutException.class);
        assertThrows(CustomDataAccessException.class, () -> {
            service.addAmount(5, 400L);
        });
        verify(accountRepository, only()).getAmount(5);
    }

    /**
     * Testing caching with new id
     */
    @Test
    void addAmountCachingNew() {
        when(accountRepository.getAmount(anyInt())).thenReturn(Optional.empty());
        service.addAmount(5, 321L);
        Long amount = service.getAmount(5);
        assertEquals(321L, amount);
        verify(accountRepository, times(1)).getAmount(5);
        verify(accountRepository, never()).addAmount(5, 321L);
        verify(accountRepository, times(1)).save(any(AccountData.class));
    }

    /**
     * Testing caching with existing id
     */
    @Test
    void addAmountCachingExisting() {
        when(accountRepository.getAmount(anyInt())).thenReturn(Optional.of(123L));
        service.addAmount(5, 321L);
        Long amount = service.getAmount(5);
        assertEquals(444L, amount);
        verify(accountRepository, times(1)).getAmount(5);
        verify(accountRepository, times(1)).addAmount(5, 321L);
        verify(accountRepository, never()).save(any(AccountData.class));
    }

    /**
     * Expecting second repository call after cache evicting
     */
    @Test
    void evictCache() {
        when(accountRepository.getAmount(anyInt())).thenReturn(Optional.of(123L));
        Long amount = service.getAmount(5);
        service.evictCache(5);
        Long amount2 = service.getAmount(5);
        verify(accountRepository, times(2)).getAmount(5);
        assertEquals(123L, amount);
        assertEquals(123L, amount2);
    }
}