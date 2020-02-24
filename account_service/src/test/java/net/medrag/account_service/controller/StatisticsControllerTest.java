package net.medrag.account_service.controller;

import net.medrag.account_service.AccountServiceApp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountServiceApp.class)
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
class StatisticsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void getStats() throws Exception {
        this.mvc.perform(get("/getStatistics"))
//                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.getAmountRequestQuantity").exists())
                .andExpect(jsonPath("$.addAmountRequestQuantity").exists());
    }
}