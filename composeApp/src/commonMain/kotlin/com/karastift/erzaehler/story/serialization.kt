package com.karastift.erzaehler.story

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Story(
    val characters: List<StoryCharacter>,
    val script: List<ScriptItem>
)

@Serializable
data class StoryCharacter(
    val id: String,
    val name: String
)

@Serializable
sealed class ScriptItem

@Serializable
@SerialName("dialog")
data class Dialog(
    val speaker: String,
    val text: String
) : ScriptItem()

@Serializable
@SerialName("enter")
data class Enter(
    val id: String
) : ScriptItem()

@Serializable
@SerialName("exit")
data class Exit(
    val id: String
) : ScriptItem()

// Example for future expansion: Add more actions like this
//@Serializable
//@SerialName("set_background")
//data class SetBackground(
//    val image: String
//) : ScriptItem()