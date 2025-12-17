package com.karastift.erzaehler.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun HomeScreen(
    topic: String,
    isLoading: Boolean,
    onTopicChange: (String) -> Unit,
    onGenerateTopic: () -> Unit,
    onTopicSubmit: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "erzaehler",
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = topic,
                        onValueChange = onTopicChange,
                        label = { Text("I'm thinking of ...") },
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(onClick = onGenerateTopic) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "Generate Story"
                        )
                    }
                }

                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    Button(
                        onClick = onTopicSubmit,
                        modifier = Modifier.fillMaxWidth(0.6f),
                        enabled = topic.isNotBlank()
                    ) {
                        Text("tell me about it!")
                    }
                }
            }
        }
    }
}

// Just to have preview in the editor
@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        topic = "Sample Topic",
        isLoading = false,
        onTopicChange = {},
        onGenerateTopic = {},
        onTopicSubmit = {}
    )
}