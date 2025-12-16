package com.karastift.erzaehler.characters

import org.jetbrains.compose.resources.DrawableResource

data class Character(
    val name: String,
    val idleFrames: List<DrawableResource>
)