package com.wangbohong.vocabularylearningapp2.logic

data class StatisticsState(
    val totalWordsStudied: Int,
    val quizAccuracy: Int,
    val bestScore: Int,
    val completedSessions: Int,
    val currentStreak: Int
)

object StatisticsUtils {

    fun defaultStatistics(): StatisticsState {
        return StatisticsState(
            totalWordsStudied = 20,
            quizAccuracy = 85,
            bestScore = 9,
            completedSessions = 3,
            currentStreak = 2
        )
    }

    fun addStudyProgress(current: StatisticsState): StatisticsState {
        return StatisticsState(
            totalWordsStudied = current.totalWordsStudied + 5,
            quizAccuracy = minOf(current.quizAccuracy + 1, 100),
            bestScore = minOf(current.bestScore + 1, 10),
            completedSessions = current.completedSessions + 1,
            currentStreak = current.currentStreak + 1
        )
    }
}