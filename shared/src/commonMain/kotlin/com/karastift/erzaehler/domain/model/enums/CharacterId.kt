package com.karastift.erzaehler.domain.model.enums

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = CharacterIdSerializer::class)
enum class CharacterId {
    BLONDE_KID_GIRL,
    BLONDE_MAN,
    BLONDE_WOMAN,
    BLUE_HAIRED_KID_GIRL,
    BLUE_HAIRED_WOMAN,
    BRIDE,
    BUSINESSMAN,

    CHEF,
    DRACULA,
    FARMER,
    FIREFIGHTER,
    GOBLIN_KID,
    GOBLIN_MAN,
    GOBLIN_WOMAN,

    JOKER,
    KNIGHT,
    KNIGHT_KID,
    NINJA,
    NUN,

    OLD_MAN,
    OLD_WOMAN,
    POLICEMAN,
    PUNK_KID_BOY,
    PUNK_WOMAN,

    SOLDIER,
    VIKING_KID_BOY,
    VIKING_MAN,
    VIKING_WOMAN
}


object CharacterIdSerializer : KSerializer<CharacterId> {

    override val descriptor =
        PrimitiveSerialDescriptor("CharacterId", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: CharacterId) {
        encoder.encodeString(value.name.lowercase())
    }

    override fun deserialize(decoder: Decoder): CharacterId {
        val raw = decoder.decodeString()
        return CharacterId.entries.firstOrNull {
            it.name.equals(raw, ignoreCase = true)
        } ?: error("Unknown CharacterId: $raw")
    }
}