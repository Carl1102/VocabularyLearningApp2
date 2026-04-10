package com.wangbohong.vocabularylearningapp2.logic

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class QuizUtilsTest {

    @Test
    fun isCorrectAnswer_returnsTrue_whenAnswerMatches() {
        val result = QuizUtils.isCorrectAnswer("Correct", "Correct")
        assertTrue(result)
    }

    @Test
    fun isCorrectAnswer_returnsFalse_whenAnswerDoesNotMatch() {
        val result = QuizUtils.isCorrectAnswer("Fast", "Correct")
        assertFalse(result)
    }

    @Test
    fun isCorrectAnswer_returnsFalse_whenAnswerIsNull() {
        val result = QuizUtils.isCorrectAnswer(null, "Correct")
        assertFalse(result)
    }
}