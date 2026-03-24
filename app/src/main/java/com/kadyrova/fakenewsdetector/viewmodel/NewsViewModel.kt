package com.kadyrova.fakenewsdetector.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NewsViewModel : ViewModel() {

    private val _analysisResult = MutableStateFlow<String?>(null)
    val analysisResult: StateFlow<String?> = _analysisResult

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun analyzeText(text: String) {
        _isLoading.value = true

        val lowerText = text.lowercase()

        val result = when {
            lowerText.contains("fake") ||
                    lowerText.contains("lüge") ||
                    lowerText.contains("betrug") -> "⚠️ Wahrscheinlich Fake News"

            lowerText.contains("studie") ||
                    lowerText.contains("quelle") ||
                    lowerText.contains("forscher") -> "✅ Klingt glaubwürdig"

            else -> "🤔 Nicht eindeutig – Quellen prüfen"
        }

        _analysisResult.value = result
        _isLoading.value = false
    }
}