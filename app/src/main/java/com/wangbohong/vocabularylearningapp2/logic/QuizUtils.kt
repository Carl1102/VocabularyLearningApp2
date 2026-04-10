package com.wangbohong.vocabularylearningapp2.logic

object QuizUtils {
    fun isCorrectAnswer(selectedAnswer: String?, correctAnswer: String): Boolean {
        return selectedAnswer == correctAnswer
    }
}