package com.karastift.erzaehler

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.karastift.erzaehler.audio.AudioManager
import com.karastift.erzaehler.audio.AudioPlayer
import com.karastift.erzaehler.di.appModule
import com.karastift.erzaehler.ui.StoryScreen
import com.karastift.erzaehler.theme.ErzaehlerTheme
import com.karastift.erzaehler.ui.HomeScreen
import com.karastift.erzaehler.ui.StoryViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.getKoin

// Enum for Screens in the APp
enum class ErzaehlerScreen { Home, Story }

@Composable
fun App() {

    KoinApplication(application = {
        modules(appModule)
    }) {
        val viewModel: StoryViewModel = getKoin().get<StoryViewModel>()
        val audioManger = getKoin().get<AudioManager>()

        val navController: NavHostController = rememberNavController()

        ErzaehlerTheme {
            // Collect navigation events and handle
            LaunchedEffect(Unit) {
                viewModel.navigationEvents.collect { event ->
                    when (event) {
                        is StoryViewModel.NavigationEvent.NavigateToStory -> {
                            navController.navigate(ErzaehlerScreen.Story.name)
                        }
                    }
                }
            }

            Scaffold { innerPadding ->
                val uiState by viewModel.uiState.collectAsState()

                NavHost(
                    navController = navController,
                    startDestination = ErzaehlerScreen.Home.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    composable(route = ErzaehlerScreen.Home.name) {
                        HomeScreen(
                            topic = uiState.topic,
                            onTopicChange = viewModel::updateTopicByUser,
                            language = uiState.language,
                            onLanguageChange = viewModel::setLanguage,
                            level = uiState.languageLevel,
                            onLevelChange = viewModel::setLanguageLevel,
                            isLoading = uiState.isLoading,
                            errorMessage = uiState.errorMessage,
                            onGenerateTopic = viewModel::onGenerateTopic,
                            onTopicSubmit = viewModel::onGenerateStory,
                        )
                    }
                    composable(ErzaehlerScreen.Story.name) {
                        val story = uiState.story
                        if (story != null) {
                            StoryScreen(
                                story = story,
                                onBack = navController::popBackStack,
                                audioManager = audioManger,
                            )
                        } else {
                            // Back out again
                            LaunchedEffect(Unit) { navController.popBackStack() }
                        }
                    }
                }
            }
        }
    }
}