package com.karastift.erzaehler.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karastift.erzaehler.domain.model.entities.Story
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

// TODO: maybe (!!) move to shared module

class StoryViewModel(
    private val generateTopic: GenerateTopicUseCase,
    private val generateStory: GenerateStoryUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(StoryUIState())
    val uiState: StateFlow<StoryUIState> = _uiState

    // Channel for navigation events
    private val _navigationEvents = Channel<NavigationEvent>()
    val navigationEvents = _navigationEvents.receiveAsFlow()

    sealed class NavigationEvent {
        data object NavigateToStory : NavigationEvent()
    }

    fun setLanguage(languageCode: LanguageCode) {
        _uiState.update { currentState ->
            currentState.copy(language = languageCode)
        }
    }

    fun setLanguageLevel(languageLevel: LanguageLevel) {
        _uiState.update { currentState ->
            currentState.copy(languageLevel = languageLevel)
        }
    }

    fun setIsUserSuggestion(flag: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(isUserSuggestion = flag)
        }
    }

    fun updateTopic(topic: String) {
        _uiState.update { currentState ->
            currentState.copy(topic = topic)
        }
    }

    fun updateTopicByUser(topic: String) {
        setIsUserSuggestion(true)
        updateTopic(topic)
    }

    fun setLoading(isLoading: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(isLoading = isLoading)
        }
    }

    fun setErrorMessage(errorMessage: String) {
        _uiState.update { currentState ->
            currentState.copy(errorMessage = errorMessage)
        }
    }

    fun setStory(story: Story) {
        _uiState.update { currentState ->
            currentState.copy(story = story)
        }
    }

    fun onGenerateTopic() {
        viewModelScope.launch {

            setLoading(true)
            setErrorMessage("")

            val suggestion = if (uiState.value.isUserSuggestion) uiState.value.topic else ""

            try {
                val response = generateTopic(
                    languageCode = LanguageCode.KO,
                    languageLevel = LanguageLevel.PROFICIENT,
                    suggestion = suggestion
                )

                updateTopic(response.topic.description)
                setIsUserSuggestion(false)
            }
            catch (e: Exception) {
                setErrorMessage(e.message ?: "Something went wrong.")
            }
            finally {
               setLoading(false)
            }
        }
    }

    fun onGenerateStory() {
        viewModelScope.launch {

            setLoading(true)
            setErrorMessage("")

            try {
                val response = generateStory(
                    languageCode = uiState.value.language,
                    languageLevel = uiState.value.languageLevel,
                    topic = uiState.value.topic
                )

                setStory(response.story)
                setIsUserSuggestion(true)
                updateTopic("")

                _navigationEvents.trySend(NavigationEvent.NavigateToStory)
            }
            catch (e: Exception) {
                setErrorMessage(e.message ?: "Something went wrong.")
            }
            finally {
                setLoading(false)
            }
        }
    }
}