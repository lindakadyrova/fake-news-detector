package com.kadyrova.fakenewsdetector.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import com.kadyrova.fakenewsdetector.BuildConfig


class NewsViewModel : ViewModel() {

    private val _analysisResult = MutableStateFlow<String?>(null)
    val analysisResult: StateFlow<String?> = _analysisResult

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val client = OkHttpClient()
    val apiKey = BuildConfig.API_KEY

    fun analyzeText(text: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _analysisResult.value = null
            try {
                _analysisResult.value = callGroq(text)
            } catch (e: Exception) {
                _analysisResult.value = "❌ Fehler: ${e.javaClass.simpleName}: ${e.message}"
            }
            _isLoading.value = false
        }
    }

    private suspend fun callGroq(text: String): String = withContext(Dispatchers.IO) {
        val prompt = """
            Analysiere den Text ob er fake oder echt ist und gib folgende emojis einfach zurück mit einem kurzen wort verdächtig sehr warhscheinlich falsch oder sehr wahrscheinlich richtig, wenn glaubwürdig, ⚠️ wenn verdächtig, ❌ wenn wahrscheinlich falsch. Gib keinen Grund an, sondern gib nur einen kurzen satz
            
            Text: $text
        """.trimIndent()

        val body = JSONObject()
            .put("model", "llama-3.1-8b-instant")
            .put("messages", org.json.JSONArray()
                .put(JSONObject()
                    .put("role", "user")
                    .put("content", prompt)))
            .toString()

        val request = Request.Builder()
            .url("https://api.groq.com/openai/v1/chat/completions")
            .post(body.toRequestBody("application/json".toMediaType()))
            .addHeader("Authorization", "Bearer $apiKey")
            .addHeader("Content-Type", "application/json")
            .build()

        val response = client.newCall(request).execute()
        val responseBody = response.body!!.string()
        android.util.Log.d("GROQ", responseBody)

        val json = JSONObject(responseBody)

        if (json.has("error")) {
            val msg = json.getJSONObject("error").getString("message")
            throw Exception(msg)
        }

        json.getJSONArray("choices")
            .getJSONObject(0)
            .getJSONObject("message")
            .getString("content")
    }
}