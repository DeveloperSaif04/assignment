package com.urlshortner.service;

import com.urlshortner.model.Url;
import com.urlshortner.repository.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UrlServiceTest {

    @InjectMocks
    private UrlService urlService;

    @Mock
    private UrlRepository repository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateShortUrl_WithCustomCode_Success() {
        String originalUrl = "http://example.com";
        String customCode = "myCustom";
        String domain = "http://localhost:9090";
        Integer userId = 1;

        when(repository.existsShortCode(customCode)).thenReturn(false);

        String shortUrl = urlService.createShortUrl(originalUrl, userId, customCode, domain);

        assertEquals("http://localhost:9090/myCustom", shortUrl);
        verify(repository).saveUrl(any(Url.class));
    }

    @Test
    public void testCreateShortUrl_WithRandomCode_Success() {
        String originalUrl = "http://example.com";
        String domain = "http://localhost:9090";

        // Assume random code is generated
        when(repository.existsShortCode(anyString())).thenReturn(false);

        String shortUrl = urlService.createShortUrl(originalUrl, null, null, domain);

        assertTrue(shortUrl.startsWith("http://localhost:9090/"));
        verify(repository).saveUrl(any(Url.class));
    }

    @Test
    public void testCreateShortUrl_CodeAlreadyExists_ShouldThrow() {
        String originalUrl = "http://example.com";
        String customCode = "taken";
        String domain = "http://localhost:9090";

        when(repository.existsShortCode(customCode)).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                urlService.createShortUrl(originalUrl, null, customCode, domain)
        );

        assertEquals("Short code already exists", ex.getMessage());
    }

    @Test
    public void testGetOriginalUrl_Success() {
        String code = "abc123";
        Url mockUrl = new Url();
        mockUrl.setOriginalUrl("http://google.com");

        when(repository.findByShortCode(code)).thenReturn(mockUrl);

        String original = urlService.getOriginalUrl(code);
        assertEquals("http://google.com", original);
    }
}

