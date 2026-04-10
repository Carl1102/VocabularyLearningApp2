package com.wangbohong.vocabularylearningapp2.logic

object SettingsUtils {

    fun increaseDailyGoal(currentGoal: Int): Int {
        return minOf(currentGoal + 5, 30)
    }

    fun decreaseDailyGoal(currentGoal: Int): Int {
        return maxOf(currentGoal - 5, 5)
    }
}