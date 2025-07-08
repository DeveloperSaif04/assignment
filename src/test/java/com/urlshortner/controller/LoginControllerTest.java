package com.urlshortner.controller;

import com.urlshortner.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;

@WebMvcTest(LoginController.class)
@Import(LoginControllerTest.MockConfig.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public JdbcTemplate jdbcTemplate() {
            return mock(JdbcTemplate.class);
        }
    }

    @Test
    public void testLogin_Success() throws Exception {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        User user = new User();
        user.setId(1);
        user.setUsername("saif");
        user.setPassword("123");

        when(jdbcTemplate().queryForObject(eq(sql), any(Object[].class), any(BeanPropertyRowMapper.class)))
                .thenReturn(user);

        mockMvc.perform(post("/login")
                        .param("username", "saif")
                        .param("password", "123"))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"));
    }

    // You can reuse same mockJdbcTemplate logic for other test methods

    private JdbcTemplate jdbcTemplate() {
        return mockMvc.getDispatcherServlet()
                .getWebApplicationContext()
                .getBean(JdbcTemplate.class);
    }
}
