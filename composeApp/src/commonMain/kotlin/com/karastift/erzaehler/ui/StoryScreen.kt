package com.karastift.erzaehler.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.karastift.erzaehler.AnimatedCharacter
import com.karastift.erzaehler.character.characterFromId
import com.karastift.erzaehler.story.Story
import com.karastift.erzaehler.story.StoryRunner
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

private val jsonStory = Json {
    ignoreUnknownKeys = true
    classDiscriminator = "type" // Polymorphic deserialization based on type field in provided json
}

@Composable
fun StoryScreen(
    story: String,
    onBack: () -> Unit = {}
) {
    // Parse json into Story
    val parsedStory = remember {
        jsonStory.decodeFromString<Story>(story)
    }

    val runner = remember { StoryRunner(parsedStory) }
    val currentDialog = runner.currentDialog

    val scope = rememberCoroutineScope()
    var displayedText by remember { mutableStateOf("") }
    var fullText by remember { mutableStateOf("") }
    var animationJob: Job? by remember { mutableStateOf(null) }

    // "Subltitle" like animation
    LaunchedEffect(currentDialog) {
        if (currentDialog != null) {
            fullText = currentDialog.text
            displayedText = ""
            animationJob?.cancel()
            animationJob = scope.launch {
                val words = fullText.split(" ")
                for (word in words) {
                    displayedText += (word + " ")
                    delay(300)
                }
            }
        } else {
            displayedText = ""
            fullText = ""
        }
    }

    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    if (runner.isFinished()) {
                        onBack()
                    } else if (animationJob?.isActive == true) {
                        animationJob?.cancel()
                        displayedText = fullText
                    } else if (currentDialog != null) {
                        // If not animating and have dialog, advance
                        runner.next()
                    }
                }
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                if (currentDialog != null) {
                    Text(
                        text = displayedText.trim(),
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(horizontal = 16.dp)
                    )
                }
            }

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    runner.visibleCharacters.sorted().forEach { id ->
                        val character = characterFromId(id)

                        AnimatedCharacter(
                            character = character,
                            scale = 4f,
                            isPlaying = currentDialog?.speaker == id
                        )
                    }
                }
            }
        }
    }
}