package com.kopytsia.translationapi3.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service
public class GoogleTranslateService {

    @Value("${google.api.key}")
    private String apiKey;

    private static final String TRANSLATE_API_URL = "https://translation.googleapis.com/language/translate/v2";

    private final RestTemplate restTemplate;

    public GoogleTranslateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String translateText(String text, String sourceLang, String targetLang) {
        if (text == null || text.trim().isEmpty()) {
            return "Текст для перекладу не може бути порожнім";
        }

        String url = UriComponentsBuilder.fromHttpUrl(TRANSLATE_API_URL)
                .queryParam("q", text)
                .queryParam("source", sourceLang)
                .queryParam("target", targetLang)
                .queryParam("key", apiKey)
                .toUriString();
        System.out.println("Request URL: " + url);

        try {
            GoogleTranslateResponse response = restTemplate.getForObject(url, GoogleTranslateResponse.class);

            if (response != null && response.getData() != null && !response.getData().getTranslations().isEmpty()) {
                // Декодуємо текст безпосередньо в сервісі
                String translatedText = response.getData().getTranslations().get(0).getTranslatedText();
                translatedText = URLDecoder.decode(translatedText, StandardCharsets.UTF_8);
                return translatedText;
            } else {
                return "Переклад не вдалося здійснити. Спробуйте пізніше.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Помилка при перекладі. Перевірте з'єднання.";
        }
    }
}
