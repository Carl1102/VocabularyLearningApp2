package com.wangbohong.vocabularylearningapp2.ui.screens.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wangbohong.vocabularylearningapp2.data.remote.VocabularyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class QuizItem(
    val word: String,
    val meaning: String,
    val example: String,
    val options: List<String>,
    val correctAnswer: String
)

@Composable
fun ActivityScreen(
    onBackClick: () -> Unit
) {
    val quizItems = listOf(
        QuizItem(
            word = "Accurate",
            meaning = "Correct and exact",
            example = "The answer is accurate.",
            options = listOf("Fast", "Correct", "Difficult", "Simple"),
            correctAnswer = "Correct"
        ),
        QuizItem(
            word = "Analyze",
            meaning = "To examine something carefully",
            example = "Students analyze the text in class.",
            options = listOf("Ignore", "Examine", "Forget", "Repeat"),
            correctAnswer = "Examine"
        ),
        QuizItem(
            word = "Benefit",
            meaning = "A useful or helpful result",
            example = "Reading every day has many benefits.",
            options = listOf("Problem", "Punishment", "Advantage", "Mistake"),
            correctAnswer = "Advantage"
        )
    )

    val repository = remember { VocabularyRepository() }
    val scope = rememberCoroutineScope()

    var currentIndex by remember { mutableStateOf(0) }
    var selectedOption by remember { mutableStateOf<String?>(null) }
    var submitted by remember { mutableStateOf(false) }

    var onlineDefinition by remember { mutableStateOf("No online definition loaded yet.") }
    var isLoading by remember { mutableStateOf(false) }

    val currentItem = quizItems[currentIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Button(onClick = onBackClick) {
            Text("Back")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Learning Activity",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Word ${currentIndex + 1} of ${quizItems.size}",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Flashcard",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Word: ${currentItem.word}")
                Text(text = "Meaning: ${currentItem.meaning}")
                Text(text = "Example: ${currentItem.example}")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Online Definition",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Current word: ${currentItem.word}")

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        scope.launch {
                            isLoading = true
                            onlineDefinition = withContext(Dispatchers.IO) {
                                repository.fetchDefinition(currentItem.word)
                            }
                            isLoading = false
                        }
                    }
                ) {
                    Text("Load Online Definition")
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = if (isLoading) "Loading..." else onlineDefinition,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Quiz",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "What is the meaning of '${currentItem.word}'?")

                Spacer(modifier = Modifier.height(12.dp))

                currentItem.options.forEach { option ->
                    OutlinedButton(
                        onClick = {
                            if (!submitted) {
                                selectedOption = option
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !submitted
                    ) {
                        Text(option)
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }

                if (selectedOption != null) {
                    Text(
                        text = "Selected answer: $selectedOption",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { submitted = true },
                    enabled = selectedOption != null && !submitted
                ) {
                    Text("Submit")
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (submitted) {
                    if (selectedOption == currentItem.correctAnswer) {
                        Text(
                            text = "Correct!",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    } else {
                        Text(
                            text = "Incorrect. Correct answer: ${currentItem.correctAnswer}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }

                Button(
                    onClick = {
                        currentIndex = (currentIndex + 1) % quizItems.size
                        selectedOption = null
                        submitted = false
                        onlineDefinition = "No online definition loaded yet."
                    }
                ) {
                    Text("Next Word")
                }
            }
        }
    }
}