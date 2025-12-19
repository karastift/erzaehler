package com.karastift.erzaehler.domain.model.enums

import kotlinx.serialization.Serializable

/** ISO 639-1 codes */
@Serializable
enum class LanguageCode(val code: String) {
    EN("en"),
    ES("es"),
    FR("fr"),
    DE("de"),
    ZH("zh"),
    JA("ja"),
    KO("ko"),
    AR("ar"),
    TR("tr"),
    PL("pl");

    override fun toString() = code
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
