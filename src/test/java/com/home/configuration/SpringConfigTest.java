package com.home.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.yml")
public class SpringConfigTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void loginDeniedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void loginAccessedTest() throws Exception {
        mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin().user("owner").password("owner"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }

    @Test
    public void badCredentials() throws Exception {
        mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin().user("username").password("pass"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login?error"));
    }
}