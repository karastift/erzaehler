package com.karastift.erzaehler

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.karastift.erzaehler.di.appModule
import com.karastift.erzaehler.ui.StoryScreen
import com.karastift.erzaehler.theme.ErzaehlerTheme
import com.karastift.erzaehler.ui.HomeScreen
import com.karastift.erzaehler.ui.StoryViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.getKoin
import org.koin.core.KoinApplication

// Enum for Screens in the APp
enum class ErzaehlerScreen { Home, Story }

@Composable
@Preview
fun App() {

    KoinApplication(application = {
        modules(appModule)
    }) {
        val viewModel: StoryViewModel = getKoin().get<StoryViewModel>()
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

            Scaffold() { innerPadding ->
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
                            isLoading = uiState.isLoading,
                            onTopicChange = viewModel::updateTopic,
                            onGenerateTopic = viewModel::onGenerateTopic,
                            onTopicSubmit = viewModel::generateStory,
                        )
                    }
                    composable(route = ErzaehlerScreen.Story.name) {
                        StoryScreen(
                            story = uiState.storyJson,
                            onBack = { navController.popBackStack() },
                        )
                    }
                }
            }
        }
    }
}