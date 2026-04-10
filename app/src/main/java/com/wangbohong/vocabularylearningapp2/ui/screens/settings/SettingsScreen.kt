package com.wangbohong.vocabularylearningapp2.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.wangbohong.vocabularylearningapp2.data.local.AppDatabaseHelper
import com.wangbohong.vocabularylearningapp2.data.local.SettingsData

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val dbHelper = remember { AppDatabaseHelper(context.applicationContext) }

    var soundEnabled by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(false) }
    var selectedDifficulty by remember { mutableStateOf("Medium") }
    var dailyGoal by remember { mutableIntStateOf(10) }

    LaunchedEffect(Unit) {
        val settings = dbHelper.getSettings()
        soundEnabled = settings.soundEnabled
        darkModeEnabled = settings.darkModeEnabled
        selectedDifficulty = settings.difficulty
        dailyGoal = settings.dailyGoal
    }

    fun saveCurrentSettings() {
        dbHelper.saveSettings(
            SettingsData(
                soundEnabled = soundEnabled,
                darkModeEnabled = darkModeEnabled,
                difficulty = selectedDifficulty,
                dailyGoal = dailyGoal
            )
        )
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
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Sound",
                style = MaterialTheme.typography.bodyLarge
            )
            Switch(
                checked = soundEnabled,
                onCheckedChange = {
                    soundEnabled = it
                    saveCurrentSettings()
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Dark Mode",
                style = MaterialTheme.typography.bodyLarge
            )
            Switch(
                checked = darkModeEnabled,
                onCheckedChange = {
                    darkModeEnabled = it
                    saveCurrentSettings()
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Difficulty",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        listOf("Easy", "Medium", "Hard").forEach { difficulty ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedDifficulty == difficulty,
                    onClick = {
                        selectedDifficulty = difficulty
                        saveCurrentSettings()
                    }
                )
                Text(text = difficulty)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Daily Goal",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(
                onClick = {
                    if (dailyGoal > 5) {
                        dailyGoal -= 5
                        saveCurrentSettings()
                    }
                }
            ) {
                Text("-")
            }

            Text(
                text = "$dailyGoal words",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.bodyLarge
            )

            Button(
                onClick = {
                    if (dailyGoal < 30) {
                        dailyGoal += 5
                        saveCurrentSettings()
                    }
                }
            ) {
                Text("+")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Current Settings Summary",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Sound: ${if (soundEnabled) "On" else "Off"}")
        Text(text = "Dark Mode: ${if (darkModeEnabled) "On" else "Off"}")
        Text(text = "Difficulty: $selectedDifficulty")
        Text(text = "Daily Goal: $dailyGoal words")
    }
}