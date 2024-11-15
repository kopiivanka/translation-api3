package com.kopytsia.translationapi3.services;

import com.kopytsia.translationapi3.model.TranslationHistory;
import com.kopytsia.translationapi3.repository.TranslationHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TranslationHistoryService {

    @Autowired
    private TranslationHistoryRepository historyRepository;

    public List<TranslationHistory> getHistory() {
        return historyRepository.findAll();
    }

    public void addTranslation(TranslationHistory translationHistory) {
        historyRepository.save(translationHistory);
    }
}
