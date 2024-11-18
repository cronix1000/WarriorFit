package com.warriorfit.warriorfit

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object AppState {
    // User state
    private var userId: String? = null

    // Exercise data
    private var exercises: List<Exercise> = listOf()
    private val gson = Gson()

    // Initialization flag
    private var isExercisesLoaded = false

    fun setUserId(id: String) {
        userId = id
    }

    fun getUserId(): String {
        return userId ?: throw IllegalStateException("User ID not set. Make sure user is logged in.")
    }

    fun loadExercises() {
        if (!isExercisesLoaded) {
            try {
                val jsonString = javaClass.getResourceAsStream("/exercises.json")?.bufferedReader()?.readText()
                    ?: throw Exception("Could not find exercises.json")

                exercises = try {
                    val listType = object : TypeToken<List<Exercise>>() {}.type
                    gson.fromJson(jsonString, listType)
                } catch (e: Exception) {
                    listOf(gson.fromJson(jsonString, Exercise::class.java))
                }

                isExercisesLoaded = true
            } catch (e: Exception) {
                println("Error loading exercises: ${e.message}")
                exercises = listOf()
            }
        }
    }

    fun getExercises(): List<Exercise> {
        if (!isExercisesLoaded) {
            loadExercises()
        }
        return exercises
    }

    fun clearUserSession() {
        userId = null
    }
}