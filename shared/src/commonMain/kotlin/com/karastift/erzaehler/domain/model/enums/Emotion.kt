package com.karastift.erzaehler.domain.model.enums

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

// Voice provider expects lowercase --> @SerialName
@Serializable(with = EmotionSerializer::class)
enum class Emotion {
    NEUTRAL,
    HAPPY,
    EXCITED,
    ENTHUSIASTIC,
    ELATED,
    EUPHORIC,
    TRIUMPHANT,
    AMAZED,
    SURPRISED,
    FLIRTATIOUS,
    CURIOUS,
    CONTENT,
    PEACEFUL,
    SERENE,
    CALM,
    GRATEFUL,
    AFFECTIONATE,
    TRUST,
    SYMPATHETIC,
    ANTICIPATION,
    MYSTERIOUS,
    ANGRY,
    MAD,
    OUTRAGED,
    FRUSTRATED,
    AGITATED,
    THREATENED,
    DISGUSTED,
    CONTEMPT,
    ENVIOUS,
    SARCASTIC,
    IRONIC,
    SAD,
    DEJECTED,
    MELANCHOLIC,
    DISAPPOINTED,
    HURT,
    GUILTY,
    BORED,
    TIRED,
    REJECTED,
    NOSTALGIC,
    WISTFUL,
    APOLOGETIC,
    HESITANT,
    INSECURE,
    CONFUSED,
    RESIGNED,
    ANXIOUS,
    PANICKED,
    ALARMED,
    SCARED,
    PROUD,
    CONFIDENT,
    DISTANT,
    SKEPTICAL,
    CONTEMPLATIVE,
    DETERMINED
}


object EmotionSerializer : KSerializer<Emotion> {

    override val descriptor =
        PrimitiveSerialDescriptor("Emotion", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Emotion) {
        encoder.encodeString(value.name.lowercase())
    }

    override fun deserialize(decoder: Decoder): Emotion {
        val value = decoder.decodeString()
        return Emotion.entries.firstOrNull {
            it.name.equals(value, ignoreCase = true)
        } ?: error("Unknown Emotion: $value")
    }
}
