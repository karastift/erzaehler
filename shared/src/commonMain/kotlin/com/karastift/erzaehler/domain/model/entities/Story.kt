package com.karastift.erzaehler.domain.model.entities

import kotlinx.serialization.Serializable

@Serializable
data class Story(
    val characters: List<Character>,
    val script: List<ScriptItem>
)
