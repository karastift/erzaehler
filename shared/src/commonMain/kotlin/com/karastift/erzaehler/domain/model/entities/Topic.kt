package com.karastift.erzaehler.domain.model.entities

import kotlinx.serialization.Serializable

@Serializable
data class Topic(
    val description: String,
)