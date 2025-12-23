package com.karastift.erzaehler.story

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.karastift.erzaehler.audio.AudioManager
import com.karastift.erzaehler.domain.model.enums.CharacterId
import com.karastift.erzaehler.domain.model.entities.Dialog
import com.karastift.erzaehler.domain.model.entities.Enter
import com.karastift.erzaehler.domain.model.entities.Exit
import com.karastift.erzaehler.domain.model.entities.Story
import com.karastift.erzaehler.domain.model.requests.AudioRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.collections.mutableSetOf

class StoryRunner(
    private val story: Story,
    private val audioManager: AudioManager,
    private val scope: CoroutineScope
) {
    var index by mutableStateOf(0)
        private set

    var visibleCharacters by mutableStateOf(mutableSetOf<CharacterId>())
        private set

    var currentDialog: Dialog? by mutableStateOf(null)
        private set

    init {
        advance()  // Initialize to first dialog or end
    }

    fun next() {
        index++
        advance()
        preloadAudio()
    }

    private fun advance() {
        while (!isFinished()) {
            when (val item = story.script[index]) {
                is Dialog -> {
                    currentDialog = item

                    val audioRequest = AudioRequest(
                        languageCode = story.languageCode,
                        languageLevel = story.languageLevel,
                        dialog = item,
                    )

                    scope.launch {
                        audioManager.ensureAudioAndPlay(audioRequest)
                    }

                    return // Wait until user clicks next
                }
                is Enter -> {
                    visibleCharacters.add(item.id)
                }
                is Exit -> {
                    visibleCharacters.remove(item.id)
                }
            }
            index++ // Continue to next item if action
        }
        currentDialog = null // Reached end
    }

    fun isFinished(): Boolean = index >= story.script.lastIndex

    private fun preloadAudio(n: Int = 3) {

        val nextDialogs = story.script
            .drop(index)
            .filterIsInstance<Dialog>()
            .take(n)

        nextDialogs.forEach { dialog ->
            scope.launch {

                val audioRequest = AudioRequest(
                    dialog = dialog,
                    languageCode = story.languageCode,
                    languageLevel = story.languageLevel,
                )

                try {
                    audioManager.ensureAudioLoaded(audioRequest)
                }
                catch (e: Exception) {
                    // TODO: display error message and show dialog without audio
                    // for now just end the story
                    index = story.script.lastIndex
                }
            }
        }
    }
}