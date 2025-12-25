package com.karastift.erzaehler.domain.model.entities

import kotlinx.serialization.Serializable

@Serializable
data class Voice(
    val id: String,
    val name: String,
    val description: String,
    val language: String,
    val gender: String,
)