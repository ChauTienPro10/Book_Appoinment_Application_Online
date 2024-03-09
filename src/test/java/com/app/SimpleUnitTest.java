package com.app;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.app.entites.Account;
import com.app.services.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class SimpleUnitTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    AccountService accountService;

    @Test
    public void testExampleEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/account/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk());
                
    }
    
    @Test
    public void testLoginByUsernameAndPassword() throws Exception {

        Map<String, String> jsonData = new HashMap<>();
        jsonData.put("username", "user");
        jsonData.put("password", "123");
        jsonData.put("role", "USER");


        Account mockAccount = new Account();
        mockAccount.setUsername("user");
        mockAccount.setPassword("123");
        Mockito.when(accountService.getAccountByUsernameAndPassword("user", "123")).thenReturn(mockAccount);


        mockMvc.perform(MockMvcRequestBuilders.post("/account/modelogin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(jsonData)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value("USER"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("user"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("123"));
    }
}