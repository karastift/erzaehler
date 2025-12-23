package com.karastift.erzaehler.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.karastift.erzaehler.theme.ErzaehlerTheme
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun HomeScreen(
    topic: String,
    isLoading: Boolean,
    errorMessage: String,
    onTopicChange: (String) -> Unit,
    onGenerateTopic: () -> Unit,
    onTopicSubmit: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            }
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
                Row {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.displaySmall,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = topic,
                        onValueChange = onTopicChange,
                        label = { Text("I'm thinking of ...") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(15.dp),
                        enabled = !isLoading,
                    )

                    IconButton(
                        onClick = onGenerateTopic,
                        enabled = !isLoading,
                    ) {
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
                        modifier = Modifier.fillMaxWidth(0.4f),
                        enabled = topic.isNotBlank() && !isLoading,
                        shape = RoundedCornerShape(15.dp),
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
    ErzaehlerTheme {
        HomeScreen(
            topic = "Ich habe meinen Doener schon bestellt und dann faellt mir auf, dass ich kein Bargeld dabei hab.",
            isLoading = false,
            onTopicChange = {},
            onGenerateTopic = {},
            onTopicSubmit = {},
            errorMessage = "Something went wrong.",
        )
    }
}