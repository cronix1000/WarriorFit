package com.warriorfit.warriorfit


import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.Serializable


@Serializable
data class Exercise(
    val name: String,
    val force: String,
    val xp: String,
    val mechanic: String,
    val equipment: String,
    val primaryMuscles: List<String>,
    val secondaryMuscles: List<String>,
    val instructions: List<String>,
    val category: String,
    val images: List<String>,
    val id: String
)



class StartWorkoutController {

    var excersises : List<Exercise> = listOf()

    fun StartWorkoutController(){
        excersises = loadExercises()

        excersises.map { println(it) }
    }

    private val gson = Gson()

    fun loadExercises(): List<Exercise> {
        var jsonString = javaClass.getResourceAsStream("/exercises.json")?.bufferedReader()?.readText()
            ?: throw Exception("Could not find exercises.json")

        return try {
            // For a list of exercises
            val listType = object : TypeToken<List<Exercise>>() {}.type
            gson.fromJson(jsonString, listType)
        } catch (e: Exception) {
            // For a single exercise
            listOf(gson.fromJson(jsonString, Exercise::class.java))
        }
    }
    fun SelectExcersise(){

    }

    fun StartWorkout(){

    }


}