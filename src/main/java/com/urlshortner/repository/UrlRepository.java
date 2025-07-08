package com.urlshortner.repository;

import com.urlshortner.model.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UrlRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveUrl(Url url) {
        String sql = "INSERT INTO urls (original_url, short_code, created_by) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, url.getOriginalUrl(), url.getShortCode(), url.getCreatedBy());
    }

    public Url findByShortCode(String shortCode) {
        String sql = "SELECT * FROM urls WHERE short_code = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{shortCode}, new BeanPropertyRowMapper<>(Url.class));
    }

    public boolean existsShortCode(String shortCode) {
        String sql = "SELECT COUNT(*) FROM urls WHERE short_code = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, shortCode);
        return count != null && count > 0;
    }
}
