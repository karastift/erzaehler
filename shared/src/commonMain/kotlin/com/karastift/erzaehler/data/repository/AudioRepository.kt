package com.karastift.erzaehler.data.repository

import com.karastift.erzaehler.domain.model.entities.AudioData
import com.karastift.erzaehler.domain.model.requests.VoiceRequest

interface AudioRepository {

    suspend fun getAudioFromDialog(voiceRequest: VoiceRequest): AudioData
}