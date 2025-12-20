package com.karastift.erzaehler.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karastift.erzaehler.domain.model.enums.LanguageCode
import com.karastift.erzaehler.domain.model.enums.LanguageLevel
import com.karastift.erzaehler.domain.usecase.GenerateStoryUseCase
import com.karastift.erzaehler.domain.usecase.GenerateTopicUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StoryViewModel(
    private val generateTopic: GenerateTopicUseCase,
//    private val generateStory: GenerateStoryUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(StoryUIState())
    val uiState: StateFlow<StoryUIState> = _uiState

    // Channel for navigation events
    private val _navigationEvents = Channel<NavigationEvent>()
    val navigationEvents = _navigationEvents.receiveAsFlow()

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

    fun onGenerateTopic() {
        viewModelScope.launch {

            setLoading(true)

            val response = generateTopic(
                LanguageCode.EN,
                languageLevel = LanguageLevel.ELEMENTARY,
                suggestion = ""
            )

            setLoading(false)
            updateTopic(response.topic)
        }
    }

    fun generateStory() {
        viewModelScope.launch {
            setLoading(true)

            try {

                setLoading(false)

                _navigationEvents.send(NavigationEvent.NavigateToStory)
            } catch (e: Exception) {
                // TODO: Handle error
                setLoading(false)
            }
        }
    }
}