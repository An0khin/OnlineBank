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
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringConfig.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(value = {"/create_accounts_before.sql", "/create_passports_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create_debit_cards_after.sql", "/create_passports_after.sql", "/create_accounts_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@WithUserDetails("vinograde@gmail.com")
public class AccountControllerTest {
    @Autowired
    MockMvc mockMvc;

    private Map<String, String> generatePassportData() {
        Map<String, String> passport = new HashMap<>();
        passport.put("name", "me");
        passport.put("surname", "master");
        passport.put("dateBirth", "2000-06-23");
        passport.put("series", "1234");
        passport.put("number", "111111");

        return passport;
    }

    private Map<String, String> generateAccountData() {
        Map<String, String> account = new HashMap<>();
        account.put("login", "aaaa");
        account.put("password", "aaaaaaaa");
        account.put("role", "USER");
        return account;
    }

    @SafeVarargs
    private MultiValueMap<String, String> generateParams(Map<String, String>... maps) {
        Map<String, List<String>> paramsMap = new HashMap<>();

        for(Map<String, String> map : maps) {
            map.forEach((k, v) -> {
                List<String> values = new ArrayList<>();
                values.add(v);
                paramsMap.put(k, values);
            });
        }

        return new MultiValueMapAdapter<>(paramsMap);
    }

    @Test
    public void registerNewAccountSuccess() throws Exception {
        Map<String, String> account = generateAccountData();
        Map<String, String> passport = generatePassportData();

        MultiValueMap<String, String> params = generateParams(account, passport);

        mockMvc.perform(MockMvcRequestBuilders.post("/register").params(params))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }

    @Test
    @WithUserDetails("user")
    public void registerNewAccountNoAccess() throws Exception {
        Map<String, String> account = generateAccountData();
        Map<String, String> passport = generatePassportData();

        MultiValueMap<String, String> params = generateParams(account, passport);

        mockMvc.perform(MockMvcRequestBuilders.post("/register").params(params))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithUserDetails("user")
    public void deleteAccountSuccess() throws Exception {
        String password = "user";

        mockMvc.perform(MockMvcRequestBuilders.post("/delete").param("text", password).param("flag", "true"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/logout"));
    }

    @Test
    @WithUserDetails("user")
    public void deleteAccountDenied() throws Exception {
        String password = "uncorrected";
        boolean agree = true;

        mockMvc.perform(MockMvcRequestBuilders.post("/delete").param("text", password).param("flag", "true"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().hasErrors());
    }
}