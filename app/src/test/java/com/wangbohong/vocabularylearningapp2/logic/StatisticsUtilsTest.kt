package com.wangbohong.vocabularylearningapp2.logic

import org.junit.Assert.assertEquals
import org.junit.Test

class StatisticsUtilsTest {

    @Test
    fun addStudyProgress_increasesStatisticsCorrectly() {
        val current = StatisticsState(
            totalWordsStudied = 20,
            quizAccuracy = 85,
            bestScore = 9,
            completedSessions = 3,
            currentStreak = 2
        )

        val updated = StatisticsUtils.addStudyProgress(current)

        assertEquals(25, updated.totalWordsStudied)
        assertEquals(86, updated.quizAccuracy)
        assertEquals(10, updated.bestScore)
        assertEquals(4, updated.completedSessions)
        assertEquals(3, updated.currentStreak)
    }

    @Test
    fun addStudyProgress_doesNotExceedMaximumValues() {
        val current = StatisticsState(
            totalWordsStudied = 50,
            quizAccuracy = 100,
            bestScore = 10,
            completedSessions = 8,
            currentStreak = 5
        )

        val updated = StatisticsUtils.addStudyProgress(current)

        assertEquals(55, updated.totalWordsStudied)
        assertEquals(100, updated.quizAccuracy)
        assertEquals(10, updated.bestScore)
        assertEquals(9, updated.completedSessions)
        assertEquals(6, updated.currentStreak)
    }

    @Test
    fun defaultStatistics_returnsExpectedValues() {
        val stats = StatisticsUtils.defaultStatistics()

        assertEquals(20, stats.totalWordsStudied)
        assertEquals(85, stats.quizAccuracy)
        assertEquals(9, stats.bestScore)
        assertEquals(3, stats.completedSessions)
        assertEquals(2, stats.currentStreak)
    }
}