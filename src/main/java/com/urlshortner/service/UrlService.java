package com.urlshortner.service;

import com.urlshortner.model.Url;
import com.urlshortner.repository.UrlRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlService {

    @Autowired
    private UrlRepository repository;

    public String generateShortCode() {
        return RandomStringUtils.randomAlphanumeric(6);
    }

    public String createShortUrl(String originalUrl, Integer userId, String customCode, String domain) {
        String code = (customCode != null && !customCode.isEmpty()) ? customCode : generateShortCode();

        if (repository.existsShortCode(code)) {
            throw new RuntimeException("Short code already exists");
        }

        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortCode(code);
        url.setCreatedBy(userId);

        repository.saveUrl(url);

        // return full short URL
        return  domain.endsWith("/") ? domain + code : domain + "/" + code;
    }

    public String getOriginalUrl(String shortCode) {
        return repository.findByShortCode(shortCode).getOriginalUrl();
    }
}
