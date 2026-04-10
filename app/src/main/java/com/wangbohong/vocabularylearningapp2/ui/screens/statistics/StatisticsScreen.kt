package com.wangbohong.vocabularylearningapp2.ui.screens.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.wangbohong.vocabularylearningapp2.data.local.AppDatabaseHelper

@Composable
fun StatisticsScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val dbHelper = remember { AppDatabaseHelper(context.applicationContext) }

    var totalWordsStudied by remember { mutableIntStateOf(20) }
    var quizAccuracy by remember { mutableIntStateOf(85) }
    var bestScore by remember { mutableIntStateOf(9) }
    var completedSessions by remember { mutableIntStateOf(3) }
    var currentStreak by remember { mutableIntStateOf(2) }

    fun loadStatistics() {
        val stats = dbHelper.getStatistics()
        totalWordsStudied = stats.totalWordsStudied
        quizAccuracy = stats.quizAccuracy
        bestScore = stats.bestScore
        completedSessions = stats.completedSessions
        currentStreak = stats.currentStreak
    }

    LaunchedEffect(Unit) {
        loadStatistics()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Button(onClick = onBackClick) {
            Text("Back")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "User Statistics",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Learning Progress",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Total Words Studied: $totalWordsStudied")
                Text(text = "Quiz Accuracy: $quizAccuracy%")
                Text(text = "Best Score: $bestScore")
                Text(text = "Completed Sessions: $completedSessions")
                Text(text = "Current Streak: $currentStreak days")
                Text(text = "Weak Words: accurate, analyze")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Demo Controls",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                dbHelper.addStudyProgress()
                loadStatistics()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Study Progress")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                dbHelper.resetStatistics()
                loadStatistics()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Reset Statistics")
        }
    }
}