package com.karastift.erzaehler.data.repository

import com.karastift.erzaehler.domain.model.entities.AudioData
import com.karastift.erzaehler.domain.model.entities.Dialog
import com.karastift.erzaehler.domain.model.enums.LanguageCode
import com.karastift.erzaehler.domain.model.requests.AudioRequest

interface AudioRepository {

    suspend fun getAudioFromDialog(audioRequest: AudioRequest): AudioData
}