package com.kadyrova.fakenewsdetector.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kadyrova.fakenewsdetector.viewmodel.NewsViewModel

@Composable
fun HomeScreen(newsViewModel: NewsViewModel = viewModel()) {
    val analysisResult by newsViewModel.analysisResult.collectAsState()
    val isLoading by newsViewModel.isLoading.collectAsState()

    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Fake News Detector",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("Text eingeben...") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 4,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { newsViewModel.analyzeText(inputText) },
            modifier = Modifier.fillMaxWidth(),
            enabled = inputText.isNotBlank() && !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text("Analysieren")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        analysisResult?.let { result ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = result,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Check mein Design")
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen()
    }
}