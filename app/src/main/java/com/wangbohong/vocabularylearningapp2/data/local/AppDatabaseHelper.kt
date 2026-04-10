package com.wangbohong.vocabularylearningapp2.data.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class SettingsData(
    val soundEnabled: Boolean,
    val darkModeEnabled: Boolean,
    val difficulty: String,
    val dailyGoal: Int
)

data class StatisticsData(
    val totalWordsStudied: Int,
    val quizAccuracy: Int,
    val bestScore: Int,
    val completedSessions: Int,
    val currentStreak: Int
)

class AppDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE settings (
                id INTEGER PRIMARY KEY,
                soundEnabled INTEGER NOT NULL,
                darkModeEnabled INTEGER NOT NULL,
                difficulty TEXT NOT NULL,
                dailyGoal INTEGER NOT NULL
            )
            """.trimIndent()
        )

        db.execSQL(
            """
            CREATE TABLE statistics (
                id INTEGER PRIMARY KEY,
                totalWordsStudied INTEGER NOT NULL,
                quizAccuracy INTEGER NOT NULL,
                bestScore INTEGER NOT NULL,
                completedSessions INTEGER NOT NULL,
                currentStreak INTEGER NOT NULL
            )
            """.trimIndent()
        )

        db.execSQL(
            """
            INSERT INTO settings (id, soundEnabled, darkModeEnabled, difficulty, dailyGoal)
            VALUES (1, 1, 0, 'Medium', 10)
            """.trimIndent()
        )

        db.execSQL(
            """
            INSERT INTO statistics (id, totalWordsStudied, quizAccuracy, bestScore, completedSessions, currentStreak)
            VALUES (1, 20, 85, 9, 3, 2)
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS settings")
        db.execSQL("DROP TABLE IF EXISTS statistics")
        onCreate(db)
    }

    fun getSettings(): SettingsData {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT soundEnabled, darkModeEnabled, difficulty, dailyGoal FROM settings WHERE id = 1",
            null
        )

        var settings = SettingsData(
            soundEnabled = true,
            darkModeEnabled = false,
            difficulty = "Medium",
            dailyGoal = 10
        )

        if (cursor.moveToFirst()) {
            settings = SettingsData(
                soundEnabled = cursor.getInt(0) == 1,
                darkModeEnabled = cursor.getInt(1) == 1,
                difficulty = cursor.getString(2),
                dailyGoal = cursor.getInt(3)
            )
        }

        cursor.close()
        return settings
    }

    fun saveSettings(settings: SettingsData) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("soundEnabled", if (settings.soundEnabled) 1 else 0)
            put("darkModeEnabled", if (settings.darkModeEnabled) 1 else 0)
            put("difficulty", settings.difficulty)
            put("dailyGoal", settings.dailyGoal)
        }

        db.update("settings", values, "id = ?", arrayOf("1"))
    }

    fun getStatistics(): StatisticsData {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT totalWordsStudied, quizAccuracy, bestScore, completedSessions, currentStreak FROM statistics WHERE id = 1",
            null
        )

        var statistics = StatisticsData(
            totalWordsStudied = 20,
            quizAccuracy = 85,
            bestScore = 9,
            completedSessions = 3,
            currentStreak = 2
        )

        if (cursor.moveToFirst()) {
            statistics = StatisticsData(
                totalWordsStudied = cursor.getInt(0),
                quizAccuracy = cursor.getInt(1),
                bestScore = cursor.getInt(2),
                completedSessions = cursor.getInt(3),
                currentStreak = cursor.getInt(4)
            )
        }

        cursor.close()
        return statistics
    }

    fun addStudyProgress() {
        val current = getStatistics()

        val updated = StatisticsData(
            totalWordsStudied = current.totalWordsStudied + 5,
            quizAccuracy = minOf(current.quizAccuracy + 1, 100),
            bestScore = minOf(current.bestScore + 1, 10),
            completedSessions = current.completedSessions + 1,
            currentStreak = current.currentStreak + 1
        )

        saveStatistics(updated)
    }

    fun resetStatistics() {
        saveStatistics(
            StatisticsData(
                totalWordsStudied = 20,
                quizAccuracy = 85,
                bestScore = 9,
                completedSessions = 3,
                currentStreak = 2
            )
        )
    }

    private fun saveStatistics(statistics: StatisticsData) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("totalWordsStudied", statistics.totalWordsStudied)
            put("quizAccuracy", statistics.quizAccuracy)
            put("bestScore", statistics.bestScore)
            put("completedSessions", statistics.completedSessions)
            put("currentStreak", statistics.currentStreak)
        }

        db.update("statistics", values, "id = ?", arrayOf("1"))
    }

    companion object {
        private const val DATABASE_NAME = "vocabulary_app.db"
        private const val DATABASE_VERSION = 1
    }
}