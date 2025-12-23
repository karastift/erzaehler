package com.karastift.erzaehler.domain.usecase

import com.karastift.erzaehler.data.repository.AudioRepository
import com.karastift.erzaehler.domain.model.entities.AudioData
import com.karastift.erzaehler.domain.model.requests.AudioRequest

class GenerateAudioUseCase(
    val repository: AudioRepository
) {
    suspend operator fun invoke(audioRequest: AudioRequest): AudioData {

        return repository.getAudioFromDialog(audioRequest)
    }
}