package com.warriorfit.warriorfit


import com.google.gson.Gson
import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.control.Alert.AlertType
import javafx.stage.Stage
import kotlinx.serialization.Serializable
import java.io.IOException
import java.net.URL
import java.util.*


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

    @FXML
    public lateinit var backButton: Button

    private var exercises: List<Exercise> = listOf()
    private val selectedExercises = mutableListOf<Exercise>()
    private val gson = Gson()
    override fun initialize(p0: URL?, p1: ResourceBundle?) {

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

        // Create workout data
        val workoutId = UUID.randomUUID().toString().substring(0, 14)
        val workoutData = mapOf(
            "workouts_id" to workoutId,
            "date" to Date(),
            "exercises" to selectedExercises.map { it.id },
            "totalXPEarned" to totalXpLabel.text,
            "notes" to workoutNotesArea.text
        )

        try {
            // TODO: Implement your database save logic here

            // Clear the form after successful save
            selectedExercises.clear()
            selectedExercisesList.items.clear()
            workoutNotesArea.text = ""
            updateTotalXP()

            val alert =Alert(Alert.AlertType.INFORMATION)
            alert.contentText = "Workout saved successfully!"
            alert.show()
        } catch (e: Exception) {
            val alert = Alert(Alert.AlertType.ERROR)
            alert.contentText = e.message
            alert.show()
        }
    }

    public fun onBackButtonClick() {
        try {
            val loader = FXMLLoader(javaClass.getResource("/com/warriorfit/warriorfit/home.fxml"))
            val root = loader.load<Parent>()

            // Create a new scene with a specified width and height
            val newScene = Scene(root, 1280.0, 720.0) // Set width to 800 and height to 600

            // Get the current stage
            val currentStage = backButton.scene.window as Stage

            // Set the new scene on the current stage
            currentStage.scene = newScene
            currentStage.title = "Warrior Fitness"

            // Optionally, you can also set a minimum or fixed size on the stage
            currentStage.minWidth = 1280.0
            currentStage.minHeight = 720.0

            // Display the stage
            currentStage.show()

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}
