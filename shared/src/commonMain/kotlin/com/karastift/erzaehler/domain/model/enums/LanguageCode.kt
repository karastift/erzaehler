package com.karastift.erzaehler.domain.model.enums

import kotlinx.serialization.Serializable

/** ISO 639-1 codes */
@Serializable
enum class LanguageCode {
    EN,
    ES,
    FR,
    DE,
    ZH,
    JA,
    KO,
    AR,
    TR,
    PL
}

val languageCodeToName = mapOf(
    LanguageCode.EN to "English",
    LanguageCode.ES to "Spanish",
    LanguageCode.FR to "French",
    LanguageCode.DE to "German",
    LanguageCode.ZH to "Chinese (Mandarin)",
    LanguageCode.JA to "Japanese",
    LanguageCode.KO to "Korean",
    LanguageCode.AR to "Arabic",
    LanguageCode.TR to "Turkish",
    LanguageCode.PL to "Polish"
)

fun LanguageCode.displayName(): String = languageCodeToName[this] ?: throw IllegalArgumentException("Unknown language code: $languageCodeToName")

fun LanguageCode.toFlag(): String = when (this) {
    LanguageCode.EN -> "🇺🇸"
    LanguageCode.ES -> "🇪🇸"
    LanguageCode.FR -> "🇫🇷"
    LanguageCode.DE -> "🇩🇪"
    LanguageCode.ZH -> "🇨🇳"
    LanguageCode.JA -> "🇯🇵"
    LanguageCode.KO -> "🇰🇷"
    LanguageCode.AR -> "🇸🇦"
    LanguageCode.TR -> "🇹🇷"
    LanguageCode.PL -> "🇵🇱"
}