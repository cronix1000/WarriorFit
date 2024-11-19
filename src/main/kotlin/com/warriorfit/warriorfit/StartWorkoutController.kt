package com.warriorfit.warriorfit


import com.google.gson.Gson
import io.appwrite.ID
import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.control.TextArea
import javafx.scene.layout.AnchorPane
import kotlinx.serialization.Serializable
import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.stage.Stage
import kotlinx.coroutines.runBlocking
import java.io.IOException
import java.net.URL
import java.util.*
import kotlin.math.round


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



class StartWorkoutController : Initializable {

    // FXML Controls
    @FXML
    private lateinit var availableExercisesList: ListView<String>

    @FXML
    private lateinit var selectedExercisesList: ListView<String>

    @FXML
    private lateinit var exerciseDetailsArea: TextArea

    @FXML
    private lateinit var totalXpLabel: Label

    @FXML
    private lateinit var workoutNotesArea: TextArea

    private var exercises: List<Exercise> = listOf()
    private val selectedExercises = mutableListOf<Exercise>()
    private val gson = Gson()

    @FXML
    //background
    public lateinit var background: AnchorPane
    //back button
    public lateinit var backButton: Button
    //labels
    public lateinit var summaryLabel: Label
    public lateinit var workoutLabel: Label
    public lateinit var xpLabel: Label
    //availableLabel
    public lateinit var availableLabel: Label
    //selectedLabel
    public lateinit var selectedLabel: Label
    //details
    public lateinit var detailsLabel: Label


    private var stats = listOf<Pair<String, Any>>(
        "Chest" to 0,
        "Shoulders" to 0,
        "Legs" to 0,
        "Back" to 0,
        "Triceps" to 0,
        "Biceps" to 0
    )


    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        //styles
        background.setStyle("-fx-background-color: red;")
        //set all text to white
        summaryLabel.setStyle("-fx-text-fill: white;")
        workoutLabel.setStyle("-fx-text-fill: white;")
        xpLabel.setStyle("-fx-text-fill: white;")
        totalXpLabel.setStyle("-fx-text-fill: white;")
        availableLabel.setStyle("-fx-text-fill: white;")
        selectedLabel.setStyle("-fx-text-fill: white;")
        detailsLabel.setStyle("-fx-text-fill: white;")
        //backbutton
        backButton.graphic = ImageView(
            Image(
                "" + javaClass.getResource
                    ("/goBack.png")
            )
        )
        //remove backbuttontext
        backButton.text = ""
        (backButton.graphic as ImageView)?.fitWidth = 70.0
        (backButton.graphic as ImageView)?.fitHeight = 70.0
        //change graphic colour to white
        backButton.graphic.style = "-fx-fill: white;"
        //remove button background
        backButton.setStyle("-fx-background-color: transparent;")


        exercises = AppState.getExercises()


        // Set up the available exercises list
        availableExercisesList.items = FXCollections.observableArrayList(
            exercises.map { it.name }
        )

        // Set up the selected exercises list
        selectedExercisesList.items = FXCollections.observableArrayList()

