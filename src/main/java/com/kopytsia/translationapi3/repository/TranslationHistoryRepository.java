package com.kopytsia.translationapi3.repository;

import com.kopytsia.translationapi3.model.TranslationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TranslationHistoryRepository extends JpaRepository<TranslationHistory, Long> {}
