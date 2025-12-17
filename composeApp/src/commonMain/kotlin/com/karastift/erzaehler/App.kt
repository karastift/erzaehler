package com.karastift.erzaehler

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.karastift.erzaehler.story.StoryScreen
import com.karastift.erzaehler.story.mockStoryJson
import com.karastift.erzaehler.theme.ErzaehlerTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    ErzaehlerTheme {

        // Current screen shown (maybe switch to real navigation later)
        var screen by remember { mutableStateOf("home") }

        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .safeContentPadding()
                .fillMaxSize()
        ) {
            when (screen) {
                "home" -> HomeScreen(
                    onStartStory = { screen = "story" }
                )
                "story" -> StoryScreen(
                    story = mockStoryJson,
                    onBack = { screen = "home" }  // Return to home screen
                )
            }
        }
    }
}

@Composable
fun HomeScreen(
    onStartStory: () -> Unit
) {
    // State for text field input
    var textInput by remember { mutableStateOf("") }

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
                    value = textInput,
                    onValueChange = { textInput = it },
                    label = { Text("I'm thinking of...") },
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = { /* TODO: Auto generate storyi */ }
                ) {
                    Icon(
                        imageVector = Icons.Filled.AutoAwesome,
                        contentDescription = "Generate Story"
                    )
                }
            }

            Button(
                onClick = onStartStory, // MockStory for now, later generated story
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text("tell me about it")
            }
        }
    }
}