        // Add selection listener for exercise details
        availableExercisesList.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            newValue?.let { showExerciseDetails(it) }
        }

        // Initialize workout notes
        workoutNotesArea.text = "Add notes about your workout..."

        updateTotalXP()
    }
    @FXML
    private fun handleAddExercise() {
        val selectedName = availableExercisesList.selectionModel.selectedItem ?: return
        val exercise = exercises.find { it.name == selectedName } ?: return

        selectedExercises.add(exercise)
        selectedExercisesList.items.add(exercise.name)
        updateTotalXP()
    }

    //back button function
    public fun onBackButtonClick() {
        try {
            backButton.text = "Back Button Clicked"
            //go to previous scene
            // Load the new FXML file temporary home file
            val loader = FXMLLoader(javaClass.getResource
                ("/com/warriorfit/warriorfit/home.fxml"))
            val root = loader.load<Parent>()
            val stage = Stage()
            // Create a new scene with a specified width and height
            val newScene = Scene(root, 1280.0, 720.0) // Set width to 800 and height to 600
            // Get the current stage
            val currentStage = backButton.scene.window as Stage
            // Set the new scene on the current stage
            currentStage.scene = newScene

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    @FXML
    private fun handleRemoveExercise() {
        val selectedIndex = selectedExercisesList.selectionModel.selectedIndex
        if (selectedIndex >= 0) {
            selectedExercises.removeAt(selectedIndex)
            selectedExercisesList.items.removeAt(selectedIndex)
            updateTotalXP()
        }
    }
    private fun showExerciseDetails(exerciseName: String) {
        val exercise = exercises.find { it.name == exerciseName } ?: return

        val details = buildString {
            appendLine("Name: ${exercise.name}")
            appendLine("Category: ${exercise.category}")
            appendLine("Equipment: ${exercise.equipment}")
            appendLine("XP: ${exercise.xp}")
            appendLine("Primary Muscles: ${exercise.primaryMuscles.joinToString(", ")}")
            appendLine("\nInstructions:")
            exercise.instructions.forEach { instruction ->
                appendLine("- $instruction")
            }
        }

        exerciseDetailsArea.text = details
    }
    private fun updateTotalXP() {
        val totalXP = selectedExercises.sumOf { it.xp.toInt() }
        totalXpLabel.text = totalXP.toString()
    }
    @FXML
    fun handleStartWorkout() {
        if (selectedExercises.isEmpty()) {
                val alert =Alert(Alert.AlertType.WARNING)
                alert.contentText = "No Exercises Please select at least one exercise before starting the workout."
                alert.show()

            return
        }

      //  println(selectedExercises.map { it.name }.toList())

        // Create workout data
        val workoutId = ID.unique().substring(0, 20)
        val workoutData = mapOf(
            "workouts_id" to workoutId,
            "date" to Date(),
            "duration" to 5,
            "exercises" to selectedExercises.map { it.name }.toList(),
            "totalXPEarned" to totalXpLabel.text,
            "notes" to workoutNotesArea.text
        )

        try {
            runBlocking {
                databases.createDocument(
                    databaseId = "6732731c0015a0af0d1e",
                    collectionId = "6736dc8f0019010e89b3", // Replace with actual collection ID
                    documentId = workoutId,
                    data = workoutData
                )

                val user_data = AppState.getCurrentUserData()


                try {


                    user_data?.let { userData ->
                        val currentXP = userData.data["currentXP"] as? Long ?: 0
                        println( userData.data["currentXP"] as Long)


                        val workoutXP = totalXpLabel.text.toInt()
                        println( workoutXP)
                        val newTotalXP = currentXP + workoutXP
                        println(newTotalXP)
                        // Calculate level (assuming 100 XP per level)
                        val newLevel = (newTotalXP / 100) + 1

                        databases.updateDocument(
                            databaseId = "6732731c0015a0af0d1e",
                            collectionId = "6736dacb0021a324feed",
                            documentId = AppState.getUserId(),
                            data = mapOf(
                                "currentXP" to newTotalXP,
                                "level" to newLevel
                            )
                        )
                    }
                }
                catch (e: Exception) {
                    val alert = Alert(Alert.AlertType.ERROR)
                    alert.contentText = "Error saving to user" + e.message
                    alert.show()
                }

                val (userStats, _) = AppState.getUserFitnessData()
                val stats = userStats?.toList() ?: stats
                val personalStats = listOf(
                    ("Strength" to stats.find { it.first == "strength" }!!.second),
                    ("Endurance" to stats.find { it.first == "endurance" }!!.second),
                    ("Flexibility" to stats.find { it.first == "flexibility" }!!.second),
                    ("Speed" to stats.find { it.first == "speed" }!!.second),
                    ("Balance" to stats.find { it.first == "balance" }!!.second)
                )

                val statsBack = calculateStats(
                    selectedExercises,
                    personalStats
                )



                val currentStatsMap = statsBack!!.associate {
                    it.first.lowercase() to it.second
                }

                var strength = currentStatsMap["strength"] as? Double ?: 0.0
                var endurance = currentStatsMap["endurance"] as? Double ?: 0.0
                var flexibility = currentStatsMap["flexibility"] as? Double ?: 0.0
                var speed = currentStatsMap["speed"] as? Double ?: 0.0
                var balance = currentStatsMap["balance"] as? Double ?: 0.0

                AppState.updateUserStats(strength, endurance, flexibility, speed, balance)

                // Clear the form after successful save
                selectedExercises.clear()
                selectedExercisesList.items.clear()
                workoutNotesArea.text = ""
                updateTotalXP()

                val alert = Alert(Alert.AlertType.INFORMATION)
                alert.contentText = "Workout saved successfully!"
                alert.show()
            }


        } catch (e: Exception) {
            val alert = Alert(Alert.AlertType.ERROR)
            alert.contentText = "Error saving workout" + e.message
            alert.show()
        }
    }

    fun calculateStats(
        workoutData: List<Exercise>,
        currentStats: List<Pair<String, Any>>
    ): List<Pair<String, Any>>? {
//        try {

        // Increment factor for scaling
        val incrementFactor = 0.004

        // Initialize muscle group stats
        val muscleGroups = mutableMapOf<String, Double>()


        val currentStatsMap = currentStats.associate {
            it.first.lowercase() to it.second
        }

        // Initialize user stats with lowercase keys
        var strength = currentStatsMap["strength"] as? Double ?: 0.0
        var endurance = currentStatsMap["endurance"] as? Double ?: 0.0
        var flexibility = currentStatsMap["flexibility"] as? Double ?: 0.0
        var speed = currentStatsMap["speed"] as? Double ?: 0.0
        var balance = currentStatsMap["balance"] as? Double ?: 0.0


        workoutData.forEach { excersise ->

                val mechanic = excersise.mechanic as? String ?: "other"
                val xp = (excersise.xp as? String)?.toDoubleOrNull() ?: 0.0

                // Increment user stats based on mechanic
                when (mechanic.lowercase()) {
                    "compound" -> {
                        strength += xp * incrementFactor * 0.4
                        endurance += xp * incrementFactor * 0.3
                        balance += xp * incrementFactor * 0.3
                    }

                    "isolation" -> {
                        strength += xp * incrementFactor * 0.5
                        flexibility += xp * incrementFactor * 0.3
                        speed += xp * incrementFactor * 0.2
                    }

                    "static" -> {
                        balance += xp * incrementFactor * 0.6
                        flexibility += xp * incrementFactor * 0.4
                    }

                    else -> {
                        endurance += xp * incrementFactor * 0.4
                        speed += xp * incrementFactor * 0.3
                        strength += xp * incrementFactor * 0.3
                    }
                }

                // Increment muscle group stats
                val primaryMuscles = excersise.primaryMuscles as? List<String> ?: emptyList()
                primaryMuscles.forEach { muscle ->
                    muscleGroups[muscle] = (muscleGroups[muscle] ?: 0.0) + (xp * incrementFactor)
                }

        }
        val finalStats = listOf(
            "strength" to round(strength * 1000) / 1000,
            "endurance" to round(endurance * 1000) / 1000,
            "flexibility" to round(flexibility * 1000) / 1000,
            "speed" to round(speed * 1000) / 1000,
            "balance" to round(balance * 1000) / 1000,
            "muscleGroups" to muscleGroups.mapValues { round(it.value * 1000) / 1000 }
        )
        println(finalStats)
        // Return updated stats
        return finalStats

//    } catch (e: Exception) {
//        val alert = Alert(Alert.AlertType.ERROR)
//        alert.contentText = "Error calculating stats" + e.message
//        alert.show()
//        return  null
//    }
    }

}
