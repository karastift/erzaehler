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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.karastift.erzaehler.domain.model.enums.LanguageCode
import com.karastift.erzaehler.domain.model.enums.LanguageLevel
import com.karastift.erzaehler.domain.model.enums.displayName
import com.karastift.erzaehler.domain.model.enums.toFlag
import com.karastift.erzaehler.theme.ErzaehlerTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

fun LanguageLevel.toIcon() = when (this) {
    LanguageLevel.BEGINNER -> Icons.Default.School
    LanguageLevel.ELEMENTARY -> Icons.Default.Book
    LanguageLevel.INTERMEDIATE -> Icons.Default.LibraryBooks
    LanguageLevel.UPPER_INTERMEDIATE -> Icons.Default.MenuBook
    LanguageLevel.ADVANCED -> Icons.Default.AutoStories
    LanguageLevel.PROFICIENT -> Icons.Default.Stars
}

@Composable
fun HomeScreen(
    topic: String,
    isLoading: Boolean,
    errorMessage: String,
    onTopicChange: (String) -> Unit,
    onGenerateTopic: () -> Unit,
    onTopicSubmit: () -> Unit,
    language: LanguageCode,
    onLanguageChange: (LanguageCode) -> Unit,
    level: LanguageLevel,
    onLevelChange: (LanguageLevel) -> Unit,
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
                modifier = Modifier.fillMaxWidth().weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "erzaehler",
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.padding(16.dp)
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(16.dp)
                ) {
                    var languageMenuOpen by remember { mutableStateOf(false) }
                    Box {
                        IconButton(
                            onClick = { languageMenuOpen = true },
                            colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text(language.toFlag(), fontSize = 24.sp)
                        }
                        DropdownMenu(
                            expanded = languageMenuOpen,
                            onDismissRequest = { languageMenuOpen = false },
                            modifier = Modifier.width(250.dp)
                        ) {
                            LanguageCode.values().forEach { lang ->
                                DropdownMenuItem(
                                    text = { Text(lang.toFlag() + " " + lang.displayName()) },
                                    onClick = {
                                        onLanguageChange(lang)
                                        languageMenuOpen = false
                                    }
                                )
                            }
                        }
                    }

                    var levelMenuOpen by remember { mutableStateOf(false) }
                    Box {
                        IconButton(
                            onClick = { levelMenuOpen = true },
                            colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Icon(level.toIcon(), contentDescription = level.name)
                        }
                        DropdownMenu(
                            expanded = levelMenuOpen,
                            onDismissRequest = { levelMenuOpen = false },
                            modifier = Modifier.width(250.dp)
                        ) {
                            LanguageLevel.entries.forEach { lev ->
                                DropdownMenuItem(
                                    onClick = {
                                        onLevelChange(lev)
                                        levelMenuOpen = false
                                    },
                                    text = {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(lev.toIcon(), contentDescription = lev.name)
                                            Text(lev.name, modifier = Modifier.padding(start = 8.dp))
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
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
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            cursorColor = MaterialTheme.colorScheme.primary
                        )
                    )

                    IconButton(
                        onClick = onGenerateTopic,
                        enabled = !isLoading,
                        colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary)
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
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
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
            language = LanguageCode.EN,
            onLanguageChange = {},
            level = LanguageLevel.INTERMEDIATE,
            onLevelChange = {},
        )
    }
}