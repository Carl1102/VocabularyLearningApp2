package com.wangbohong.vocabularylearningapp2.logic

import org.junit.Assert.assertEquals
import org.junit.Test

class SettingsUtilsTest {

    @Test
    fun increaseDailyGoal_addsFiveNormally() {
        val result = SettingsUtils.increaseDailyGoal(10)
        assertEquals(15, result)
    }

    @Test
    fun increaseDailyGoal_doesNotExceedThirty() {
        val result = SettingsUtils.increaseDailyGoal(30)
        assertEquals(30, result)
    }

    @Test
    fun decreaseDailyGoal_subtractsFiveNormally() {
        val result = SettingsUtils.decreaseDailyGoal(15)
        assertEquals(10, result)
    }

    @Test
    fun decreaseDailyGoal_doesNotGoBelowFive() {
        val result = SettingsUtils.decreaseDailyGoal(5)
        assertEquals(5, result)
    }
}