package com.wangbohong.vocabularylearningapp2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.wangbohong.vocabularylearningapp2.ui.screens.activity.ActivityScreen
import com.wangbohong.vocabularylearningapp2.ui.screens.landing.LandingScreen
import com.wangbohong.vocabularylearningapp2.ui.screens.settings.SettingsScreen
import com.wangbohong.vocabularylearningapp2.ui.screens.statistics.StatisticsScreen

object AppDestinations {
    const val LANDING = "landing"
    const val ACTIVITY = "activity"
    const val SETTINGS = "settings"
    const val STATISTICS = "statistics"
}

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.LANDING,
        modifier = modifier
    ) {
        composable(AppDestinations.LANDING) {
            LandingScreen(
                onStartLearningClick = {
                    navController.navigate(AppDestinations.ACTIVITY)
                },
                onViewStatisticsClick = {
                    navController.navigate(AppDestinations.STATISTICS)
                },
                onSettingsClick = {
                    navController.navigate(AppDestinations.SETTINGS)
                }
            )
        }

        composable(AppDestinations.ACTIVITY) {
            ActivityScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(AppDestinations.SETTINGS) {
            SettingsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(AppDestinations.STATISTICS) {
            StatisticsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}