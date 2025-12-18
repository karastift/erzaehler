package com.karastift.erzaehler.story

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.karastift.erzaehler.domain.model.Dialog
import com.karastift.erzaehler.domain.model.Enter
import com.karastift.erzaehler.domain.model.Exit
import com.karastift.erzaehler.domain.model.Story
import kotlin.collections.mutableSetOf

class StoryRunner(
    private val story: Story
) {
    var index by mutableStateOf(0)
        private set

    var visibleCharacters by mutableStateOf(mutableSetOf<String>())
        private set

    var currentDialog: Dialog? by mutableStateOf(null)
        private set

    init {
        advance()  // Initialize to first dialog or end
    }

    fun next() {
        index++
        advance()
    }

    private fun advance() {
        while (!isFinished()) {
            when (val item = story.script[index]) {
                is Dialog -> {
                    currentDialog = item
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

    fun isFinished(): Boolean =
        index >= story.script.lastIndex
}