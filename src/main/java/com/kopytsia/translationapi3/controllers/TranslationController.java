package com.kopytsia.translationapi3.controllers;

import com.kopytsia.translationapi3.services.GoogleTranslateService;
import com.kopytsia.translationapi3.services.TranslationHistoryService;
import com.kopytsia.translationapi3.model.TranslationHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TranslationController {

    @Autowired
    private GoogleTranslateService googleTranslateService;

    @Autowired
    private TranslationHistoryService translationHistoryService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("history", translationHistoryService.getHistory());
        return "index";
    }

    @PostMapping("/translate")
    public String translate(@RequestParam("text") String text,
                            @RequestParam("sourceLang") String sourceLang,
                            @RequestParam("targetLang") String targetLang,
                            Model model) {

        if (text == null || text.trim().isEmpty()) {
            model.addAttribute("error", "Будь ласка, введіть текст для перекладу.");
            return "index";
        }

        String translatedText = googleTranslateService.translateText(text, sourceLang, targetLang);
        System.out.println("Original Text: " + text);
        System.out.println("Translated Text from API: " + translatedText);

        if (translatedText == null || translatedText.isEmpty()) {
            model.addAttribute("error", "Переклад не вдалося отримати.");
            return "index";
        }

        translationHistoryService.addTranslation(new TranslationHistory(text, translatedText, sourceLang, targetLang));
        System.out.println("Translation added to history");

        model.addAttribute("originalText", text);
        model.addAttribute("translatedText", translatedText);
        model.addAttribute("history", translationHistoryService.getHistory());

        return "index";
    }
}
