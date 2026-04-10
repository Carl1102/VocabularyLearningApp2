package com.wangbohong.vocabularylearningapp2.data.remote

import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class VocabularyRepository {

    fun fetchDefinition(word: String): String {
        val url = URL("https://api.dictionaryapi.dev/api/v2/entries/en/${word.lowercase()}")
        val connection = url.openConnection() as HttpURLConnection

        return try {
            connection.requestMethod = "GET"
            connection.connectTimeout = 10000
            connection.readTimeout = 10000

            val responseCode = connection.responseCode

            if (responseCode != HttpURLConnection.HTTP_OK) {
                val errorText = connection.errorStream?.bufferedReader()?.use { it.readText() }
                return "HTTP $responseCode: ${errorText ?: "Unable to load definition"}"
            }

            val response = connection.inputStream.bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(response)

            val firstEntry = jsonArray.getJSONObject(0)
            val meanings = firstEntry.getJSONArray("meanings")
            val firstMeaning = meanings.getJSONObject(0)
            val definitions = firstMeaning.getJSONArray("definitions")
            val firstDefinition = definitions.getJSONObject(0)

            firstDefinition.getString("definition")
        } catch (e: Exception) {
            "Error: ${e.javaClass.simpleName} - ${e.message}"
        } finally {
            connection.disconnect()
        }
    }
}