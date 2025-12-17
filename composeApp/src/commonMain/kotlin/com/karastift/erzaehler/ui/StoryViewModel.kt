package com.karastift.erzaehler.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karastift.erzaehler.api.generateStoryJson
import com.karastift.erzaehler.api.generateStoryTopic
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StoryViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(StoryUIState())
    val uiState: StateFlow<StoryUIState> = _uiState

    // Channel for navigation events
    private val _navigationEvents = Channel<NavigationEvent>()
    val navigationEvents = _navigationEvents.receiveAsFlow()

    data class StoryUIState(
        val topic: String = "",
        val isLoading: Boolean = false,
        val storyJson: String = "",
    )

    sealed class NavigationEvent {
        data object NavigateToStory : NavigationEvent()
    }

    fun updateTopic(topic: String) {
        _uiState.update { currentState ->
            currentState.copy(topic = topic)
        }
    }

    fun setLoading(isLoading: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(isLoading = isLoading)
        }
    }

    fun setStoryJson(storyJson: String) {
        _uiState.update { currentState ->
            currentState.copy(storyJson = storyJson)
        }
    }

    fun generateTopic() {
        viewModelScope.launch {
            val generated = generateStoryTopic()
            updateTopic(generated)
        }
    }

    fun generateStory() {
        viewModelScope.launch {
            setLoading(true)

            try {
                val json = generateStoryJson(_uiState.value.topic)

                setStoryJson(json)
                setLoading(false)

                _navigationEvents.send(NavigationEvent.NavigateToStory)
            } catch (e: Exception) {
                // TODO: Handle error
                setLoading(false)
            }
        }
    }
}