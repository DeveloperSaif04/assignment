package com.urlshortner.controller;

import com.urlshortner.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.io.IOException;

@Controller
public class UrlController {

    @Autowired
    private UrlService service;

    @PostMapping("/shorten")
    public String shortenUrl(@RequestParam String url,
                             @RequestParam(required = false) String custom,
                             @RequestParam(required = false, defaultValue = "http://localhost:9090") String domain,
                             HttpSession session,
                             Model model) {
        Integer userId = (Integer) session.getAttribute("userId");

        try {
            // Anonymous users cannot create custom codes
            if (custom != null && !custom.isEmpty() && userId == null) {
                throw new RuntimeException("Login required to use custom short code.");
            }

            String shortUrl = service.createShortUrl(url, userId, custom, domain);
            model.addAttribute("msg", "URL shortened successfully!");
            model.addAttribute("shortUrl", shortUrl);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "dashboard";
    }

    @GetMapping("/{code}")
    public void redirect(@PathVariable String code, HttpServletResponse response) throws IOException {
        String original = service.getOriginalUrl(code);
        response.sendRedirect(original);
    }
}
