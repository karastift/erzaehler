package com.karastift.erzaehler.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("exit")
data class Exit(
    val id: String
) : ScriptItem()
