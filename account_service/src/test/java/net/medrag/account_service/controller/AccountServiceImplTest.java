package net.medrag.account_service.controller;

import net.medrag.account_service.AccountServiceApp;
import net.medrag.account_service.dao.AccountRepository;
import net.medrag.account_service.model.AccountData;
import net.medrag.account_service.service.api.AccountCachingService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountServiceApp.class)
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
class AccountServiceImplTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    AccountRepository repository;

    @Autowired
    AccountCachingService service;

    @BeforeEach
    public void setUp(){
        repository.save(new AccountData(42, 42L));
    }

    @AfterEach
    public void tearDown(){
        repository.deleteAll();
        service.evictCache(42);
    }

    /**
     * Amount for specified id exists in database
     */
    @Test
    void getAmountExisting() throws Exception {
        this.mvc.perform(get("/amount/get/42"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(42));
    }

    /**
     * There is no amount for specified id in database
     */
    @Test
    void getAmountNullable() throws Exception {
        this.mvc.perform(get("/amount/get/43"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(0));
    }

    /**
     * Add amount to non-existing id and creating a new one
     */
    @Test
    void addAmountNew() throws Exception {
        this.mvc.perform(post("/amount/add/43/43"))
                .andExpect(status().isOk());
        this.mvc.perform(get("/amount/get/43"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(43));
    }

    /**
     * Adding amount to existing id
     */
    @Test
    void addAmountToExisting() throws Exception {
        this.mvc.perform(post("/amount/add/42/43"))
                .andExpect(status().isOk());
        this.mvc.perform(get("/amount/get/42"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(85));
    }
}