package com.urlshortner.controller;

import com.urlshortner.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        try {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            User user = jdbcTemplate.queryForObject(sql, new Object[]{username, password},
                    new BeanPropertyRowMapper<>(User.class));

            if (user != null) {
                session.setAttribute("userId", user.getId());
                session.setAttribute("username", user.getUsername());
                return "dashboard"; // make sure dashboard.jsp exists
            } else {
                model.addAttribute("error", "Invalid credentials");
                return "login";
            }

        } catch (Exception e) {
            model.addAttribute("error", "Invalid credentials or system error.");
            return "login";
        }
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           Model model) {
        try {
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            jdbcTemplate.update(sql, username, password);
            model.addAttribute("msg", "Registration successful! Please login.");
            return "login";
        } catch (Exception e) {
            model.addAttribute("error", "Username already exists or system error.");
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }
}
