package net.medrag.account_service.controller;

import net.medrag.account_service.AccountServiceApp;
import net.medrag.account_service.dao.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountServiceApp.class)
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
class AdviceControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    AccountRepository repository;

    /**
     * Testing exception handling
     */
    @Test
    void handleDataAccessException() throws Exception {
        when(repository.getAmount(anyInt())).thenThrow(QueryTimeoutException.class);
        this.mvc.perform(get("/amount/get/42"))
                .andExpect(status().is(555));
    }
}