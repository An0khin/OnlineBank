package com.home.web;


import com.home.configuration.SpringConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringConfig.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(
        value = {
                "/create_accounts_before.sql",
                "/create_passports_before.sql",
                "/create_debit_cards_before.sql",
                "/create_credit_cards_before.sql",
                "/create_savings_before.sql"
        },
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(
        value = {
                "/create_savings_after.sql",
                "/create_credit_cards_after.sql",
                "/create_debit_cards_after.sql",
                "/create_passports_after.sql",
                "/create_accounts_after.sql"
        },
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@WithUserDetails("user")
public class CardControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void orderNewDebitSuccess() throws Exception {
        String agree = "true";

        mockMvc.perform(MockMvcRequestBuilders.post("/order_new_debit").param("flag", agree))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }

    @Test
    public void orderNewSavingSuccess() throws Exception {
        String agree = "true";

        mockMvc.perform(MockMvcRequestBuilders.post("/order_new_saving").param("flag", agree))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }

    @Test
    public void orderNewCreditRequestSuccess() throws Exception {
        String desiredLimit = "10000";
        String percent = "10";

        mockMvc.perform(MockMvcRequestBuilders.post("/new_credit_request")
                        .param("desiredLimit", desiredLimit)
                        .param("percent", percent)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }

    @Test
    public void takeCreditSuccess() throws Exception {
        String money = "5000";
        String creditId = "1";

        mockMvc.perform(MockMvcRequestBuilders.post("/take_credit")
                        .param("number", money)
                        .param("text", creditId)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }

    @Test
    public void takeCreditFailed() throws Exception {
        String money = "15000";
        String creditId = "1";

        mockMvc.perform(MockMvcRequestBuilders.post("/take_credit")
                        .param("number", money)
                        .param("text", creditId)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().hasErrors());
    }
}
